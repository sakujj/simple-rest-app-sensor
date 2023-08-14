INSERT INTO Sensor(name) VALUES ('sensor_1');
INSERT INTO Measurement(value, raining, sensor_id, receive_time)
VALUES (28.4,
        true,
        (SELECT id FROM Sensor WHERE name = 'sensor_1'),
        NOW());