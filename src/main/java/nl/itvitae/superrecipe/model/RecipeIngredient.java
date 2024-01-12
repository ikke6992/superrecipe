package nl.itvitae.superrecipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "recipe_ingredients")
public class RecipeIngredient {

    @Id
    @GeneratedValue
    public long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    public Ingredient ingredient;

    public double amount;

    public RecipeIngredient(Ingredient ingredient, double amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    @JsonProperty
    public String getName() {
        return ingredient.getName();
    }

    @JsonProperty
    public String getAmountInUnits() {
        return ingredient.getUnit().format(amount);
    }

    @Override
    // For debug purposes
    public String toString() {
        return "RecipeIngredient[" + getAmountInUnits() + " " + ingredient.getName() + "]";
    }
}
