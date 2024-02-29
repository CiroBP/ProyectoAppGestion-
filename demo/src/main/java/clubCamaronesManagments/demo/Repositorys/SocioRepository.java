package clubCamaronesManagments.demo.Repositorys;

import clubCamaronesManagments.demo.Models.Socio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocioRepository extends CrudRepository<Socio, Integer> {
    @Override
    boolean existsById(Integer integer);

    @Query("SELECT s FROM Socio s WHERE s.categoria LIKE %:categoriaSocio%")
    List<Socio> BuscarSocioPorCategoria(@Param("categoriaSocio") String categoriaSocio);

    // Separacion por legibilidad

    @Query("SELECT s FROM Socio s WHERE LOWER(s.diciplina) LIKE LOWER(CONCAT('%', :diciplina, '%'))")
    List<Socio> buscarPorDiciplina(@Param("diciplina") String diciplina);





}
