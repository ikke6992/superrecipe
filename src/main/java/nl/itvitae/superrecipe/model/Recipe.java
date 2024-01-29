package nl.itvitae.superrecipe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

    @ManyToMany
    private Set<Keyword> keywords = new HashSet<>();

    private byte[] image;

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

    public Recipe(String name) {
        this.name=name;
        this.instructions="";
        this.kitchen="";
        this.preparationMethod=PreparationMethod.COLD;
        this.ingredients = new ArrayList<>();
        this.type=DishType.MAIN_DISH;
    }

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

    public void addImage(String path) throws Exception {
        BufferedImage bImage = ImageIO.read(new File(path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        image = bos.toByteArray();
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
