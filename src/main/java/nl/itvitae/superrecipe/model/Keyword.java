package nl.itvitae.superrecipe.model;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="keywords")
public class Keyword {

    @Id
    @GeneratedValue
    private long id;

    @JsonValue
    private String value;

    public Keyword(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
