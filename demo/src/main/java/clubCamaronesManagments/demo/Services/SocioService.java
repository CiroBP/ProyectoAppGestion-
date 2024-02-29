package clubCamaronesManagments.demo.Services;

import clubCamaronesManagments.demo.Models.Cuota;
import clubCamaronesManagments.demo.Models.Socio;
import clubCamaronesManagments.demo.Repositorys.SocioRepository;
import clubCamaronesManagments.demo.Utilities.RandomNumberGenerator;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Service
public class SocioService {
    private final SocioRepository sr;
    private List<Socio> socios; // Habia notado una mala practica en 2 metodos por lo que implemente esto.
    private final Logger logger = LoggerFactory.getLogger(SocioService.class);
    private RandomNumberGenerator random;

    @Autowired
    public SocioService(SocioRepository sr) {
        this.sr = sr;
        this.socios = new ArrayList<>();
        this.random = new RandomNumberGenerator(new Random(), this.sr);
        asignarCategoriaSocios();
        /*
        Dejo las pruebas por aca si queres ver algo por consola
         */

    }

    public List<Socio> getAll() {
        try {
            return (List<Socio>) sr.findAll();
        } catch (Exception e) {
            return (List<Socio>) new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public List<Socio> getAllByDiciplina(String diciplina) {
        /*
        Este metodo se encarga de buscar socios por una diciplina, en la consulta de la base de datos
        se encarga de pasar a minusculas todo.
         */
        try {
            return (List<Socio>) sr.buscarPorDiciplina(diciplina);
        } catch (Exception e) {
            return (List<Socio>) new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity SaveSocio(Socio socio) {
        try {
            socio.setId(random.asignarId());
            sr.save(socio);
            return ResponseEntity.status(CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity UpdateSocio(int id, Socio socio) {
        try {
            if (sr.existsById(id)) {
                Socio soc = sr.findById(id).get();
                soc.setNombre(socio.getNombre());
                soc.setApellido(socio.getApellido());
                soc.setContacto(socio.getContacto());
                soc.setFechaNacimiento(socio.getFechaNacimiento());
                soc.setDescuentoCuota(socio.getDescuentoCuota());
                soc.setCategoria(socio.getCategoria());
                soc.setDiciplina(socio.getDiciplina());
                soc.setCuotas(socio.getCuotas());
                sr.save(soc);
                return ResponseEntity.status(OK).build();
            } else {
                return ResponseEntity.status(NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Boolean> DeleteSocio(int id) {
        /*
        Solo se pueden eliminar socio que no deban cuotas, ademas este metodo devolvera una ResponseEntity
        y un booleano dependendiendo de la situacion.
         */
        try {
            Optional<Socio> socioTemp = sr.findById(id); // Creo un socio temporal.
            // No me dejaba manejar socioTemp como yo queria por la clase "Optional".
            if (socioTemp.isPresent()) { // Verifico que el socio haya sido encontrado
                Socio socio = socioTemp.get(); // asigno ese socio
                for (Cuota cuota : socio.getCuotas()) {
                    if (!cuota.isPagada()) {
                        // Verifico que almenos una cuota no haya sido pagada, ya con eso es suficiente para no poder eliminar el socio.
                        return ResponseEntity.status(CONFLICT).body(false);
                    }
                }
                sr.deleteById(id);
                return ResponseEntity.status(OK).body(true);
            } else {
                // De lo contrario devuelvo el status correspondiente
                return ResponseEntity.status(NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<Socio> BuscarSocioPorCategoria(String categoria) {
        /*
        Este metodo recibe como parametro una categoria y retorna una lista con los socios dependiendo de
        la categoria seleccionada
         */
        try {
            return (List<Socio>) sr.BuscarSocioPorCategoria(categoria);
        } catch (Exception e) {
            return (List<Socio>) new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public boolean VerificarExistenciaSocio(int id) {
        return sr.existsById(id);
    }

    public String ObtenerMesActual() {
        /*
        Este metodo se encarga de obtener el mes actual en formato string por ej: "diciembre"
         */
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        SimpleDateFormat formatoMes = new SimpleDateFormat("MMMM");
        String mesActual = formatoMes.format(fechaActual);
        return mesActual;
    }

    public void asignarCategoriaSocios() {
        socios.clear();
        socios = getAll();
        for (Socio socio : socios) {
            // Itero sobre la lista de socios.
            LocalDate localDate = LocalDate.from(socio.getFechaNacimiento().toInstant().atZone(java.time.ZoneId.systemDefault()));
            socio.setCategoria(ubicarCategoriaSocio(parseLocalDateToString(localDate),parseLocalDateToString(LocalDate.now())));
            UpdateSocio(socio.getId(), socio);

        }
    }
    public ResponseEntity<List<Cuota>> obtenerCuotasSocio(Socio socio){
        try{
            if(sr.existsById(socio.getId())){
                List<Cuota> cuotasSocio = socio.getCuotas();
                return ResponseEntity.status(OK).body(cuotasSocio);
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    private String parseLocalDateToString(LocalDate localDate) {
        /*
        Este metodo recibe como parametro un dato de tipo localdate (yyyy-mm-dd) y retorna
        un dato de tipo String con unicamente el año
        por ej:
        Recibe "2023-01-01".
        Retorna "2023".
         */
        try {
            String fecha = localDate.toString();
            String result = "";
            for (int i = 0; i < 4; i++) {
                result = result + fecha.charAt(i);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al convertir los datos.");
        }

    }

    private String ubicarCategoriaSocio(String anioNacimientoSocio, String anioActual) {
        /*
        Este metodo recibe como parametro 2 enteros el año de nacimiento del socio, y el anio actual
        luego le resta el año de nacimiento al año actual y en base a eso determina la categoria del
        socio, devolviendo como resultado un String con la categoria correspondiente
         */
        try {
            int result = Integer.parseInt(anioActual) - Integer.parseInt(anioNacimientoSocio);
            if (result < 0) {
                return "";
            } else if (result < 7) {
                return "Baby Camarones";
            } else if (result == 8) {
                return "M-8";
            } else if (result == 9) {
                return "M-9";
            } else if (result == 10) {
                return "M-10";
            } else if (result == 11) {
                return "M-11";
            } else if (result == 12) {
                return "M-12";
            } else if (result == 13) {
                return "M-13";
            } else if (result == 14) {
                return "M-14";
            } else if (result == 15) {
                return "M-15";
            } else if (result == 16) {
                return "M-16";
            } else if (result == 17) {
                return "M-17";
            } else if (result >= 18 && result <= 19) {
                return "M-19";
            } else if (result > 19 && result < 40) {
                return "Plantel Superior";
            } else if (result > 40) {
                return "Veterano";
            } else {
                return "";
            }
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al convertir los datos.");
        }
    }
}
