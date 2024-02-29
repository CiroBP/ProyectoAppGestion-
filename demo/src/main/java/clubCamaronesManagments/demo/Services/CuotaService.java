package clubCamaronesManagments.demo.Services;
import clubCamaronesManagments.demo.Models.Cuota;
import clubCamaronesManagments.demo.Models.PrecioCuota;
import clubCamaronesManagments.demo.Models.Socio;
import clubCamaronesManagments.demo.Repositorys.CuotaRepository;
import clubCamaronesManagments.demo.Utilities.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class CuotaService {

    private final CuotaRepository cr;
    private CuotaSocioService sc;
    private PrecioCuotaService pcs;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class); // para ver los errores de los @Scheduled
    private RandomNumberGenerator random;
    @Autowired
    public CuotaService(CuotaRepository cr, CuotaSocioService sc, PrecioCuotaService pcs){
        this.cr = cr;
        this.sc = sc;
        this.random = new RandomNumberGenerator(new Random(), cr);
        this.pcs = pcs;
        AsignarCuotaAsocios();
    }

    public List<Cuota> getAll(){
        try{

            return (List<Cuota>) cr.findAll();
        }
        catch(Exception e){
            return (List<Cuota>) new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity SaveCuota(Cuota cuota){
        try {
            cuota.setId(random.asignarId());
            cr.save(cuota);
            return ResponseEntity.status(CREATED).build();
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity UpdateCuota (int id, Cuota cuota){
        try {
            if (cr.existsById(id)) {
                Cuota cta = cr.findById(id).get();
                cta.setMesCuota(cuota.getMesCuota());
                cta.setFechaVencimiento(cuota.getFechaVencimiento());
                cta.setPagada(cuota.isPagada());
                cta.setPrecioCuota(cuota.getPrecioCuota());
                cr.save(cta);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity DeleteCuota (int id){
        try{
            if (cr.existsById(id)){
                cr.deleteById(id);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Scheduled(cron = "0 0 0 1 * ?") // Asignada para que pase a las 00:00 del 1 de cada mes
    public void AsignarCuotaAsocios(){
        /*
        Este metodo se encarga de asignarle las cuotas de cada mes a todos los socios.
        */
        List<Socio> socios = sc.getAllSocios();
        List<Cuota> cuotasParaGuardar = new ArrayList<>();
        try{
            if(!socios.isEmpty()){
                LocalDate fechaActual = LocalDate.now();
                Date fechaVencimiento = obtenerFechaVencimiento(fechaActual);
                for (Socio socio: socios) {
                    // Luego itero sobre cada socio
                    double precio = pcs.obtenerPrecioActual();
                    PrecioCuota pc = new PrecioCuota();
                    Cuota cuotaNueva = new Cuota(null, obtenerNombreMesActual(), fechaVencimiento, false, 000, socio, pc);
                    pc.setPrecio(1);
                    pc.setCuota(cuotaNueva);
                    pcs.savePrecioCuota(pc);
                    cuotaNueva.setPrecioCuota(pc);

                    cuotasParaGuardar.add(cuotaNueva); // guardo las cuotas en una lista

                }
                cr.saveAll(cuotasParaGuardar);
                // Utilizo el SaveAll() para guardar todas las cuotas de lo contrario tendria que ingresar a la base de datos para guardar cada cuota
            }else{
                throw new Error("no se pudo asignar las cuotas a los socios");
                // Esto si se ve en el front.
            }
        }
        catch (Exception ex){
            logger.info("se produjo el siguiente error: " + ex.getMessage());
        }
    }
    @Scheduled(cron = "0 0 5 1 * ?")
    public ResponseEntity AplicarDescuentoCuota(){
                /*
               Este metodo itera sobre los socios y le asigna su descuento y porcentaje a los que les corresponda
         */
        List<Socio> socios = sc.getAllSocios();
        try{
            for(Socio socio: socios) {
                if (socio.getDescuentoCuota() > 0 && socio.getDescuentoCuota() <= 100) {
                    /*
                    Se verifica que los valores que tiene asociados al atibuto descuentoCuota esten dentro
                    de los parametros acertados.
                 */
                    if (sc.VerificarExistenciaSocio(socio.getId())) {
                    /*
                        Se verifica que el socio exista en la base de datos.
                    */
                        List<Cuota> cuotasSocio = socio.getCuotas();
                        if (cuotasSocio.isEmpty()) {
                            // Evaluo que la lista de cuotas no este vacia.
                            return ResponseEntity.status(BAD_REQUEST).build();
                        }
                        for (Cuota cuota : cuotasSocio) {
                            /*
                                Itero sobre las cuotas del socio en busca de la cuota de la del mes actual
                                para aplicarle el descuento correspondiente.
                            */
                            // corregir esto usando filter...
                            if (cuota.getMesCuota().equalsIgnoreCase(sc.ObtenerMesActual()) && !cuota.isPagada()) {
                                // Verifico que el mes de la cuota sea el mismo y que ademas la cuota no haya sido pagada.
                                cuota.getPrecioCuota().setPrecio(cuota.getPrecioCuota().getPrecio() * (1 - socio.getDescuentoCuota()));
                                UpdateCuota(cuota.getId(), cuota);
                            } else {
                                return ResponseEntity.status(BAD_REQUEST).body("No se encontro la cuota dentro de la base de datos.");
                            }
                        }
                    } else {
                        return ResponseEntity.status(BAD_REQUEST).body("No se pudo encontrar al socio en la base de datos");
                    }
                } else {
                    return ResponseEntity.status(BAD_REQUEST).body("Error al verificar el descuento del socio");
                }
            }
            return ResponseEntity.status(OK).build();

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }


    public ResponseEntity PagarCuota(Cuota cuota, double monto){
        /*
        Este metodo se encarga de dar como pagada cuota, recibiendo la cuota que se quiere pagar
        y un monto que sera descontado a la cuota.
         */
        try{
            if(cr.existsById(cuota.getId())) {
                if (monto < cuota.getPrecioCuota().getPrecio() && monto > 0) {
                    // Verifico que el monto final sea menor al precio de la cuota
                    // de lo contrario estaria pagando mal y generaria inconsistencias en la base de datos

                    cuota.setPagada(true);
                    cuota.setCantidadPagada(monto);

                    UpdateCuota(cuota.getId(), cuota); // cambio los valores en la base de datos

                    return ResponseEntity.status(OK).build();

                } else {
                    // retorno el status correspondiente.
                    return ResponseEntity.status(NOT_ACCEPTABLE).build();
                }
            }
            else{
                // Si no se encontro retorno not found
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    @Scheduled(cron = "0 0 0 11 * ?")
    public void AplicarRecargo(){
        try {
            LocalDate fecha10CadaMes = LocalDate.now().withDayOfMonth(10);
            List<Cuota> cuotas = getAll();
            List<Cuota> cuotasDeuda = cuotas.stream().filter(
                    cuota -> cuota.isPagada() == false
            ).collect(Collectors.toList());
            for (Cuota cuota : cuotasDeuda) {
                if(cuota.getFechaVencimiento().before(Date.valueOf(fecha10CadaMes))){
                    cuota.getPrecioCuota().setPrecio(cuota.getPrecioCuota().getPrecio() * 1.1);
                    pcs.updatePrecioCuota(cuota.getPrecioCuota(),cuota.getPrecioCuota().getId());
                }
            }
        }catch(Exception e){
            System.out.println("Ocurrio un error" + e);
        }
    }

    private static String obtenerNombreMesActual() {
        // Obtener el mes actual
        Calendar calendario = Calendar.getInstance();
        int mesActual = calendario.get(Calendar.MONTH);

        // Obtener el nombre del mes en el idioma predeterminado del sistema
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
        String[] nombresMeses = dfs.getMonths();

        // El índice de los meses comienza en 0, por lo que restamos 1 al mes actual
        return nombresMeses[mesActual];
    }
    private static Date obtenerFechaVencimiento(LocalDate fechaActual) {
        // Obtener el mes actual
        int mesActual = fechaActual.getMonthValue();

        // Calcular el mes siguiente
        int mesSiguiente = (mesActual % 12) + 1;

        // Crear la fecha de vencimiento para el décimo día del mes siguiente
        LocalDate fechaVencimientoLocalDate = LocalDate.of(fechaActual.getYear(), mesSiguiente, 10);

        // Convertir LocalDate a Date
        return Date.valueOf(fechaVencimientoLocalDate);
    }
}

