package sakuuj.springcourse.sensor.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SensorDto {

    @Pattern(regexp = "\\S*", message = "A name can not contain white spaces")
    @Length.List({
            @Length(min = 3, message = "The specified name is too short"),
            @Length(max = 30, message = "The specified name is too long")
    })
    private String name;
}
