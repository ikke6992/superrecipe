package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Ingredient;
import nl.itvitae.superrecipe.repo.IngredientRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientRepo ingredientRepo;

    @GetMapping("/")
    public List<Ingredient> getAll() {
        return ingredientRepo.findAll();
    }
}
