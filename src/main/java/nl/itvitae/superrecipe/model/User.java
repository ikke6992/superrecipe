package nl.itvitae.superrecipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Entity(name = "`users`")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password; // Will be empty as long as security is not implemented

    @Getter
    @Setter
    private String roles;

    @Getter
    @Setter
    @ManyToMany
    @OrderBy("name ASC")
    @JoinTable(name = "favorite_recipes")
    private List<Recipe> favoriteRecipes;

    public User(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.favoriteRecipes = new ArrayList<>();
    }

    public void addToFavorite(Recipe recipe) {
        favoriteRecipes.add(recipe);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TokenDTO {
        private String username;
        private String token;

        public TokenDTO(String username, String token) {
            this.username = username;
            this.token = token;
        }
    }

    @Override
    public String toString() {
        return "User{name=" + username + ", password=" + (password.startsWith("$") ? "encrypted" : "unencrypted;" + password) + ", roles=[" + roles + "]}";
    }
}
