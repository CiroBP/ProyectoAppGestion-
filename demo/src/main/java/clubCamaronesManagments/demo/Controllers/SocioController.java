package clubCamaronesManagments.demo.Controllers;

import clubCamaronesManagments.demo.Models.Cuota;
import clubCamaronesManagments.demo.Models.Socio;
import clubCamaronesManagments.demo.Services.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/socio")
public class SocioController {

    @Autowired
    private SocioService ss;

    @GetMapping("")
    public List<Socio> GetAll(){
        return  ss.getAll();
    }
    @GetMapping("/{diciplina}/bydiciplina")
    public List<Socio> getAllByDicipilna(@PathVariable String diciplina){
        System.out.println("hila");
        return (List<Socio>) ss.getAllByDiciplina(diciplina);
    }
    @PostMapping("")
    public ResponseEntity SaveSocio(@RequestBody Socio socio){
        return ss.SaveSocio(socio);
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity UpdateSocio(@PathVariable int id,@RequestBody Socio socio){
        return ss.UpdateSocio(id,socio);
    }

    @DeleteMapping ("/{id}/eliminar")
    public ResponseEntity DeleteSocio(@PathVariable int id){
        return ss.DeleteSocio(id);
    }


    @GetMapping("/{categoria}/Categoria")
    public List<Socio> BuscarSocioPorCategoria(@PathVariable String categoria){
        return (List<Socio>) ss.BuscarSocioPorCategoria(categoria);
    }
    @GetMapping("/{socio}/obtenerCuota")
    public ResponseEntity<List<Cuota>> obtenerCuotaSocio(@RequestBody Socio socio){
        return ss.obtenerCuotasSocio(socio);
    }

}
