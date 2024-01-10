package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(String ingredient);
}
