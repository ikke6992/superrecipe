package nl.itvitae.superrecipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Entity(name="users")
public class User {

    @Id
    private String username;
    private String password;
    private boolean enabled = true;

    //private Set<Recipe> favorites;


    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() { return username;}

    /**public void addFavorite(Recipe recipe) {
        favorites.add(recipe);
    }

    public void removeFavorite(Recipe recipe) {
        favorites.remove(recipe);
    }*/
}
