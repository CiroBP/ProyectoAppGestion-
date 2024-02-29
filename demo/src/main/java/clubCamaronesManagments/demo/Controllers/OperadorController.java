package clubCamaronesManagments.demo.Controllers;

import clubCamaronesManagments.demo.Models.Operador;
import clubCamaronesManagments.demo.Services.OperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operador")

public class OperadorController {

    @Autowired
    private OperadorService os;

    @GetMapping("")
    public List<Operador> GetAll(){
        return (List<Operador>) os.getAll();
    }
    @PostMapping("")
    public ResponseEntity SaveOperador(@RequestBody Operador operador){
        return os.SaveOperador(operador);
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity UpdateSocio(@PathVariable int id,@RequestBody Operador operador){
        return os.UpdateOperador(id,operador);
    }

    @PostMapping("/{id}/eliminar")
    public ResponseEntity DeleteOperador(@PathVariable int id){
        return os.DeleteOperador(id);
    }

}
