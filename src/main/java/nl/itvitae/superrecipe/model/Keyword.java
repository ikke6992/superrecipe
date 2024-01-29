package nl.itvitae.superrecipe.model;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="keywords")
public class Keyword {

    @Id
    @GeneratedValue
    private long id;

    @JsonValue
    private String name;

    public Keyword(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
