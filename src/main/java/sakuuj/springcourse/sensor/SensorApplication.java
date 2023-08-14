package sakuuj.springcourse.sensor;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sakuuj.springcourse.sensor.dto.MeasurementDto;
import sakuuj.springcourse.sensor.models.Measurement;
import sakuuj.springcourse.sensor.services.SensorService;

@SpringBootApplication
public class SensorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();
        configureMeasurementMapper(mapper);
        return mapper;
    }

    public void configureMeasurementMapper(ModelMapper mapper) {
        mapper.createTypeMap(Measurement.class, MeasurementDto.class)
                .addMappings(m -> m.map(
                        src -> src.getSensor().getName(),
                        MeasurementDto::setSensorName
                ));

        mapper.createTypeMap(MeasurementDto.class, Measurement.class)
                .addMappings(m -> m.skip(Measurement::setSensor));
    }
}
