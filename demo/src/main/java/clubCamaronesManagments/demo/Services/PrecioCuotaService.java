package clubCamaronesManagments.demo.Services;

import clubCamaronesManagments.demo.Models.PrecioCuota;
import clubCamaronesManagments.demo.Repositorys.PrecioCuotaRepository;
import clubCamaronesManagments.demo.Utilities.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.*;

@Service
public class PrecioCuotaService {
    private final PrecioCuotaRepository pcr;
    private RandomNumberGenerator random;
    @Autowired
    public PrecioCuotaService(PrecioCuotaRepository pcr){
        this.pcr = pcr;
        this.random = new RandomNumberGenerator(new Random(),pcr);
    }
    public List<PrecioCuota> getAll(){
        try{
            return (List<PrecioCuota>) pcr.findAll();
        }catch (Exception e){
            return  (List<PrecioCuota>) new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity savePrecioCuota(PrecioCuota precioCuota){
        try{
            if(precioCuota.getPrecio() > 0) {
                // verifico que no sean tan pelotudos de guardar el precio de la cuota en 0
                precioCuota.setId(random.asignarId());
                pcr.save(precioCuota);
                return ResponseEntity.status(OK).body("El precio fue guardado correctamente");
            }
            else{
                return ResponseEntity.status(BAD_REQUEST).body("Ocurrio un error al guardar el precio de la cuota" +
                        " Recuerde que los valores deben de ser mayores a 0.");
            }
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity updatePrecioCuota(PrecioCuota precioCuota, int id){
        try{
            if(pcr.existsById(id)){
                PrecioCuota pc = pcr.findById(id).get();
                pc.setPrecio(precioCuota.getPrecio());
                pcr.save(pc);
                return ResponseEntity.status(OK).build();
            }
            else{
                return ResponseEntity.status(BAD_REQUEST).body("Ocurrio un error al actualizas los precios de las cuotas");
            }
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    public Double obtenerPrecioActual() {

        PrecioCuota ultimoPrecio = pcr.findTopByOrderByIdDesc();

        if (ultimoPrecio != null) {
            return ultimoPrecio.getPrecio();
        } else {
            return 0.0;
        }
    }

}
