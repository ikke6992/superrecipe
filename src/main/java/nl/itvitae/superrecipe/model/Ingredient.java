package nl.itvitae.superrecipe.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne(optional = false)
    private IngredientCategory category;

    @ManyToOne(optional = false)
    private UnitValue unit;

    public Ingredient(String name, IngredientCategory category, UnitValue unit) {
        this.name = name;
        this.category = category;
        this.unit = unit;
    }

    @Override
    // For debug purposes
    public String toString() {
        return "Ingredient{name=" + name + ", category=" + category.getName() + ", unit=" + unit + "}";
    }
}
