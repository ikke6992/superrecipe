package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface KeywordRepo extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByValue(String string);
}
