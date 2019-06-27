package middleware.socket.model;

public enum SensorClassEnum {
    TEMPERATURE(0),
    BLINDER(1),
    DOOR(2),
    LIGHT(3),
    HVAC(4);

    private static final int TEMPERATURE_VALUE = 0;
    private static final int BLINDER_VALUE = 1;
    private static final int DOOR_VALUE = 2;
    private static final int LIGHT_VALUE = 3;
    private static final int HVAC_VALUE = 4;

    private final int value;

    SensorClassEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SensorClassEnum valueOf(int value) {
        switch (value) {
            case TEMPERATURE_VALUE:
                return TEMPERATURE;
            case BLINDER_VALUE:
                return BLINDER;
            case DOOR_VALUE:
                return DOOR;
            case LIGHT_VALUE:
                return LIGHT;
            case HVAC_VALUE:
                return HVAC;
            default:
                throw new IllegalArgumentException("Does not exist class for that value");
        }
    }
}
