package log;

import domain.notification.NotificationTrigger;
import model.notification.AbstractNotification;
import model.sensor.Sensor;

import java.util.logging.Logger;

public aspect NotificationTriggerLogs {
    private static final Logger LOGGER = Logger.getLogger(NotificationTrigger.class.getName());

    pointcut notificationTriggerExecution() : execution(* NotificationTrigger.triggerNotification(..));

    before(): notificationTriggerExecution() {
        AbstractNotification notification = (AbstractNotification) thisJoinPoint.getArgs()[0];
        Sensor sensor = (Sensor) thisJoinPoint.getArgs()[1];

        LOGGER.info(String.join(" ", "Notification with id:", notification.getKey(),
                "was triggered by sensor with id:", sensor.getKey(), "and name:", sensor.getName()));

    }
}
