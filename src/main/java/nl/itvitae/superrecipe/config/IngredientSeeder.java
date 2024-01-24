package nl.itvitae.superrecipe.config;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
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
    private final UnitValueRepo unitValueRepo;
    private final RecipeRepo recipeRepo;
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder passwordEncoder;

    public void seedIngredients() {

        UnitValue ml = new UnitValue("Milliliter", "%s ml", false);
        UnitValue gr = new UnitValue("Gram", "%s gr", false);
        UnitValue el = new UnitValue("Eetlepel", "%s el", true);
        UnitValue tl = new UnitValue("Theelepel", "%s tl", true);
        UnitValue stuks = new UnitValue("Stuks", "%s stuks", false);
        UnitValue tenen = new UnitValue("Tenen", "%s tenen", false);
        unitValueRepo.saveAll(List.of(ml, gr, el, tl, stuks, tenen));
        
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
            new Ingredient("ui", vegetable, gr),
            new Ingredient("knoflook", vegetable, tenen),
            new Ingredient("bloemkool", vegetable, gr),
            new Ingredient("wortel", vegetable, gr),
            new Ingredient("paprika", vegetable, gr),
            new Ingredient("tomaat", vegetable, gr),
            new Ingredient("aardappel", carb, gr),
            new Ingredient("rijst", carb, gr),
            new Ingredient("polenta", carb, gr),
            new Ingredient("pasta", carb, gr),
            new Ingredient("brood", carb, gr),
            new Ingredient("bladerdeeg", carb, stuks),
            new Ingredient("varkenshaas", vegetable, gr),
            new Ingredient("runderhaas", meat, gr),
            new Ingredient("kipfilet", meat, gr),
            new Ingredient("kipdij", meat, gr),
            new Ingredient("bacon", meat, gr),
            new Ingredient("ribeye", meat, gr),
            new Ingredient("frikandel", meat, stuks),
            new Ingredient("ei", dairy, stuks),
            new Ingredient("pecorino", dairy, gr),
            new Ingredient("pinda", nut, gr),
            new Ingredient("cashew", nut, gr),
            new Ingredient("macadamia", nut, gr),
            new Ingredient("zout", spice, tl),
            new Ingredient("peper", spice, tl),
            new Ingredient("curry", condiment, ml),
            new Ingredient("pinda", nut, gr),
            new Ingredient("cashew", nut, gr),
            new Ingredient("macadamia", nut, gr)
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
