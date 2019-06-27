package domain.messaging;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.util.Map;

public class CloudMessaging {
    private static CloudMessaging cloudMessagingInstance;

    private FirebaseMessaging firebaseMessagingInstance;

    private CloudMessaging() {
        this.firebaseMessagingInstance = FirebaseMessaging.getInstance();
    }

    public static synchronized CloudMessaging getInstance() {
        if (cloudMessagingInstance == null) {
            cloudMessagingInstance = new CloudMessaging();
        }
        return cloudMessagingInstance;
    }

    public void sendMessage(String token, Map<String, String> messageParams) throws FirebaseMessagingException {
        Message message = Message.builder()
                .putAllData(messageParams)
                .setToken(token)
                .build();

        firebaseMessagingInstance.send(message);
    }
}
