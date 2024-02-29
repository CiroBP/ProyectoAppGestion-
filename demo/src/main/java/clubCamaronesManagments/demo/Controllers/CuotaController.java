package clubCamaronesManagments.demo.Controllers;

import clubCamaronesManagments.demo.Models.Cuota;
import clubCamaronesManagments.demo.Services.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuota")

public class CuotaController {

    @Autowired
    private CuotaService cs;

    @GetMapping("")
    public ResponseEntity<List<Cuota>> getAll() {
        List<Cuota> cuotas = (List<Cuota>) cs.getAll();
        return ResponseEntity.ok(cuotas);
    }

    @PostMapping("/save")
    public ResponseEntity SaveCuota(@RequestBody Cuota cuota) {
        return cs.SaveCuota(cuota);
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity UpdateCuota(@PathVariable int id, @RequestBody Cuota cuota) {
        return cs.UpdateCuota(id, cuota);
    }

    @PostMapping("/{id}/eliminar")
    public ResponseEntity DeleteCuota(@PathVariable int id) {
        return cs.DeleteCuota(id);
    }

    @PutMapping("/{monto}/pay")
    public ResponseEntity pagarCuota(@RequestBody Cuota cuota, @PathVariable double monto) {
        return cs.PagarCuota(cuota, monto);
    }
}