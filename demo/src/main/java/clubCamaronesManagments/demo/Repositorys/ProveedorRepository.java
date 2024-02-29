package clubCamaronesManagments.demo.Repositorys;

import clubCamaronesManagments.demo.Models.Proveedor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends CrudRepository<Proveedor,Integer> {
    @Override
    boolean existsById(Integer integer);
}
