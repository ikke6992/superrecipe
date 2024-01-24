package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
