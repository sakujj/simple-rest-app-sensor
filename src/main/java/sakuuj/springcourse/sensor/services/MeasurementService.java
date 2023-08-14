package sakuuj.springcourse.sensor.services;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sakuuj.springcourse.sensor.dao.MeasurementDao;
import sakuuj.springcourse.sensor.models.Measurement;
import sakuuj.springcourse.sensor.repositories.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class MeasurementService {
    private final MeasurementDao measurementDao;
    private final MeasurementRepository measurementRepo;

    @Autowired
    public MeasurementService(MeasurementDao measurementDao, MeasurementRepository measurementRepo) {
        this.measurementDao = measurementDao;
        this.measurementRepo = measurementRepo;
    }


    public List<Measurement> findAll() {
        return measurementDao.findAll();
    }

    public Optional<Measurement> findById(int id) {
        return measurementRepo.findById(id);
    }

    public List<Measurement> findAllBySensorName(String name) {
        return measurementRepo.findAllBySensorName(name);
    }

    @Transactional
    public int save(Measurement measurement) {
        return measurementRepo.save(measurement).getId();
    }
}
