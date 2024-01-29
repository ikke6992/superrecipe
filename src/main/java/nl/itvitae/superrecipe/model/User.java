package nl.itvitae.superrecipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@Entity(name="users")
public class User {

    @Id
    private String username;
    private String password;
    private boolean enabled = true;


    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() { return username;}
}
