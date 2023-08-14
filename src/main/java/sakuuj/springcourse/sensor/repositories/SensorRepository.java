package sakuuj.springcourse.sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakuuj.springcourse.sensor.models.Sensor;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    List<Sensor> findByName(String name);
}
