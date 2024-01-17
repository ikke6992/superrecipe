package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Recipe;
import nl.itvitae.superrecipe.model.User;
import nl.itvitae.superrecipe.repo.RecipeRepo;
import nl.itvitae.superrecipe.repo.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final UserRepo userRepo;

    @GetMapping("/")
    public List<Recipe> getAll() {
        return recipeRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Recipe> getOne(@PathVariable("id") long id) {
        return recipeRepo.findById(id);
    }

    @Deprecated
    public List<Recipe> searchAll(@RequestParam("q") String query) {
        return recipeRepo.findAllWhereKeywordMatch(query + "%");
    }

    @GetMapping("/search")
    public List<String> searchAll() {
        return recipeRepo.findAllNames();
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody Recipe recipe) {
        recipeRepo.save(recipe);
        // noinspection DataFlowIssue
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/{id}/favorite")
    public Optional<Recipe> addToFavorite(@PathVariable("id") long id, Authentication authentication) {
        try {
            Recipe recipe = recipeRepo.findById(id).orElseThrow();
            String username = (String) authentication.getPrincipal();
            User user = userRepo.findByUsername(username).orElseThrow();
            user.addToFavorite(recipe);
            return Optional.of(recipe);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    @DeleteMapping("/{id}/favorite")
    public ResponseEntity<?> removeFromFavorite(@PathVariable("id") long id, Authentication authentication) {
        try {
            Recipe recipe = recipeRepo.findById(id).orElseThrow();
            String username = (String) authentication.getPrincipal();
            User user = userRepo.findByUsername(username).orElseThrow();
            return user.removeFavorite(recipe) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
