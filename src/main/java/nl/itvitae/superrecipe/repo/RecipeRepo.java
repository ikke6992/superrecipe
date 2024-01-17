package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {
    @Query(
        value = "SELECT r1_0.id,r1_0.instructions,r1_0.kitchen,r1_0.name,r1_0.preparation_method,r1_0.type FROM recipes r1_0 JOIN (keywords k1_0 JOIN recipe_keywords r2_0 ON k1_0.id = r2_0.keyword_id) ON r1_0.id = r2_0.recipe_id WHERE k1_0.value IN (SELECT value FROM keywords WHERE value LIKE ?1)",
        nativeQuery = true
        // This functionality doesn't work yet and will be left alone
    )
    List<Recipe> findAllWhereKeywordMatch(String name);

    @Query("SELECT name FROM recipes")
    List<String> findAllNames();
}
