package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {
    @Query(
        value = "SELECT * FROM recipe WHERE name IN (SELECT name FROM keyword WHERE name LIKE ?1)",
        nativeQuery = true
    )
    List<Recipe> findAllWhereKeywordMatch(String name);
}
