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

    @Enumerated(EnumType.STRING)
    private UnitValue unit;

    public Ingredient(String name, IngredientCategory category, UnitValue unit) {
        this.name = name;
        this.category = category;
        this.unit = unit;
    }

    public enum UnitValue {
        MILLILITERS("%s ml", false),
        @JsonEnumDefaultValue
        GRAMS("%s gr", false),
        TABLESPOONS("%s el", true),
        TEASPOONS("%s tl", true),
        PIECES("%s stuks", false),
        TOES("%s tenen", false);

        private final String format;
        private final boolean useFraction;

        UnitValue(String format, boolean useFraction) {
            this.format = format;
            this.useFraction = useFraction;
        }

        public String format(double number) {
            double d = number % 1.0;
            if (!useFraction || d == 0.0) return String.format(format, (int) number);
            if (d < 0.375) return String.format(format, number + "\u00bc"); // 1/4
            if (d < 0.625) return String.format(format, number + "\u00bd"); // 1/2
            return String.format(format, number + "\u00be"); // 3/4
        }
    }

    @Override
    // For debug purposes
    public String toString() {
        return "Ingredient{name=" + name + ", category=" + category.getName() + ", unit=" + unit + "}";
    }
}
