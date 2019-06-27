package domain.initializer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;
import java.io.InputStream;

import static utils.Properties.DATABASE_STRING_URL;
import static utils.Properties.SERVICE_ACCOUNT_RESOURCE_PATH;

public class FirebaseAppInitializer {
    public static void init() throws IOException {
        InputStream serviceAccountStream = FirebaseAppInitializer.class
                .getResourceAsStream(SERVICE_ACCOUNT_RESOURCE_PATH);
        initializeFromStream(serviceAccountStream);
    }

    private static void initializeFromStream(InputStream serviceAccountStream) throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .setDatabaseUrl(DATABASE_STRING_URL)
                .build();

        FirebaseApp.initializeApp(options);
    }
}
