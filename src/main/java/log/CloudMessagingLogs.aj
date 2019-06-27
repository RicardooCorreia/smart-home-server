package log;

import domain.messaging.CloudMessaging;

import java.util.Map;
import java.util.logging.Logger;

public aspect CloudMessagingLogs {
    private static final Logger LOGGER = Logger.getLogger(CloudMessaging.class.getName());

    pointcut sendMessageExecution(): execution(* CloudMessaging.sendMessage(..));

    before(): sendMessageExecution() {
        if (thisJoinPoint.getArgs().length >= 2 && thisJoinPoint.getArgs()[1] instanceof Map) {
            String token = thisJoinPoint.getArgs()[0].toString();
            Map<String, String> messageParams = (Map<String, String>) thisJoinPoint.getArgs()[1];
            LOGGER.info(String.join(" ", "Sending message to token: ", token,
                    "with params", messageParams.toString()));
        }
    }
}
