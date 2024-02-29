package clubCamaronesManagments.demo.Controllers;

import clubCamaronesManagments.demo.Models.Proveedor;
import clubCamaronesManagments.demo.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/proveedor")
public class ProveedorController{
    @Autowired
    private ProveedorService ps;

    @GetMapping("")
    public List<Proveedor> GetAll(){
        return (List<Proveedor>) ps.getAll();
    }
    @PostMapping("")
    public ResponseEntity SaveProveedor(@RequestBody Proveedor proovedor){
        return ps.SaveProveedor(proovedor);
    }
    @PutMapping("/{id}/actualizar")
    public ResponseEntity UpdateProveedor(@PathVariable int id,@RequestBody Proveedor proovedor){
        return ps.UpdateProveedor(id,proovedor);
    }
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity DeleteProveedor(@PathVariable int id){
        return ps.DeleteProveedor(id);
    }
}
