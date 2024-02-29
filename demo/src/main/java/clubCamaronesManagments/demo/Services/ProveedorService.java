package clubCamaronesManagments.demo.Services;

import clubCamaronesManagments.demo.Models.Proveedor;
import clubCamaronesManagments.demo.Repositorys.ProveedorRepository;
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
public class ProveedorService {

    private final ProveedorRepository pr;
    private RandomNumberGenerator random;
    @Autowired
    public ProveedorService(ProveedorRepository pr){
        this.pr = pr;
        this.random = new RandomNumberGenerator(new Random(), pr);

    }
    public List<Proveedor> getAll(){
        try{
            return (List<Proveedor>) pr.findAll();
        }
        catch(Exception e){
            return (List<Proveedor>) new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity SaveProveedor(Proveedor proveedor){
        try{
            proveedor.setId(random.asignarId());
            pr.save(proveedor);
            return ResponseEntity.status(CREATED).build();
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity UpdateProveedor(int id, Proveedor proveedor){
        try{
            if (pr.existsById(id)) {
                Proveedor pro = pr.findById(id).get();
                pro.setNombre(proveedor.getNombre());
                pro.setCategoria(proveedor.getCategoria());
                pro.setGastos(proveedor.getGastos());
                pr.save(pro);
                return ResponseEntity.status(OK).build();
            }
            else {
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity DeleteProveedor(int id){
        try{
            if (pr.existsById(id)){
                pr.deleteById(id);
                return ResponseEntity.status(OK).build();
            }else{
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}

