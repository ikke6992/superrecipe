package nl.itvitae.superrecipe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity(name="keywords")
public class Keyword {

    @Id
    @GeneratedValue
    private long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "recipe_keyword",
            joinColumns = {@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "keyword_id")}
    )
    @JsonBackReference
    private Set<Recipe> recipes = new HashSet<>();

    public Keyword(String name) {
        this.name = name;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
}
