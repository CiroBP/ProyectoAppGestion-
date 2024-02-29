package clubCamaronesManagments.demo.Services;

import clubCamaronesManagments.demo.Models.Operador;
import clubCamaronesManagments.demo.Repositorys.OperadorRepository;
import clubCamaronesManagments.demo.Utilities.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class OperadorService {


    private final OperadorRepository or;
    private RandomNumberGenerator random;
    @Autowired
    public OperadorService(OperadorRepository or){
        this.or = or;
        this.random = new RandomNumberGenerator(new Random(), this.or);

    }
    public List<Operador> getAll(){
        try{
            return (List<Operador>) or.findAll();
        }
        catch(Exception e){
            return (List<Operador>) new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity SaveOperador (Operador operador){
        try{
            operador.setId(random.asignarId());
            or.save(operador);
            return ResponseEntity.status(CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity UpdateOperador (int id, Operador operador){
        try{
            if (or.existsById(id)) {
                Operador ope = or.findById(id).get();
                ope.setUserName(operador.getUserName());
                ope.setContrasenia(operador.getContrasenia());
                ope.setPermisoTotal(operador.isPermisoTotal());
                ope.setContacto(operador.getContacto());
                or.save(ope);
                return ResponseEntity.status(OK).build();
            }
            else{
                return ResponseEntity.status(NOT_FOUND).build();
            }

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity DeleteOperador (int id){
        try{
            if (or.existsById(id)){
                or.deleteById(id);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }


}
