package samples.first.repositories;

import org.springframework.data.repository.CrudRepository;
import samples.first.entities.FirstEntity;

public interface FirstEntityRepository extends CrudRepository<FirstEntity, Integer> {
}
