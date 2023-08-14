package sakuuj.springcourse.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MeasurementDto {
    @Pattern(regexp = "(true)|(TRUE)|(True)|(false)|(FALSE)|(False)",
            message = "The field 'raining' is incorrect")
    private String raining;

    @Pattern(regexp = "[+-]?\\d{1,2}(.\\d{1,5})?", message = "The field 'value' is incorrect")
    private String value;

    @JsonProperty("sensor_name")
    @Pattern(regexp = "\\S*", message = "The specified sensor does not exist")
    @Length.List({
            @Length(min = 3, message = "The specified sensor does not exist"),
            @Length(max = 30, message = "The specified sensor does not exist")
    })
    private String sensorName;
}
