package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepo extends JpaRepository<Keyword, Long> {
}
