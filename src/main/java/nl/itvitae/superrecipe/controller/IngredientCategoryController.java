package nl.itvitae.superrecipe.controller;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.IngredientCategory;
import nl.itvitae.superrecipe.repo.IngredientCategoryRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class IngredientCategoryController {
    private final IngredientCategoryRepo ingredientCategoryRepo;

    @GetMapping("/")
    public List<IngredientCategory> getAll() {
        return ingredientCategoryRepo.findAll();
    }
}
