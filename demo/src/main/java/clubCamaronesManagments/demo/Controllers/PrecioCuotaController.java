package clubCamaronesManagments.demo.Controllers;


import clubCamaronesManagments.demo.Models.PrecioCuota;
import clubCamaronesManagments.demo.Services.PrecioCuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/precioCuota")
public class PrecioCuotaController {
    @Autowired
    private PrecioCuotaService precioCuotaService;
    @GetMapping("")
    public List<PrecioCuota> getAll(){
        return precioCuotaService.getAll();
    }

    @PutMapping("/{id}/update")
    public ResponseEntity updatePrecioCuota(@PathVariable int id, @RequestBody PrecioCuota precioCuota){
        return precioCuotaService.updatePrecioCuota(precioCuota, id);
    }

}
