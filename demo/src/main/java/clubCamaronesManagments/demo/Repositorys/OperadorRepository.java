package clubCamaronesManagments.demo.Repositorys;

import clubCamaronesManagments.demo.Models.Operador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperadorRepository extends CrudRepository<Operador, Integer> {
    @Override
    boolean existsById(Integer integer);
}
