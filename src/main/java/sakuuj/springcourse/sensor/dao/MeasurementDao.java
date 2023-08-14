package sakuuj.springcourse.sensor.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sakuuj.springcourse.sensor.models.Measurement;

import java.util.List;

@Component
public class MeasurementDao {
    EntityManager entityManager;

    @Autowired
    public MeasurementDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private static final String SELECT_ALL = """
            SELECT m
            FROM Measurement m
            LEFT JOIN
            FETCH m.sensor""";

    public List<Measurement> findAll() {
        var session = entityManager.unwrap(Session.class);
        return session
                .createQuery(SELECT_ALL, Measurement.class)
                .getResultList();
    }
}
