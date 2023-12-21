package nl.itvitae.superrecipe.model;

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
@Entity(name = "`ingredients`")
public class Ingredient {
    @Id
    @GeneratedValue
    private long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    private IngredientCategory category;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private UnitValue unit;

    public Ingredient(String name, IngredientCategory category, UnitValue unit) {
        this.name = name;
        this.category = category;
        this.unit = unit;
    }

    public enum UnitValue {
        MILLILITERS("%s ml", false),
        GRAMS("%s gr", false),
        TABLESPOONS("%s tbsp", true),
        TEASPOONS("%s tsp", true),
        PIECES("%s pieces", false),
        TOES("%s toes", false);

        private final String format;
        private final boolean useFraction;

        UnitValue(String format, boolean useFraction) {
            this.format = format;
            this.useFraction = useFraction;
        }

        public String format(double number) {
            if (!useFraction || number % 1.0 == 0.0) return String.format(format, (int) number);
            return String.format(format, number); // TODO: Make format return fractions like 1 1/2 tsp
        }
    }
}
