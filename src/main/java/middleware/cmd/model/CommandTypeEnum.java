package middleware.cmd.model;

public enum CommandTypeEnum {
    SET,
    GET,
    SEARCH,
    ERROR;

    public static CommandTypeEnum valueOfOrError(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException ex) {
            return ERROR;
        }
    }
}
