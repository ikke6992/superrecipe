package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {


    public Optional<User> findByUsername(String username);
}
