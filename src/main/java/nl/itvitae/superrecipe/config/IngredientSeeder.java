package nl.itvitae.superrecipe.config;

import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.model.*;
import nl.itvitae.superrecipe.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IngredientSeeder implements CommandLineRunner {

    private final IngredientRepo ingredientRepo;
    private final IngredientCategoryRepo ingredientCategoryRepo;
    private final RecipeRepo recipeRepo;
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
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
        frikandellington.addIngredient(ingredientRepo.findByName("frikandel").get(), 5);
        frikandellington.addIngredient(ingredientRepo.findByName("bladerdeeg").get(), 1);
        frikandellington.addIngredient(ingredientRepo.findByName("curry").get(), 50);
        frikandellington.addKeyword("Hollands");
        frikandellington.addKeyword("Oven");
        frikandellington.addKeyword("Haute Cuisine");
        recipeRepo.save(frikandellington);

        Recipe carbonara = new Recipe("Carbonara", "GEEN F*CKING ROOM!", "Italiaans",
                Recipe.PreparationMethod.COOKED, Recipe.DishType.MAIN_DISH);
        carbonara.addIngredient(ingredientRepo.findByName("pasta").get(), 400);
        carbonara.addIngredient(ingredientRepo.findByName("ei").get(), 6);
        carbonara.addIngredient(ingredientRepo.findByName("bacon").get(), 200);
        carbonara.addIngredient(ingredientRepo.findByName("knoflook").get(), 5);
        carbonara.addIngredient(ingredientRepo.findByName("pecorino").get(), 120);
        carbonara.addIngredient(ingredientRepo.findByName("zout").get(), 1);
        carbonara.addIngredient(ingredientRepo.findByName("peper").get(), 2);
        carbonara.addKeyword("Italiaans");
        carbonara.addKeyword("Binnen 30 Minuten");
        recipeRepo.save(carbonara);
    }


    @Override
    public void run(String... args) {
        long ingredientCount = ingredientRepo.count();
        if (ingredientCount == 0) {
            seedIngredients();
            seedRecipes();
        } else {
            System.out.println("Ingredients already present: " + ingredientCount);
        }

        if (userRepo.count() == 0) {
            User bill = new User("bill", passwordEncoder.encode("secret"));
            userRepo.save(bill);
            Authority billsRole = new Authority((bill.getUsername()),"ROLE_ADMIN");
            authorityRepo.save(billsRole);
            User jane = new User("Jane", passwordEncoder.encode("password"));
            userRepo.save(jane);
            Authority janesRole = new Authority(jane.getUsername(),"ROLE_USER");
            authorityRepo.save(janesRole);

        }
    }
}
