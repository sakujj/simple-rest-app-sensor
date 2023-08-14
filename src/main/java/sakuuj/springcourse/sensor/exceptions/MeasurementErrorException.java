package sakuuj.springcourse.sensor.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class MeasurementErrorException extends RestErrorException {
    public MeasurementErrorException(List<ObjectError> errors) {
        super(errors);
    }
}
