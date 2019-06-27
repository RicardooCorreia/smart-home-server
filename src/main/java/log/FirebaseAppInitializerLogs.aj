package log;

import domain.initializer.FirebaseAppInitializer;
import utils.Properties;

import java.util.logging.Logger;

public aspect FirebaseAppInitializerLogs {
    private static final Logger LOGGER = Logger.getLogger(FirebaseAppInitializer.class.getName());

    pointcut initializationExecution(): execution(public static void FirebaseAppInitializer.init());

    after(): initializationExecution() {
        LOGGER.info(String.join(System.lineSeparator(), "Firebase App initialized!",
                "Service account location: " + Properties.SERVICE_ACCOUNT_RESOURCE_PATH,
                "Database URL: " + Properties.DATABASE_STRING_URL));
    }
}
