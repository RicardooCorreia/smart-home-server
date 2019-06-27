package log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import domain.sensor.SensorListener;

import java.util.logging.Logger;

public aspect SensorListenerLogs {
    private static final Logger LOGGER = Logger.getLogger(SensorListener.class.getName());

    pointcut onChildAddedExecution() : execution(* SensorListener.onChildAdded(..));
    pointcut onChildChangedExecution() : execution(* SensorListener.onChildChanged(..));
    pointcut onChildRemovedExecution() : execution(* SensorListener.onChildRemoved(..));
    pointcut onChildMovedExecution() : execution(* SensorListener.onChildMoved(..));
    pointcut onCancelledExecution() : execution(* SensorListener.onCancelled(..));

    before() : onChildAddedExecution() {
        DataSnapshot dataSnapshot = (DataSnapshot) thisJoinPoint.getArgs()[0];
        LOGGER.info("Sensor added: " +  dataSnapshot.toString());
    }

    before() : onChildChangedExecution() {
        DataSnapshot dataSnapshot = (DataSnapshot) thisJoinPoint.getArgs()[0];
        LOGGER.info("Sensor changed: " +  dataSnapshot.toString());
    }

    before() : onChildRemovedExecution() {
        DataSnapshot dataSnapshot = (DataSnapshot) thisJoinPoint.getArgs()[0];
        LOGGER.info("Sensor removed: " +  dataSnapshot.toString());
    }

    before() : onChildMovedExecution() {
        DataSnapshot dataSnapshot = (DataSnapshot) thisJoinPoint.getArgs()[0];
        LOGGER.info("Sensor moved: " +  dataSnapshot.toString());
    }

    before() : onCancelledExecution() {
        DatabaseError databaseError = (DatabaseError) thisJoinPoint.getArgs()[0];
        LOGGER.severe("Database error: " +  databaseError.toString());
    }
}
