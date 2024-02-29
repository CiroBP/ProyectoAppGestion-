package clubCamaronesManagments.demo.Services;

import clubCamaronesManagments.demo.Models.Cuota;
import clubCamaronesManagments.demo.Models.Socio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
@Service
public class CuotaSocioServiceImp implements CuotaSocioService {
    private final SocioService ss;
    @Autowired
    public CuotaSocioServiceImp(SocioService ss) {
        this.ss = ss;

    }
    @Override
    public List<Socio> getAllSocios() {
        return ss.getAll();
    }
    @Override
    public boolean VerificarExistenciaSocio(int id) {
        return ss.VerificarExistenciaSocio(id);
    }

    @Override
    public String ObtenerMesActual() {
        return ss.ObtenerMesActual();
    }

    @Override
    public double obtenerPrecioCuota() {
        return 0;
    }

}
