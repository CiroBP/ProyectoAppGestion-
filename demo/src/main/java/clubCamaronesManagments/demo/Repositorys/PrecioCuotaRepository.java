package clubCamaronesManagments.demo.Repositorys;

import clubCamaronesManagments.demo.Models.PrecioCuota;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioCuotaRepository extends CrudRepository<PrecioCuota, Integer> {
    @Override
    boolean existsById(Integer integer);
    @Query("SELECT pc FROM PrecioCuota pc ORDER BY pc.id DESC")
    PrecioCuota findTopByOrderByIdDesc();
}
