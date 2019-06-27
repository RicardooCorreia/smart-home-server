package utils;

public class Properties {
    // Common
    public static final String CHARSET_NAME = "UTF-8";
    public static final int SERVER_SOCKET_PORT = 9000;

    // Database references
    public static final String USER_REFERENCE_PATH = "user";
    public static final String SENSORS_REFERENCE_PATH = "sensor";
    public static final String NOTIFICATIONS_REFERENCE_PATH = "notification";
    public static final String HOUSES_REFERENCE_PATH = "houses";

    public static final String USER_TOKEN_REFERENCE_PATH = "tokens";

    public static final String SENSOR_HISTORY_REFERENCE_PATH = "sensor-history";

    public static final String HISTORY_EXTENSION_REFERENCE_PATH = "-history";

    // Firebase specific
    public static final String SERVICE_ACCOUNT_RESOURCE_PATH = "/service-account.json";
    public static final String DATABASE_STRING_URL = "https://supersmarthouses.firebaseio.com";

    // Socket tests
    public static final String TEST_HOUSE_ID = "123";
    public static final String TEST_USER_ID = "321";
}
