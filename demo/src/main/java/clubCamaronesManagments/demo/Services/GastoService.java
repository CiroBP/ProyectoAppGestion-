package clubCamaronesManagments.demo.Services;

import clubCamaronesManagments.demo.Models.Gasto;
import clubCamaronesManagments.demo.Models.Proveedor;
import clubCamaronesManagments.demo.Repositorys.GastoRepository;
import clubCamaronesManagments.demo.Repositorys.ProveedorRepository;
import clubCamaronesManagments.demo.Utilities.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Service
public class GastoService {

    private final GastoRepository gr;
    private RandomNumberGenerator random;
    private List<Gasto> gastos; // Esta lista esta sirve para no tener que instanciar tantas listas dentro de las funciones internamente
    private final ProveedorRepository pr;
    @Autowired
    public GastoService(GastoRepository gr, ProveedorRepository pr){
        this.gr = gr;
        this.gastos = new ArrayList<>();
        this.random = new RandomNumberGenerator(new Random(), this.gr);
        this.pr = pr;
    }

    public List<Gasto> getAll(){
        try{
            return (List<Gasto>) gr.findAll();
        }
        catch(Exception e){
            return (List<Gasto>) new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity SaveGasto(Gasto gasto){
        try{
            gasto.setId(random.asignarId());
            gr.save(gasto);
            return ResponseEntity.status(CREATED).build();
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity UpdateGasto(int id, Gasto gasto){
        try{
            if (gr.existsById(id)) {
                Gasto g = gr.findById(id).get();
                g.setNombreGasto(gasto.getNombreGasto());
                g.setDetalleGasto(gasto.getDetalleGasto());
                g.setMontoGasto(gasto.getMontoGasto());
                g.setMetodoPago(gasto.getMetodoPago());
                g.setFecha(gasto.getFecha());
                g.setProveedor(gasto.getProveedor());
                gr.save(g);
                return ResponseEntity.status(OK).build();
            }
            else {
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity DeleteGasto(int id){
        try{
            if (gr.existsById(id)){
                gr.deleteById(id);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastoXFecha(String fecha){
        /*
        Esta funcion recibe como parametro una fecha y retorna los gastos realizados
        en tal fecha.
         */
        try{
            this.gastos = vaciarLista();
            this.gastos = gr.findGastoByFecha(convertirStringADate(fecha));

            if (gastos.isEmpty()){
                return new ResponseEntity<>(NOT_FOUND);
            }
            return new ResponseEntity<>(this.gastos, OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastoXMes(String mes){
        /*
            Esta funcion recibe como parametro un mes que podria ser "enero" por ej
            y va retornar una lista con todos los gastos registrados desde el principio al
            fin de tal mes.
         */
        try{
            Date fechaInicioMes = ConvertirMesADate(mes);

            return new ResponseEntity<>(gr.findGastoByMes(fechaInicioMes), OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastoXFechaYProovedorDeterminado(String fecha, Integer idProveedor){
        /*
           Esta funcion recibe como parametros una fecha y proveedor determinado retornando
           una lista con todos los gastos asociados a tal proveedor determinados en esa fecha.
         */
        try{
            this.gastos = vaciarLista();
            if (pr.existsById(idProveedor)) {
                Proveedor proveedor = pr.findById(idProveedor).get();
                this.gastos = gr.findByGastoProveedorAndFecha(proveedor, convertirStringADate(fecha));
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(gastos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(this.gastos, OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastosEntreFechas(String fechaInicial, String fechaFinal){
        /*
        Esta funcion recibe como parametro 2 fechas una de inicio y otra de finalizacion y
        se encarga de retornar todos los gastos realizados entre esas 2 fechas.
         */
        try{
            this.gastos = vaciarLista();
            gastos.add((Gasto) gr.findAll());
            List<Gasto> gastosEntreFechas = gastos.stream().filter(
                    gasto -> gasto.getFecha().after(convertirStringADate(fechaInicial)) && gasto.getFecha().before(convertirStringADate(fechaFinal))
            ).collect(Collectors.toList());
            if(gastosEntreFechas.isEmpty()){
                return new ResponseEntity<>(NOT_FOUND);
            }
            return new ResponseEntity<>(gastosEntreFechas, OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Gasto>> ObtenerGastosTotalesPorProovedor(Proveedor proveedor){
        /*
        Esta funcion recibe un proveedor como parametro y retornanara todos los gastos
        relacionados a ese proveedor.
         */
        try{
            this.gastos = vaciarLista();
            this.gastos = gr.findGastosByProveedor(proveedor);
            if(gastos.isEmpty()){
                return new ResponseEntity<>(NOT_FOUND);
            }
            return new ResponseEntity<>(this.gastos, OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private static Date ConvertirMesADate(String mes) {
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("MMMM");
            Date fecha = formatoEntrada.parse(mes);
            calendar.setTime(fecha);
            calendar.set(Calendar.YEAR, year);
            fecha = calendar.getTime();
            return fecha;
        } catch (ParseException p) {
            throw new IllegalArgumentException("Formato de mes no válido: " + mes);
        }
    }
    private Date convertirStringADate(String cadena){
        try{
            String patron = "yyyy-MM-dd";
            SimpleDateFormat formato = new SimpleDateFormat(patron);
            Date fecha = formato.parse(cadena);
            return fecha;
        }catch (Exception e){
            throw new IllegalArgumentException("Formato no valido: "+ cadena + " No se puede convertir a fecha.");
        }
    }

    private List<Gasto> vaciarLista(){
        /*
        Esta funcion se encarga de vaciar la lista o retornarla si ya esta vacia.
         */
        if(this.gastos.isEmpty()){
            return this.gastos;
        }
        else{
            this.gastos.clear();
            return this.gastos;
        }
    }
}
