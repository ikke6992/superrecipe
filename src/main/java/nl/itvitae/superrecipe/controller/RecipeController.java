package nl.itvitae.superrecipe.controller;

import nl.itvitae.superrecipe.model.Recipe;
import nl.itvitae.superrecipe.repo.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    @Autowired
    public RecipeRepo recipeRepo;

    @GetMapping("/")
    public List<Recipe> getAll() {
        return recipeRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Recipe> getOne(@PathVariable("id") long id) {
        return recipeRepo.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody Recipe recipe) {
        recipe = recipeRepo.save(recipe);
        return ResponseEntity.created(null).build();
    }
}
