package model.notification;

import com.google.common.base.MoreObjects;
import model.notification.type.NotificationTypeEnum;
import model.sensor.Sensor;

import java.util.Objects;
import java.util.UUID;

public class BooleanNotification extends AbstractNotification {
    public static final String VALUE_PATH = "value";
    private boolean value;

    private BooleanNotification(String id, String userId, String sensorId, boolean value) {
        super(id, NotificationTypeEnum.BOOLEAN, userId, sensorId);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean isTriggeredBy(Sensor sensor) {
        if (!Objects.equals(sensor.getKey(), this.getSensorId())) {
            return false;
        }
        return sensor.isOn() == value;
    }

    @Override
    public String toString() {
        return super.toString() +
                MoreObjects.toStringHelper(this)
                        .add("value", value)
                        .toString();
    }

    public static class Builder {
        String id;
        String userId;
        String sensorId;
        boolean value;

        public static Builder aNotification() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withNewId() {
            this.id = UUID.randomUUID().toString();
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withSensorId(String sensorId) {
            this.sensorId = sensorId;
            return this;
        }

        public Builder withValue(boolean value) {
            this.value = value;
            return this;
        }

        public BooleanNotification build() {
            return new BooleanNotification(id, userId, sensorId, value);
        }
    }
}

