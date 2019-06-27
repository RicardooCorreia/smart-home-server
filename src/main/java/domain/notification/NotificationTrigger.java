package domain.notification;

import com.google.common.collect.ImmutableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingException;
import database.NotificationRepository;
import domain.messaging.CloudMessaging;
import model.notification.AbstractNotification;
import model.sensor.Sensor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static utils.Converters.notificationsFrom;
import static utils.Properties.USER_REFERENCE_PATH;
import static utils.Properties.USER_TOKEN_REFERENCE_PATH;

public class NotificationTrigger {
    private static final Logger LOGGER = Logger.getLogger(NotificationTrigger.class.getName());

    private static final String SENSOR_KEY = "sensorKey";
    private static final String USER_KEY = "userKey";
    private static final String NOTIFICATION_KEY = "notificationKey";

    private static NotificationTrigger notificationTriggerInstance;
    private final NotificationRepository notificationRepository;

    private NotificationTrigger() {
        notificationRepository = NotificationRepository.getInstance();
    }

    public static synchronized NotificationTrigger getInstance() {
        if (notificationTriggerInstance == null) {
            notificationTriggerInstance = new NotificationTrigger();
        }
        return notificationTriggerInstance;
    }

    public void notify(Sensor sensor) {
        notificationRepository.getReference().addListenerForSingleValueEvent(
                getNotificationListener(notifications ->
                        notifications.stream()
                                .filter(bySensorId(sensor.getKey()))
                                .filter(triggeredBy(sensor))
                                .forEach(notification -> triggerNotification(notification, sensor))
                ));
    }

    private Predicate<AbstractNotification> triggeredBy(Sensor sensor) {
        return notification -> notification.isTriggeredBy(sensor);
    }

    private Predicate<AbstractNotification> bySensorId(String key) {
        return abstractNotification -> Objects.equals(key, abstractNotification.getSensorId());
    }

    private void triggerNotification(AbstractNotification abstractNotification, Sensor sensor) {
        getUserTokens(abstractNotification.getUserId(),
                token -> {
                    try {
                        CloudMessaging.getInstance().sendMessage(token, buildMessage(sensor, abstractNotification));
                    } catch (FirebaseMessagingException e) {
                        LOGGER.warning(e.getMessage());
                    }
                });
    }

    private Map<String, String> buildMessage(Sensor sensor, AbstractNotification abstractNotification) {
        return ImmutableMap.of(
                SENSOR_KEY, sensor.getKey(),
                USER_KEY, abstractNotification.getUserId(),
                NOTIFICATION_KEY, abstractNotification.getKey()
        );
    }

    private void getUserTokens(String userId, Consumer<String> consumeToken) {
        FirebaseDatabase.getInstance().getReference(USER_REFERENCE_PATH).child(userId).child(USER_TOKEN_REFERENCE_PATH)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> tokens = tokenListFrom(dataSnapshot.getValue());
                        tokens.forEach(consumeToken);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        LOGGER.warning(databaseError.getMessage());
                    }
                });
    }

    private List<String> tokenListFrom(Object tokensValue) {
        if (Objects.isNull(tokensValue) || !(tokensValue instanceof List)) {
            return Collections.emptyList();
        }
        return (List<String>) tokensValue;
    }

    private ValueEventListener getNotificationListener(Consumer<List<AbstractNotification>> consumer) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                consumer.accept(notificationsFrom(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }
}
