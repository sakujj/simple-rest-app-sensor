package sakuuj.springcourse.sensor.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sakuuj.springcourse.sensor.dto.MeasurementDto;
import sakuuj.springcourse.sensor.models.Sensor;
import sakuuj.springcourse.sensor.services.SensorService;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDto dto = (MeasurementDto) target;
        String sensorName = dto.getSensorName();
        Optional<Sensor> found = sensorService.findByName(sensorName);
        if (found.isEmpty()) {
            errors.rejectValue("sensorName", "", "The specified sensor does not exist[*]");
        }
    }
}
