package database;

import com.google.firebase.database.FirebaseDatabase;
import model.sensor.history.SensorSnapshot;

import static utils.Properties.SENSORS_REFERENCE_PATH;
import static utils.Properties.HISTORY_EXTENSION_REFERENCE_PATH;

public class SensorHistoryRepository extends AbstractRepository<SensorSnapshot> {
    private static SensorHistoryRepository sensorHistoryRepositoryInstance;

    private SensorHistoryRepository() {
        super(FirebaseDatabase.getInstance()
                .getReference(SENSORS_REFERENCE_PATH + HISTORY_EXTENSION_REFERENCE_PATH));
    }

    public static synchronized SensorHistoryRepository getInstance() {
        if (sensorHistoryRepositoryInstance == null) {
            sensorHistoryRepositoryInstance = new SensorHistoryRepository();
        }
        return sensorHistoryRepositoryInstance;
    }
}
