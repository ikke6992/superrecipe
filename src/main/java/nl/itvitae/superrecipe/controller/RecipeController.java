package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Recipe;
import nl.itvitae.superrecipe.repo.RecipeRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/recipes")
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
    public List<Recipe> searchAll(@RequestParam("q") String query) {
        return recipeRepo.findAllWhereKeywordMatch(query + "%");
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody Recipe recipe) {
        recipeRepo.save(recipe);
        //noinspection DataFlowIssue
        return ResponseEntity.created(null).build();
    }
}
