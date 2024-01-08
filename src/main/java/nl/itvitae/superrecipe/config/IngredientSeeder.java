package nl.itvitae.superrecipe.config;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Ingredient;
import nl.itvitae.superrecipe.model.IngredientCategory;
import nl.itvitae.superrecipe.repo.IngredientCategoryRepo;
import nl.itvitae.superrecipe.repo.IngredientRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IngredientSeeder implements CommandLineRunner {

    private final IngredientRepo ingredientRepo;
    private final IngredientCategoryRepo ingredientCategoryRepo;

    public void seedIngredients() {
        IngredientCategory vegetable = new IngredientCategory("vegetable", false);
        ingredientCategoryRepo.save(vegetable);
        IngredientCategory meat = new IngredientCategory("meat", false);
        ingredientCategoryRepo.save(meat);
        IngredientCategory dairy = new IngredientCategory("dairy", true);
        ingredientCategoryRepo.save(dairy);
        IngredientCategory fruit = new IngredientCategory("fruit", true);
        ingredientCategoryRepo.save(fruit);
        IngredientCategory nut = new IngredientCategory("nut",true);
        ingredientCategoryRepo.save(nut);
        IngredientCategory spice = new IngredientCategory("spice", true);
        ingredientCategoryRepo.save(spice);
        IngredientCategory carb = new IngredientCategory("carbs", true);
        ingredientCategoryRepo.save(carb);


        List<Ingredient> ingredient = List.of(
        new Ingredient("ui", vegetable, Ingredient.UnitValue.GRAMS),
        new Ingredient("bloemkool", vegetable, Ingredient.UnitValue.GRAMS),
        new Ingredient("wortel", vegetable, Ingredient.UnitValue.GRAMS),
        new Ingredient("paprika", vegetable, Ingredient.UnitValue.GRAMS),
        new Ingredient("tomaat", vegetable, Ingredient.UnitValue.GRAMS),
        new Ingredient("aardappel", carb, Ingredient.UnitValue.GRAMS),
        new Ingredient("rijst", carb, Ingredient.UnitValue.GRAMS),
        new Ingredient("polenta", carb, Ingredient.UnitValue.GRAMS),
        new Ingredient("pasta", carb, Ingredient.UnitValue.GRAMS),
        new Ingredient("brood", carb, Ingredient.UnitValue.GRAMS),
        new Ingredient("varkenshaas", vegetable, Ingredient.UnitValue.GRAMS),
        new Ingredient("runderhaas", meat, Ingredient.UnitValue.GRAMS),
        new Ingredient("kipfilet", meat, Ingredient.UnitValue.GRAMS),
        new Ingredient("kipdij", meat, Ingredient.UnitValue.GRAMS),
        new Ingredient("bacon", meat, Ingredient.UnitValue.GRAMS),
        new Ingredient("ribeye", meat, Ingredient.UnitValue.GRAMS),
        new Ingredient("pinda", nut, Ingredient.UnitValue.GRAMS),
        new Ingredient("cashew", nut, Ingredient.UnitValue.GRAMS),
        new Ingredient("macadamia", nut, Ingredient.UnitValue.GRAMS)
        );

        ingredientRepo.saveAll(ingredient);
    }


    @Override
    public void run(String... args) throws Exception {

        long ingredientCount = ingredientRepo.count();
        if (ingredientCount == 0) {
            seedIngredients();
        } else {
            System.out.println("Ingredients already present: " + ingredientCount);
        }

        
    }
}