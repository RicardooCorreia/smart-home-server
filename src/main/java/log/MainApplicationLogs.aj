package log;

import app.MainApplication;

import java.util.logging.Logger;

public aspect MainApplicationLogs {
    private static final Logger LOGGER = Logger.getLogger(MainApplication.class.getName());

    pointcut mainApplicationExecution() : execution(public static void app.MainApplication.main(..));

    before() : mainApplicationExecution() {
        LOGGER.info("Smart Homes Server starting with Aspectj");
    }

    after() : mainApplicationExecution() {
        LOGGER.info("Smart Homes Server launched successfully and threads running");
    }
}
