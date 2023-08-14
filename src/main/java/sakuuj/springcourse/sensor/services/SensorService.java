package sakuuj.springcourse.sensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sakuuj.springcourse.sensor.models.Sensor;
import sakuuj.springcourse.sensor.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class SensorService {
    private final SensorRepository sensorRepo;

    public SensorService(SensorRepository sensorRepo) {
        this.sensorRepo = sensorRepo;
    }

    public List<Sensor> findAll() {
        return sensorRepo.findAll();
    }

    public Optional<Sensor> findById(int id) {
        return sensorRepo.findById(id);
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepo.findByName(name).stream().findAny();
    }

    @Transactional
    public int save(Sensor sensor) {
        return sensorRepo.save(sensor).getId();
    }
}
