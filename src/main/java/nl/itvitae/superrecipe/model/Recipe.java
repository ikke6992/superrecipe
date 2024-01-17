package nl.itvitae.superrecipe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "recipe_keyword",
        joinColumns = {@JoinColumn(name = "keyword_id")},
        inverseJoinColumns = {@JoinColumn(name = "recipe_id")}
    )
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

    public void addKeyword(String keyword) {
        keywords.add(new Keyword(keyword));
    }

    @JsonGetter
    public String getKeywords() {
        return keywords.stream().map(Keyword::getName).collect(Collectors.joining(","));
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
