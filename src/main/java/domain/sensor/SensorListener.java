package domain.sensor;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import database.SensorHistoryRepository;
import database.SensorRepository;
import domain.notification.NotificationTrigger;
import model.sensor.Sensor;
import model.sensor.history.SensorSnapshot;

import java.time.Instant;

import static utils.Converters.sensorFrom;

public class SensorListener implements Runnable, ChildEventListener {
    private final SensorRepository sensorRepository;
    private final SensorHistoryRepository sensorHistoryRepository;

    public SensorListener() {
        this.sensorRepository = SensorRepository.getInstance();
        this.sensorHistoryRepository = SensorHistoryRepository.getInstance();
    }

    @Override
    public void run() {
        sensorRepository.addChildEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        final Sensor sensor = sensorFrom(dataSnapshot);
        NotificationTrigger.getInstance().notify(sensor);
        sensorHistoryRepository.save(SensorSnapshot.Builder
                .aSensorSnapshot()
                .withNewId()
                .withSensor(sensor)
                .withInstant(Instant.now())
                .build());
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }
}
