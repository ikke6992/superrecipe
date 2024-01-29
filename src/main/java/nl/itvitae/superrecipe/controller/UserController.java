package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Recipe;
import nl.itvitae.superrecipe.repo.RecipeRepo;
import nl.itvitae.superrecipe.repo.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserRepo userRepo;
    private final RecipeRepo recipeRepo;

    @GetMapping("/")
    public String hello() { return "Hello user 0954"; }

    private record RecipeData(String name) {}
    @PutMapping("/add")
    public ResponseEntity<Recipe> addFavorite(@RequestBody RecipeData recipeData,
                                              UriComponentsBuilder ucb) {
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Recipe> removeFavorite(@RequestBody RecipeData recipeData,
                                                 UriComponentsBuilder ucb) {
        return ResponseEntity.badRequest().build();
    }
}
