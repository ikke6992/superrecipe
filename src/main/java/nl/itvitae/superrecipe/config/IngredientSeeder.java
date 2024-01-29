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
    private final KeywordRepo keywordRepo;
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
        IngredientCategory fluid = new IngredientCategory("fluid", false);
        ingredientCategoryRepo.save(fluid);

        List<Ingredient> ingredient = List.of(
                new Ingredient("ui", vegetable, stuks),
                new Ingredient("knoflook", vegetable, tenen),
                new Ingredient("bloemkool", vegetable, gr),
                new Ingredient("wortel", vegetable, stuks),
                new Ingredient("prei", vegetable, stuks),
                new Ingredient("paprika", vegetable, stuks),
                new Ingredient("tomaat", vegetable, stuks),
                new Ingredient("spliterwten", vegetable, gr),
                new Ingredient("knolselderij", vegetable, gr),
                new Ingredient("aardappel", carb, gr),
                new Ingredient("rijst", carb, gr),
                new Ingredient("polenta", carb, gr),
                new Ingredient("pasta", carb, gr),
                new Ingredient("brood", carb, gr),
                new Ingredient("bladerdeeg", carb, stuks),
                new Ingredient("karbonade", vegetable, gr),
                new Ingredient("varkenshaas", vegetable, gr),
                new Ingredient("runderhaas", meat, gr),
                new Ingredient("kipfilet", meat, gr),
                new Ingredient("kipdij", meat, gr),
                new Ingredient("bacon", meat, gr),
                new Ingredient("ribeye", meat, gr),
                new Ingredient("frikandel", meat, stuks),
                new Ingredient("rookworst", meat, stuks),
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
                new Ingredient("macadamia", nut, gr),
                new Ingredient("bleekselderij", vegetable, stuks),
                new Ingredient("bouillonblokje", condiment, stuks),
                new Ingredient("tijm", spice, el),
                new Ingredient("laurierblad", spice, stuks),
                new Ingredient("rode wijn", fluid, ml),
                new Ingredient("witte wijn", fluid, ml),
                new Ingredient("water", fluid, ml),
                new Ingredient("appel", fruit, stuks),
                new Ingredient("bloem", carb, gr),
                new Ingredient("suiker", carb, gr),
                new Ingredient("boter", dairy, gr),
                new Ingredient("kaneel", spice, tl),
                new Ingredient("selderij", spice, gr)
        );

        ingredientRepo.saveAll(ingredient);
    }

    public void seedRecipes() throws Exception {

        Keyword italiaans = new Keyword("Italiaans");
        Keyword hollands = new Keyword("Hollands");
        Keyword oven = new Keyword("Oven");
        Keyword stoof = new Keyword("Stoof");
        Keyword hauteCuisine = new Keyword("Haute Cuisine");
        Keyword binnen30Minuten = new Keyword("Binnen 30 Minuten");
        Keyword taart = new Keyword("Taart");
        Keyword soep = new Keyword("Soep");
        keywordRepo.saveAll(List.of(italiaans, hollands, oven, stoof, hauteCuisine, binnen30Minuten, taart, soep));

        Recipe frikandellington = new Recipe("Frikandellington", "Maak het lekker", "Hollands",
                Recipe.PreparationMethod.OVEN, Recipe.DishType.MAIN_DISH);
        frikandellington.addIngredient(ingredientRepo.findByName("frikandel").get(), 5);
        frikandellington.addIngredient(ingredientRepo.findByName("bladerdeeg").get(), 1);
        frikandellington.addIngredient(ingredientRepo.findByName("curry").get(), 50);
        frikandellington.addKeyword(hollands);
        frikandellington.addKeyword(oven);
        frikandellington.addKeyword(hauteCuisine);
        frikandellington.addImage("images/Frikandellington.jpg");
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
        carbonara.addKeyword(italiaans);
        carbonara.addKeyword(binnen30Minuten);
        carbonara.addImage("images/Carbonara.jpg");
        recipeRepo.save(carbonara);

        Recipe ragu = new Recipe("Ragu alla Bolognese", "Lekker lang laten trekken", "Italiaans",
                Recipe.PreparationMethod.COOKED, Recipe.DishType.MAIN_DISH);
        ragu.addIngredient(ingredientRepo.findByName("pasta").get(), 400);
        ragu.addIngredient(ingredientRepo.findByName("ui").get(), 2);
        ragu.addIngredient(ingredientRepo.findByName("wortel").get(), 2);
        ragu.addIngredient(ingredientRepo.findByName("bleekselderij").get(), 2);
        ragu.addIngredient(ingredientRepo.findByName("knoflook").get(), 5);
        ragu.addIngredient(ingredientRepo.findByName("runderhaas").get(), 800);
        ragu.addIngredient(ingredientRepo.findByName("tomaat").get(), 5);
        ragu.addIngredient(ingredientRepo.findByName("bouillonblokje").get(), 2);
        ragu.addIngredient(ingredientRepo.findByName("zout").get(), 1);
        ragu.addIngredient(ingredientRepo.findByName("peper").get(), 1);
        ragu.addIngredient(ingredientRepo.findByName("tijm").get(), 0.75);
        ragu.addIngredient(ingredientRepo.findByName("laurierblad").get(), 3);
        ragu.addIngredient(ingredientRepo.findByName("rode wijn").get(), 250);
        ragu.addIngredient(ingredientRepo.findByName("water").get(), 375);
        ragu.addKeyword(italiaans);
        ragu.addKeyword(stoof);
        ragu.addKeyword(oven);
        ragu.addImage("images/Ragu.jpg");
        recipeRepo.save(ragu);

        Recipe appeltaart = new Recipe("Appeltaart", "Mmm... taart...", "Hollands",
                Recipe.PreparationMethod.OVEN, Recipe.DishType.DESSERT);
        appeltaart.addIngredient(ingredientRepo.findByName("appel").get(), 6);
        appeltaart.addIngredient(ingredientRepo.findByName("suiker").get(), 225);
        appeltaart.addIngredient(ingredientRepo.findByName("kaneel").get(), 2);
        appeltaart.addIngredient(ingredientRepo.findByName("boter").get(), 200);
        appeltaart.addIngredient(ingredientRepo.findByName("zout").get(), 0.5);
        appeltaart.addIngredient(ingredientRepo.findByName("bloem").get(), 300);
        appeltaart.addKeyword(taart);
        appeltaart.addKeyword(oven);
        appeltaart.addKeyword(hollands);
        appeltaart.addImage("images/Appeltaart.jpg");
        recipeRepo.save(appeltaart);

        Recipe snert = new Recipe("Snert", "Snert", "Hollands",
                Recipe.PreparationMethod.COOKED, Recipe.DishType.MAIN_DISH);
        snert.addIngredient(ingredientRepo.findByName("spliterwten").get(), 500);
        snert.addIngredient(ingredientRepo.findByName("prei").get(), 1);
        snert.addIngredient(ingredientRepo.findByName("ui").get(), 1);
        snert.addIngredient(ingredientRepo.findByName("knolselderij").get(), 250);
        snert.addIngredient(ingredientRepo.findByName("wortel").get(), 1);
        snert.addIngredient(ingredientRepo.findByName("aardappel").get(), 250);
        snert.addIngredient(ingredientRepo.findByName("rookworst").get(), 1);
        snert.addIngredient(ingredientRepo.findByName("karbonade").get(), 300);
        snert.addIngredient(ingredientRepo.findByName("laurierblad").get(), 1);
        snert.addIngredient(ingredientRepo.findByName("selderij").get(), 25);
        snert.addIngredient(ingredientRepo.findByName("water").get(), 2000);
        snert.addIngredient(ingredientRepo.findByName("bouillonblokje").get(), 2);
        snert.addKeyword(soep);
        snert.addKeyword(hollands);
        snert.addImage("images/Snert.jpg");
        recipeRepo.save(snert);

        /**Recipe kipkornwrap = new Recipe("Kipkornwrap", "Warm de kipkorn op, gooi er wat groentjes bij, " +
                "prop het in een wrap en klaar is Keessie", "Studentikoos", Recipe.PreparationMethod.OVEN,
                Recipe.DishType.MAIN_DISH);
        kipkornwrap.addIngredient(ingredientRepo.findByName("kipkorn").get(), 2);
        kipkornwrap.addIngredient(ingredientRepo.findByName("tortillawrap").get(), 2);
        kipkornwrap.addIngredient(ingredientRepo.findByName("mais").get(), 50);
        kipkornwrap.addIngredient(ingredientRepo.findByName("paprika").get(), 0.25);
        kipkornwrap.addIngredient(ingredientRepo.findByName("ui").get(), 0.5);
        kipkornwrap.addIngredient(ingredientRepo.findByName("sla").get(), 50);
        kipkornwrap.addIngredient(ingredientRepo.findByName("creme fraiche").get(), 25);
        kipkornwrap.addIngredient(ingredientRepo.findByName("sweet chili saus").get(), 25);
        kipkornwrap.addKeyword(oven);
        kipkornwrap.addKeyword(oven);*/

    }

    @Override
    public void run(String... args) throws Exception {
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
