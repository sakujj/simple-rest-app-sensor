package sakuuj.springcourse.sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sakuuj.springcourse.sensor.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findAllBySensorName(String name);

    @Query("""
            SELECT m FROM Measurement m
            LEFT JOIN FETCH m.sensor""")
    List<Measurement> findAll();
}
