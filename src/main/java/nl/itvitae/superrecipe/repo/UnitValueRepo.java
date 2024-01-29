package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.UnitValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitValueRepo extends JpaRepository<UnitValue, Long> {

    Optional<UnitValue> findByName(String name);
}
