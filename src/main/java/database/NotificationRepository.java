package database;

import com.google.firebase.database.FirebaseDatabase;
import model.notification.AbstractNotification;

import java.util.Objects;

import static utils.Properties.NOTIFICATIONS_REFERENCE_PATH;

public class NotificationRepository extends AbstractRepository<AbstractNotification> {
    private static NotificationRepository instance;

    private NotificationRepository() {
        super(FirebaseDatabase.getInstance().getReference(NOTIFICATIONS_REFERENCE_PATH));
    }

    public static NotificationRepository getInstance() {
        if (Objects.isNull(instance)) {
            instance = new NotificationRepository();
        }
        return instance;
    }
}
