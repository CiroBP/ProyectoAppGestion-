package clubCamaronesManagments.demo.Services;

import clubCamaronesManagments.demo.Models.Cuota;
import clubCamaronesManagments.demo.Models.Socio;

import java.util.List;

public interface CuotaSocioService {
    List<Socio> getAllSocios();
    boolean VerificarExistenciaSocio(int id);
    String ObtenerMesActual();
    double obtenerPrecioCuota();
}
