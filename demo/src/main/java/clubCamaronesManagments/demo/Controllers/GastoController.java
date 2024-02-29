package clubCamaronesManagments.demo.Controllers;

import clubCamaronesManagments.demo.Models.Gasto;
import clubCamaronesManagments.demo.Models.Proveedor;
import clubCamaronesManagments.demo.Services.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/gasto")

public class GastoController {

    @Autowired
    private GastoService gs;

    public GastoController(GastoService gs) {
        this.gs = gs;
    }

    @GetMapping("")
    public List<Gasto> getAll() {
        return (List<Gasto>) gs.getAll();
    }

    @PostMapping("")
    public ResponseEntity saveGasto(@RequestBody Gasto gasto) {
        return gs.SaveGasto(gasto);
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity updateGasto(@PathVariable int id, @RequestBody Gasto gasto) {
        return gs.UpdateGasto(id, gasto);
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity deleteGasto(@PathVariable int id) {
        return gs.DeleteGasto(id);
    }

    @GetMapping("/{fecha}/porFecha")
    public ResponseEntity obtenerGastoXFecha(@PathVariable  String fecha) {
        System.out.println("Entro");
        return gs.ObtenerGastoXFecha(fecha);
    }

    @GetMapping("/{mes}/porMes")
    public ResponseEntity obtenerGastoXMes(@PathVariable String mes) {

        System.out.println(mes);
        return gs.ObtenerGastoXMes(mes);
    }

    @GetMapping("/porFechaYProveedor")
    public ResponseEntity obtenerGastoXFechaYProovedorDeterminado(
            @RequestParam String fecha, @RequestParam Integer idProveedor) {
        return gs.ObtenerGastoXFechaYProovedorDeterminado(fecha, idProveedor);
    }

    @GetMapping("/entreFechas")
    public ResponseEntity obtenerGastosEntreFechas(
            @RequestParam String fechaInicio, @RequestParam String fechaFinal) {
        return gs.ObtenerGastosEntreFechas(fechaInicio, fechaFinal);
    }

    @GetMapping("/totalPorProveedor")
    public ResponseEntity obtenerGastosTotalesPorProovedor(@RequestParam Proveedor proveedor) {
        return gs.ObtenerGastosTotalesPorProovedor(proveedor);
    }
    /*
    NOTA: cambiar los parametros que reciben proveedores como parametro por sus id, o mas bien
    revisar cual es mas conveniente
     */
}

