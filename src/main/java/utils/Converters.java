package utils;

import com.google.firebase.database.DataSnapshot;
import model.notification.AbstractNotification;
import model.notification.BooleanNotification;
import model.notification.NumberNotification;
import model.notification.type.ComparingTypeEnum;
import model.notification.type.NotificationTypeEnum;
import model.sensor.NumberSensor;
import model.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    public static NumberSensor numberSensorFrom(Sensor sensor) {
        return NumberSensor.Builder.aSensor()
                .withId(sensor.getKey())
                .withHouseId(sensor.getHouseId())
                .withName(sensor.getName())
                .withSourceId(sensor.getSourceId())
                .build();
    }

    private static AbstractNotification notificationFrom(DataSnapshot dataSnapshot) {
        return isNumberNotificationSnapshot(dataSnapshot) ?
                numberNotificationFrom(dataSnapshot) : simpleNotificationFrom(dataSnapshot);
    }

    private static BooleanNotification simpleNotificationFrom(DataSnapshot dataSnapshot) {
        return BooleanNotification.Builder.aNotification()
                .withId(getStringValue(dataSnapshot, BooleanNotification.KEY_PATH))
                .withSensorId(getStringValue(dataSnapshot, BooleanNotification.SENSOR_KEY_PATH))
                .withUserId(getStringValue(dataSnapshot, BooleanNotification.USER_KEY_PATH))
                .withValue(Boolean.parseBoolean(getStringValue(dataSnapshot, BooleanNotification.VALUE_PATH)))
                .build();
    }

    private static NumberNotification numberNotificationFrom(DataSnapshot dataSnapshot) {
        return NumberNotification.NumberNotificationBuilder.aNumberNotification()
                .withKey(getStringValue(dataSnapshot, NumberNotification.KEY_PATH))
                .withComparingTypeEnum(ComparingTypeEnum.valueOf(getStringValue(dataSnapshot,
                        NumberNotification.COMPARING_PATH)))
                .withNumber(Float.parseFloat(getStringValue(dataSnapshot, NumberNotification.VALUE_PATH)))
                .withSensorId(getStringValue(dataSnapshot, NumberNotification.SENSOR_KEY_PATH))
                .withUserId(getStringValue(dataSnapshot, NumberNotification.USER_KEY_PATH))
                .build();
    }

    private static String getStringValue(DataSnapshot dataSnapshot, String child) {
        return dataSnapshot.child(child).getValue().toString();
    }

    private static boolean isNumberNotificationSnapshot(DataSnapshot dataSnapshot) {
        return dataSnapshot.child(AbstractNotification.TYPE_PATH).getValue()
                .toString().equals(NotificationTypeEnum.NUMBER.toString());
    }

    public static List<AbstractNotification> notificationsFrom(DataSnapshot dataSnapshot) {
        List<AbstractNotification> notifications = new ArrayList<>();
        dataSnapshot.getChildren()
                .forEach(notificationSnapshot -> notifications.add(notificationFrom(notificationSnapshot)));
        return notifications;
    }

    public static Sensor sensorFrom(DataSnapshot dataSnapshot) {
        return isSimpleSensor(dataSnapshot) ? simpleSensorFrom(dataSnapshot) : numberSensorFrom(dataSnapshot);
    }

    private static Sensor numberSensorFrom(DataSnapshot dataSnapshot) {
        return NumberSensor.Builder.aSensor()
                .withId(getStringValue(dataSnapshot, NumberSensor.KEY_PATH))
                .withSourceId(getStringValue(dataSnapshot, NumberSensor.SOURCE_KEY_PATH))
                .withName(getStringValue(dataSnapshot, NumberSensor.NAME_PATH))
                .withNumberValue(Float.parseFloat(getStringValue(dataSnapshot, NumberSensor.VALUE_PATH)))
                .withValue(Boolean.parseBoolean(getStringValue(dataSnapshot, NumberSensor.ON_PATH)))
                .build();
    }

    private static Sensor simpleSensorFrom(DataSnapshot dataSnapshot) {
        return Sensor.Builder.aSensor()
                .withId(getStringValue(dataSnapshot, Sensor.KEY_PATH))
                .withHouseId(getStringValue(dataSnapshot, Sensor.HOUSE_KEY_PATH))
                .withName(getStringValue(dataSnapshot, Sensor.NAME_PATH))
                .withSourceId(getStringValue(dataSnapshot, Sensor.SOURCE_KEY_PATH))
                .withValue(Boolean.parseBoolean(getStringValue(dataSnapshot, Sensor.ON_PATH)))
                .build();
    }

    private static boolean isSimpleSensor(DataSnapshot dataSnapshot) {
        return !dataSnapshot.hasChild(NumberSensor.VALUE_PATH);
    }
}
