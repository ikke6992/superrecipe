package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Recipe;
import nl.itvitae.superrecipe.repo.RecipeRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeRepo recipeRepo;

    @GetMapping("/")
    public List<Recipe> getAll() {
        return recipeRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Recipe> getOne(@PathVariable("id") long id) {
        return recipeRepo.findById(id);
    }

    @GetMapping("/search")
    public Iterable<String> searchAll() {
        return recipeRepo.findAllNames();
    }

    private record RecipeData(String name) {}
    @PostMapping("/new")
    public ResponseEntity<Recipe> add(@RequestBody RecipeData recipeData, UriComponentsBuilder ucb) {
        if (!recipeData.name.isEmpty()) {
            Recipe recipe = new Recipe(recipeData.name);
            recipeRepo.save(recipe);
            URI locationOfNewRecipe = ucb
                    .path("/api/ingredients/{id}")
                    .buildAndExpand(recipeRepo.findAll().size())
                    .toUri();
            return ResponseEntity.created(locationOfNewRecipe).body(recipe);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
