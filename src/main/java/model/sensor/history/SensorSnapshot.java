package model.sensor.history;

import model.EntityModel;
import model.sensor.Sensor;

import java.time.Instant;
import java.util.UUID;

public class SensorSnapshot implements EntityModel {
    private String key;
    private Sensor sensor;
    private Instant instant;

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    private SensorSnapshot() {
    }

    public Sensor getSensor() {
        return sensor;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public static class Builder {
        private String key;
        private Sensor sensor;
        private Instant instant;

        private Builder() {
        }

        public static Builder aSensorSnapshot() {
            return new Builder();
        }

        public Builder withNewId() {
            this.key = UUID.randomUUID().toString();
            return this;
        }

        public Builder withSensor(Sensor sensor) {
            this.sensor = sensor;
            return this;
        }

        public Builder withInstant(Instant instant) {
            this.instant = instant;
            return this;
        }

        public SensorSnapshot build() {
            SensorSnapshot sensorSnapshot = new SensorSnapshot();
            sensorSnapshot.setKey(key);
            sensorSnapshot.setSensor(sensor);
            sensorSnapshot.setInstant(instant);
            return sensorSnapshot;
        }
    }
}
