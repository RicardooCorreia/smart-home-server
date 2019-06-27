package log;

import domain.input.socket.SocketJob;

import java.util.logging.Logger;

public aspect SocketJobLogs {
    private static final Logger LOGGER = Logger.getLogger(SocketJob.class.getName());

    pointcut runExecution(): execution(* SocketJob.run());
    pointcut startServerCall(): call(* SocketJob.startServerAtPort(..));

    before(): runExecution() {
        LOGGER.info("Starting socket!");
    }

    before(): startServerCall() {
        LOGGER.info("Trying port: " + thisJoinPoint.getArgs()[0]);
    }

    after() returning (boolean result): startServerCall() {
        LOGGER.info(result ? "Socket server closed!" : "Socket server failed, trying again!");
    }
}