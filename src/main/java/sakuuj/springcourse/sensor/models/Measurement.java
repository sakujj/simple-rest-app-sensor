package sakuuj.springcourse.sensor.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Measurement {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "raining")
    private boolean raining;

    @Column(name = "value")
    private float value;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name="receive_time")
    private LocalDateTime receiveTime;
}
