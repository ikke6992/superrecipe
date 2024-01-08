package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {
}
