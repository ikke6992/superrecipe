package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientCategoryRepo extends JpaRepository<IngredientCategory, Long> {
}
