package nl.itvitae.superrecipe.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="unit_values")
@Getter
@Setter
@NoArgsConstructor
public class UnitValue {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String format;
    private boolean useFraction;

    public UnitValue(String name, String format, boolean useFraction) {
        this.name = name;
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
