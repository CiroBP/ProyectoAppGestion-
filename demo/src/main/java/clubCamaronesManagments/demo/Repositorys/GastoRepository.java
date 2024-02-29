package clubCamaronesManagments.demo.Repositorys;

import clubCamaronesManagments.demo.Models.Gasto;
import clubCamaronesManagments.demo.Models.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GastoRepository extends CrudRepository<Gasto,Integer> {
    @Override
    boolean existsById(Integer integer);
    @Query("SELECT g FROM Gasto g WHERE DATE(g.fecha) = DATE(:fecha)")
    List<Gasto> findGastoByFecha(@Param("fecha") Date fecha);

    //Separacion por legibilidad

    @Query("SELECT g FROM Gasto g WHERE YEAR(g.fecha) = YEAR(:fechaInicioMes) AND MONTH(g.fecha) = MONTH(:fechaInicioMes)")
    List<Gasto> findGastoByMes(@Param("fechaInicioMes") Date fechaInicioMes);


    //Separacion por legibilidad

    @Query("SELECT g FROM Gasto g WHERE g.proveedor = :proveedor AND DATE(g.fecha) = DATE(:fecha)")
    List<Gasto> findByGastoProveedorAndFecha(@Param("proveedor") Proveedor proveedor, @Param("fecha") Date fecha);

    //Separacion por legibilidad

    @Query("SELECT g FROM Gasto g WHERE g.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Gasto> findGastosEntreFechas(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    //Separacion por legibilidad

    @Query("SELECT g FROM Gasto g WHERE g.proveedor = :proveedor")
    List<Gasto> findGastosByProveedor(@Param("proveedor") Proveedor proveedor);
}
