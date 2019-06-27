package log;

import java.util.logging.Logger;

public aspect RepositoryLogs {
    private static final Logger LOGGER = Logger.getLogger("Repository");

    pointcut saveExecution() : execution(* save(..));

    before() : saveExecution() {
        LOGGER.info("Saving entity: " + thisJoinPoint.getArgs()[0].toString());
    }
}
