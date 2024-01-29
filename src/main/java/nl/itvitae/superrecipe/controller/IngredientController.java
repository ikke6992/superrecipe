package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Ingredient;
import nl.itvitae.superrecipe.model.IngredientCategory;
import nl.itvitae.superrecipe.model.UnitValue;
import nl.itvitae.superrecipe.repo.IngredientCategoryRepo;
import nl.itvitae.superrecipe.repo.IngredientRepo;
import nl.itvitae.superrecipe.repo.UnitValueRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientRepo ingredientRepo;
    private final IngredientCategoryRepo ingredientCategoryRepo;
    private final UnitValueRepo unitValueRepo;

    @GetMapping("/")
    public List<Ingredient> getAll() {
        return ingredientRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Ingredient> getById(@PathVariable long id) {
        return ingredientRepo.findById(id);
    }

    private record IngredientData(String name, String category, String unit) {}
    @PostMapping("/new")
    public ResponseEntity<Ingredient> add(@RequestBody IngredientData ingredientData, UriComponentsBuilder ucb) {
        Optional<IngredientCategory> category = ingredientCategoryRepo.findByName(ingredientData.category);
        Optional<UnitValue> unit = unitValueRepo.findByName(ingredientData.unit);

        if (!ingredientData.name.isEmpty() && category.isPresent() && unit.isPresent()) {
            Ingredient ingredient = new Ingredient(ingredientData.name, category.get(), unit.get());
            ingredientRepo.save(ingredient);
            URI locationOfNewIngredient = ucb
                    .path("/api/ingredients/{id}")
                    .buildAndExpand(ingredientRepo.findAll().size())
                    .toUri();
            return ResponseEntity.created(locationOfNewIngredient).body(ingredient);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}
