package samples.second.repositories;

import org.springframework.data.repository.CrudRepository;
import samples.second.entities.SecondEntity;

public interface SecondEntityRepository extends CrudRepository<SecondEntity, Integer> {
}
