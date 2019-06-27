package database;

import com.google.firebase.database.FirebaseDatabase;
import model.sensor.Sensor;

import java.util.Objects;

import static utils.Properties.SENSORS_REFERENCE_PATH;

public class SensorRepository extends AbstractRepository<Sensor> {
    private static SensorRepository instance;

    private SensorRepository() {
        super(FirebaseDatabase.getInstance().getReference(SENSORS_REFERENCE_PATH));
    }

    public static SensorRepository getInstance() {
        if (Objects.isNull(instance)) {
            instance = new SensorRepository();
        }
        return instance;
    }
}
