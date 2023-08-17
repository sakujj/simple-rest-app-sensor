package sakuuj.springcourse.sensor.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import sakuuj.springcourse.sensor.dto.MeasurementDto;
import sakuuj.springcourse.sensor.exceptions.MeasurementErrorException;
import sakuuj.springcourse.sensor.exceptions.RestErrorResponse;
import sakuuj.springcourse.sensor.exceptions.SensorErrorException;
import sakuuj.springcourse.sensor.models.Measurement;
import sakuuj.springcourse.sensor.models.Sensor;
import sakuuj.springcourse.sensor.services.MeasurementService;
import sakuuj.springcourse.sensor.services.SensorService;
import sakuuj.springcourse.sensor.validator.MeasurementValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    MeasurementService measurementService;
    SensorService sensorService;
    MeasurementValidator validator;
    ModelMapper mapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService,
                                 SensorService sensorService,
                                 MeasurementValidator validator,
                                 ModelMapper mapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.validator = validator;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementDto> getOne(@PathVariable("id") String id) {
        int parsedId;
        try {
            parsedId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Measurement> measurement =  measurementService.findById(parsedId);
        return measurement
                .map(value -> ResponseEntity.ok(this.mapToDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MeasurementDto>> getAll() {
        return ResponseEntity.ok(measurementService.findAll()
                .stream()
                .map(this::mapToDto)
                .toList());
    }

    @GetMapping("/rainy-days-count")
    public ResponseEntity<Map<String, Long>> getRainyDaysCount() {
        long count = measurementService.findAll()
                .stream()
                .filter(Measurement::isRaining)
                .count();
        return ResponseEntity.ok(Map.of("rainy-days-count", count));
    }

    @PostMapping("/add")
    public ResponseEntity<MeasurementDto> add(@RequestBody @Valid
                                              MeasurementDto measurementDto,
                                              BindingResult bindingResult,
                                              UriComponentsBuilder ucb) {
        validator.validate(measurementDto, bindingResult);
        if (bindingResult.hasErrors())
            throw new MeasurementErrorException(bindingResult.getAllErrors());

        Measurement measurement = mapFromDto(measurementDto);

        var returnedId = measurementService.save(measurement);

        return ResponseEntity
                .created(ucb
                        .path("/measurements/{id}")
                        .buildAndExpand(returnedId)
                        .toUri())
                .build();
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorResponse<MeasurementErrorException>> handleException(MeasurementErrorException ex) {
        var errorResponse = new RestErrorResponse<>(ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public Measurement mapFromDto(MeasurementDto dto) {
        Measurement measurement = mapper.map(dto, Measurement.class);
        Sensor sensor = sensorService.findByName(dto.getSensorName()).get();
        measurement.setSensor(sensor);
        measurement.setReceiveTime(LocalDateTime.now());
        return measurement;
    }

    public MeasurementDto mapToDto(Measurement measurement) {
        return mapper.map(measurement, MeasurementDto.class);
    }

}
