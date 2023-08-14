package sakuuj.springcourse.sensor.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class SensorErrorException extends RestErrorException {
    public SensorErrorException(List<ObjectError> errors) {
        super(errors);
    }
}
