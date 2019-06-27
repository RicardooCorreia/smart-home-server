package model.notification;

import com.google.common.base.MoreObjects;
import model.EntityModel;
import model.notification.type.NotificationTypeEnum;
import model.sensor.Sensor;

public abstract class AbstractNotification implements EntityModel {
    public static final String KEY_PATH = "key";
    public static final String TYPE_PATH = "type";
    public static final String USER_KEY_PATH = "userId";
    public static final String SENSOR_KEY_PATH = "sensorId";

    private String key;
    private NotificationTypeEnum type;
    private String userId;
    private String sensorId;

    AbstractNotification(String key, NotificationTypeEnum type, String userId, String sensorId) {
        this.key = key;
        this.type = type;
        this.userId = userId;
        this.sensorId = sensorId;
    }

    public String getKey() {
        return key;
    }

    public NotificationTypeEnum getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public String getSensorId() {
        return sensorId;
    }
    public abstract boolean isTriggeredBy(Sensor sensor);

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("key", key)
                .add("type", type)
                .add("userId", userId)
                .add("sensorId", sensorId)
                .toString();
    }
}