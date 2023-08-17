package sakuuj.springcourse.sensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import sakuuj.springcourse.sensor.dto.SensorDto;
import sakuuj.springcourse.sensor.exceptions.RestErrorResponse;
import sakuuj.springcourse.sensor.exceptions.SensorErrorException;
import sakuuj.springcourse.sensor.models.Sensor;
import sakuuj.springcourse.sensor.services.SensorService;


@RestController
@RequestMapping("/sensors")
public class SensorController {

    final ModelMapper mapper;

    final SensorService sensorService;

    @Autowired
    public SensorController(ModelMapper mapper, SensorService sensorService) {
        this.mapper = mapper;
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public ResponseEntity<SensorDto> registerSensor(@RequestBody @Valid SensorDto sensorDto,
                                                    BindingResult bindingResult,
                                                    UriComponentsBuilder ucb) {

        if (bindingResult.hasErrors()) {
            throw new SensorErrorException(bindingResult.getAllErrors());
        }

        Sensor sensor = mapper.map(sensorDto, Sensor.class);
        sensorService.save(sensor);

        return new ResponseEntity<>(sensorDto, HttpStatus.CREATED);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorResponse<SensorErrorException>> handleException(SensorErrorException ex) {
        var errorResponse = new RestErrorResponse<>(ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
