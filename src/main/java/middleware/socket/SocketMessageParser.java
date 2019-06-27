package middleware.socket;

import com.google.common.base.CaseFormat;
import middleware.socket.model.SensorClassEnum;
import model.sensor.NumberSensor;
import model.sensor.Sensor;

import static utils.Converters.numberSensorFrom;
import static utils.Properties.TEST_HOUSE_ID;


public class SocketMessageParser {
    private static final int CLASS_INDEX_START = 0;
    private static final int CLASS_INDEX_END = 2;

    private static final int ID_INDEX_START = 2;
    private static final int ID_INDEX_END = 4;

    private static final int VALUE_INDEX_START = 4;
    private static final int NORMAL_VALUE_INDEX_END = 6;
    private static final int PERCENTAGE_VALUE_INDEX_END = 7;

    private static final int HVAC_STATE_INDEX_START = 6;
    private static final int HVAC_STATE_INDEX_END = 8;

    private static final String TRUE_STRING = "01";

    public static Sensor parse(String message) {
        return isNumericSensor(message) ? numericSensorFrom(message) : simpleSensorFrom(message);
    }

    private static Sensor simpleSensorFrom(String message) {
        return Sensor.Builder
                .aSensor()
                .withId(getSensorDatabaseId(message))
                .withValue(getBooleanValue(message))
                .withName(getName(message))
                .withSourceId(TEST_HOUSE_ID)
                .withHouseId(TEST_HOUSE_ID)
                .build();
    }

    private static String getName(String message) {
        return String.join(" - ", getSensorId(message), camelCaseOf(getSensorClass(message).toString()));
    }

    private static String camelCaseOf(String string) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, string);
    }

    private static Sensor numericSensorFrom(String message) {
        NumberSensor sensor = numberSensorFrom(simpleSensorFrom(message));
        sensor.setValue(getNumericValue(message));
        return sensor;
    }

    private static boolean isNumericSensor(String message) {
        switch (getSensorClass(message)) {
            case TEMPERATURE:
            case BLINDER:
            case HVAC:
                return true;
            case DOOR:
            case LIGHT:
                return false;
            default:
                throw new IllegalArgumentException("Does not exist class for input");
        }
    }

    private static String getSensorId(String message) {
        return message.substring(ID_INDEX_START, ID_INDEX_END);
    }

    private static String getSensorDatabaseId(String message) {
        return getSensorId(message) + getSensorClass(message);
    }

    private static SensorClassEnum getSensorClass(String message) {
        return SensorClassEnum.valueOf(Integer.parseInt(message.substring(CLASS_INDEX_START, CLASS_INDEX_END)));
    }

    private static int getNumericValue(String message) {
        switch (getSensorClass(message)) {
            case TEMPERATURE:
            case HVAC:
                return Integer.parseInt(message.substring(VALUE_INDEX_START, NORMAL_VALUE_INDEX_END));
            case BLINDER:
                return Integer.parseInt(message.substring(VALUE_INDEX_START, PERCENTAGE_VALUE_INDEX_END));
            case DOOR:
            case LIGHT:
                return -1;
            default:
                return -1;
        }
    }

    private static boolean getBooleanValue(String message) {
        switch (getSensorClass(message)) {
            case TEMPERATURE:
            case BLINDER:
                return true;
            case DOOR:
            case LIGHT:
                return TRUE_STRING.equals(message.substring(VALUE_INDEX_START, NORMAL_VALUE_INDEX_END));
            case HVAC:
                return TRUE_STRING.equals(message.substring(HVAC_STATE_INDEX_START, HVAC_STATE_INDEX_END));
            default:
                return false;
        }
    }
}
