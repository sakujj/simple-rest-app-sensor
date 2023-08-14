package sakuuj.springcourse.sensor.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class RestErrorException extends RuntimeException{
    private List<ObjectError> restErrors = new ArrayList<>();
}
