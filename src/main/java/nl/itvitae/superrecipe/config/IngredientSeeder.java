package nl.itvitae.superrecipe.config;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.Ingredient;
import nl.itvitae.superrecipe.model.IngredientCategory;
import nl.itvitae.superrecipe.model.Recipe;
import nl.itvitae.superrecipe.model.User;
import nl.itvitae.superrecipe.repo.IngredientCategoryRepo;
import nl.itvitae.superrecipe.repo.IngredientRepo;
import nl.itvitae.superrecipe.repo.RecipeRepo;
import nl.itvitae.superrecipe.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class IngredientSeeder implements CommandLineRunner {

    private final IngredientRepo ingredientRepo;
    private final IngredientCategoryRepo ingredientCategoryRepo;
    private final RecipeRepo recipeRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

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
        IngredientCategory condiment = new IngredientCategory("condiment", false);
        ingredientCategoryRepo.save(condiment);

        List<Ingredient> ingredient = List.of(
            new Ingredient("ui", vegetable, Ingredient.UnitValue.GRAMS),
            new Ingredient("knoflook", vegetable, Ingredient.UnitValue.TOES),
            new Ingredient("bloemkool", vegetable, Ingredient.UnitValue.GRAMS),
            new Ingredient("wortel", vegetable, Ingredient.UnitValue.GRAMS),
            new Ingredient("paprika", vegetable, Ingredient.UnitValue.GRAMS),
            new Ingredient("tomaat", vegetable, Ingredient.UnitValue.GRAMS),
            new Ingredient("aardappel", carb, Ingredient.UnitValue.GRAMS),
            new Ingredient("rijst", carb, Ingredient.UnitValue.GRAMS),
            new Ingredient("polenta", carb, Ingredient.UnitValue.GRAMS),
            new Ingredient("pasta", carb, Ingredient.UnitValue.GRAMS),
            new Ingredient("brood", carb, Ingredient.UnitValue.GRAMS),
            new Ingredient("bladerdeeg", carb, Ingredient.UnitValue.PIECES),
            new Ingredient("varkenshaas", vegetable, Ingredient.UnitValue.GRAMS),
            new Ingredient("runderhaas", meat, Ingredient.UnitValue.GRAMS),
            new Ingredient("kipfilet", meat, Ingredient.UnitValue.GRAMS),
            new Ingredient("kipdij", meat, Ingredient.UnitValue.GRAMS),
            new Ingredient("bacon", meat, Ingredient.UnitValue.GRAMS),
            new Ingredient("ribeye", meat, Ingredient.UnitValue.GRAMS),
            new Ingredient("frikandel", meat, Ingredient.UnitValue.PIECES),
            new Ingredient("ei", dairy, Ingredient.UnitValue.PIECES),
            new Ingredient("pecorino", dairy, Ingredient.UnitValue.GRAMS),
            new Ingredient("pinda", nut, Ingredient.UnitValue.GRAMS),
            new Ingredient("cashew", nut, Ingredient.UnitValue.GRAMS),
            new Ingredient("macadamia", nut, Ingredient.UnitValue.GRAMS),
            new Ingredient("zout", spice, Ingredient.UnitValue.TEASPOONS),
            new Ingredient("peper", spice, Ingredient.UnitValue.TEASPOONS),
            new Ingredient("curry", condiment, Ingredient.UnitValue.MILLILITERS),
            new Ingredient("pinda", nut, Ingredient.UnitValue.GRAMS),
            new Ingredient("cashew", nut, Ingredient.UnitValue.GRAMS),
            new Ingredient("macadamia", nut, Ingredient.UnitValue.GRAMS)
        );

        ingredientRepo.saveAll(ingredient);
    }

    public void seedRecipes() {

        Recipe frikandellington = new Recipe("Frikandellington", "Maak het lekker", "Hollands",
                Recipe.PreparationMethod.OVEN, Recipe.DishType.MAIN_DISH);
        frikandellington.addIngredient(ingredientRepo.findByName("frikandel").orElseThrow(), 5);
        frikandellington.addIngredient(ingredientRepo.findByName("bladerdeeg").orElseThrow(), 1);
        frikandellington.addIngredient(ingredientRepo.findByName("curry").orElseThrow(), 50);
        frikandellington.addKeywords("Hollands", "Oven", "Haute Cuisine");
        recipeRepo.save(frikandellington);

        Recipe carbonara = new Recipe("Carbonara", "GEEN F*CKING ROOM!", "Italiaans",
                Recipe.PreparationMethod.COOKED, Recipe.DishType.MAIN_DISH);
        carbonara.addIngredient(ingredientRepo.findByName("pasta").orElseThrow(), 400);
        carbonara.addIngredient(ingredientRepo.findByName("ei").orElseThrow(), 6);
        carbonara.addIngredient(ingredientRepo.findByName("bacon").orElseThrow(), 200);
        carbonara.addIngredient(ingredientRepo.findByName("knoflook").orElseThrow(), 5);
        carbonara.addIngredient(ingredientRepo.findByName("pecorino").orElseThrow(), 120);
        carbonara.addIngredient(ingredientRepo.findByName("zout").orElseThrow(), 1);
        carbonara.addIngredient(ingredientRepo.findByName("peper").orElseThrow(), 2);
        carbonara.addKeywords("Italiaans", "Binnen 30 Minuten");
        recipeRepo.save(carbonara);
    }

    private void seedUsers() {
        userRepo.save(new User("user", passwordEncoder.encode("user"), "ROLE_USER"));
    }

    @Override
    public void run(String... args) {
        long ingredientCount = ingredientRepo.count();
        if (ingredientCount == 0) {
            seedIngredients();
            seedRecipes();
            seedUsers();
        } else {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.info("Ingredients already present: " + ingredientCount);
        }
    }
}
