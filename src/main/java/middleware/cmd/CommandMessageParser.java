package middleware.cmd;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import middleware.cmd.model.CommandTypeEnum;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static utils.Properties.HOUSES_REFERENCE_PATH;
import static utils.Properties.SENSORS_REFERENCE_PATH;
import static utils.Properties.USER_REFERENCE_PATH;
import static utils.Properties.NOTIFICATIONS_REFERENCE_PATH;

public class CommandMessageParser {
    // Common
    private static final int MESSAGE_TYPE_INDEX = 0;
    private static final int REFERENCE_TYPE_INDEX = 1;
    private static final int ID_INDEX = 2;

    // Specific
    private static final int SET_FIELD_INDEX = 3;
    private static final int SET_VALUE_INDEX = 4;
    private static final int SEARCH_FIELD_INDEX = 2;
    private static final int SEARCH_VALUE_INDEX = 3;

    // Message split length
    private static final int SET_PARTS_NUMBER = 5;
    private static final int GET_PARTS_NUMBER = 3;
    private static final int SEARCH_PARTS_NUMBER = 4;


    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    private static final ValueEventListener PRINT_RESULT_LISTENER = new PrintResultListener();

    public static boolean delegateMessage(String message) {
        String[] messageParts = message.split(" ");
        switch (getMessageType(messageParts[MESSAGE_TYPE_INDEX])) {
            case SET:
                return set(messageParts);
            case GET:
                return get(messageParts);
            case SEARCH:
                return search(messageParts);
            default:
                System.out.println("Couldn't recognized command!");
                return false;
        }
    }

    private static boolean referenceIsValid(String entity) {
        return getReferences().contains(entity);
    }

    private static List<String> getReferences() {
        return Arrays.asList(
                SENSORS_REFERENCE_PATH,
                HOUSES_REFERENCE_PATH,
                USER_REFERENCE_PATH,
                NOTIFICATIONS_REFERENCE_PATH);
    }

    private static boolean validateMessage(String[] messageParts, int partsLength) {
        if (messageParts.length != partsLength) {
            System.err.println("Number of args doesn't match!" +
                    " Expected: " + SET_PARTS_NUMBER +
                    " Actual: " + messageParts.length);
            return false;
        } else if (!referenceIsValid(messageParts[REFERENCE_TYPE_INDEX])) {
            System.err.println("Reference is not valid!" +
                    " Reference given: " + messageParts[REFERENCE_TYPE_INDEX] +
                    " Expected one of: " + Arrays.toString(getReferences().toArray()));
            return false;
        }
        return true;
    }

    private static boolean search(String[] messageParts) {
        if (!validateMessage(messageParts, SEARCH_PARTS_NUMBER)) {
            return false;
        }

        String reference = messageParts[REFERENCE_TYPE_INDEX];
        String field = messageParts[SEARCH_FIELD_INDEX];
        String value = messageParts[SEARCH_VALUE_INDEX];

        DATABASE.getReference(reference).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren()
                        .forEach(entity -> {
                            if (entity.exists() && entity.hasChild(field)
                                    && entity.child(field).getValue().toString().equals(value)) {
                                System.out.println(entity.getValue().toString());
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Something went wrong!");
                Logger.getLogger(getClass().getName()).severe(databaseError.toException().toString());
            }
        });

        return true;
    }

    private static boolean get(String[] messageParts) {
        if (!validateMessage(messageParts, GET_PARTS_NUMBER)) {
            return false;
        }

        String reference = messageParts[REFERENCE_TYPE_INDEX];
        String id = messageParts[ID_INDEX];

        DATABASE.getReference(reference).child(id).addListenerForSingleValueEvent(PRINT_RESULT_LISTENER);

        return true;
    }

    private static boolean set(String[] messageParts) {
        if (!validateMessage(messageParts, SET_PARTS_NUMBER)) {
            return false;
        }
        String reference = messageParts[REFERENCE_TYPE_INDEX];
        String id = messageParts[ID_INDEX];
        String field = messageParts[SET_FIELD_INDEX];
        String value = messageParts[SET_VALUE_INDEX];

        DATABASE.getReference(reference).child(id).child(field).setValueAsync(value);
        return true;
    }

    private static CommandTypeEnum getMessageType(String type) {
        return CommandTypeEnum.valueOfOrError(type.toUpperCase());
    }

    private static class PrintResultListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            System.out.println(dataSnapshot.getValue());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.err.println("Something went wrong!");
            Logger.getLogger(getClass().getName()).severe(databaseError.toException().toString());
        }
    }
}
