package clubCamaronesManagments.demo.Repositorys;


import clubCamaronesManagments.demo.Models.Cuota;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CuotaRepository extends CrudRepository<Cuota, Integer> {
    @Override
    boolean existsById(Integer integer);

}
