package nl.itvitae.superrecipe.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "`recipes`")
public class Recipe {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToMany(mappedBy = "recipes")
    private Set<Keyword> keywords = new HashSet<>();

    // TODO: Add LOB for picture

    @Column(columnDefinition = "TEXT")
    private String instructions;

    private String kitchen;

    @Enumerated(EnumType.STRING)
    private PreparationMethod preparationMethod;

    @JoinColumn(name = "recipe_id")
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<RecipeIngredient> ingredients;

    @Enumerated(EnumType.STRING)
    private DishType type;

    public Recipe(String name, String instructions, String kitchen, PreparationMethod preparationMethod, DishType type) {
        this.name = name;
        this.instructions = instructions;
        this.kitchen = kitchen;
        this.preparationMethod = preparationMethod;
        this.ingredients = new ArrayList<>();
        this.type = type;
    }

    public void addIngredient(Ingredient ingredient, double amount) {
        this.ingredients.add(new RecipeIngredient(ingredient, amount));
    }

    public void addKeyword(Keyword keyword) {
        keywords.add(keyword);
    }

    public enum PreparationMethod {
        OVEN,
        DEEP_FRIER,
        AIR_FRIER,
        @JsonEnumDefaultValue
        COLD,
        COOKED,
        BAKED,
        STEAMED
    }

    public enum DishType {
        APPETIZER,
        @JsonEnumDefaultValue
        MAIN_DISH,
        DESSERT,
        SNACK,
        SIDE_DISH,
        DRINK
    }
}
