package model.notification;

import com.google.common.base.MoreObjects;
import model.notification.type.ComparingTypeEnum;
import model.notification.type.NotificationTypeEnum;
import model.sensor.NumberSensor;
import model.sensor.Sensor;
import model.sensor.SensorTypeEnum;

import java.util.Objects;

public class NumberNotification extends AbstractNotification {
    public static final String VALUE_PATH = "number";
    public static final String COMPARING_PATH = "comparingType";

    private float number;
    private ComparingTypeEnum comparingTypeEnum;

    public NumberNotification(String id, String userId, String sensorId,
                              float number, ComparingTypeEnum comparingTypeEnum) {
        super(id, NotificationTypeEnum.NUMBER, userId, sensorId);
        this.number = number;
        this.comparingTypeEnum = comparingTypeEnum;
    }

    public float getNumber() {
        return number;
    }

    public ComparingTypeEnum getComparingTypeEnum() {
        return comparingTypeEnum;
    }

    @Override
    public boolean isTriggeredBy(Sensor sensor) {
        if (!sensorIsValid(sensor)) {
            return false;
        }

        NumberSensor numberSensor = (NumberSensor) sensor;
        switch (getComparingTypeEnum()) {
            case BIGGER:
                return numberSensor.getValue() > getNumber();
            case LESSER:
                return numberSensor.getValue() < getNumber();
            case EQUALS:
                return numberSensor.getValue() == getNumber();
            default:
                return false;
        }
    }

    private boolean sensorIsValid(Sensor sensor) {
        return Objects.equals(sensor.getKey(), this.getSensorId()) && SensorTypeEnum.NUMBER.equals(sensor.getType());
    }

    @Override
    public String toString() {
        return super.toString() +
                MoreObjects.toStringHelper(this)
                        .add("number", number)
                        .add("comparingTypeEnum", comparingTypeEnum)
                        .toString();
    }

    public static final class NumberNotificationBuilder {
        private float number;
        private String key;
        private ComparingTypeEnum comparingTypeEnum;
        private String userId;
        private String sensorId;

        private NumberNotificationBuilder() {
        }

        public static NumberNotificationBuilder aNumberNotification() {
            return new NumberNotificationBuilder();
        }

        public NumberNotificationBuilder withNumber(float number) {
            this.number = number;
            return this;
        }

        public NumberNotificationBuilder withKey(String key) {
            this.key = key;
            return this;
        }

        public NumberNotificationBuilder withComparingTypeEnum(ComparingTypeEnum comparingTypeEnum) {
            this.comparingTypeEnum = comparingTypeEnum;
            return this;
        }

        public NumberNotificationBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public NumberNotificationBuilder withSensorId(String sensorId) {
            this.sensorId = sensorId;
            return this;
        }

        public NumberNotification build() {
            return new NumberNotification(key, userId, sensorId, number, comparingTypeEnum);
        }
    }
}
