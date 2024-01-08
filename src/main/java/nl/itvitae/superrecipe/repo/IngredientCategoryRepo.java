package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientCategoryRepo extends JpaRepository<IngredientCategory, Long> {
}
