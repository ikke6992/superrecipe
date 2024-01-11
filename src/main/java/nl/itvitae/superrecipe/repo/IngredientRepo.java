package nl.itvitae.superrecipe.repo;

import nl.itvitae.superrecipe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(String ingredient);
}
