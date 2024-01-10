package nl.itvitae.superrecipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "`ingredient_categories`")
public class IngredientCategory {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private boolean allergies;

    public IngredientCategory(String name, boolean allergies) {
        this.name = name;
        this.allergies = allergies;
    }
}
