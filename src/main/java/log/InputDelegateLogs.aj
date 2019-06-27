package log;

import domain.input.command.line.InputDelegate;

import java.util.logging.Logger;

public aspect InputDelegateLogs {
    private static final Logger LOGGER = Logger.getLogger(InputDelegate.class.getName());

    pointcut runExecution(): execution(public void InputDelegate.run());
    pointcut dispatchInputCall(): call(* InputDelegate.dispatchInput(..));

    void around(): runExecution() {
        System.out.println("User input now a available! You can type in the terminal!");
        System.out.println(String.join(System.lineSeparator(),
                "Commands:",
                "set <entity_reference> <id> <field> <new_value>",
                "get <entity_reference> <id>",
                "search <entity_reference> <field> <with_value>"));
        proceed();
    }

    before(): dispatchInputCall(){
        LOGGER.info("User input being dispatched: " + thisJoinPoint.getArgs()[0]);
    }

    after() returning(boolean saved): dispatchInputCall() {
        String userInput = thisJoinPoint.getArgs()[0].toString();
        if (saved) {
            LOGGER.info("User input: '" + userInput + "' correctly dispatched!");
        } else {
            LOGGER.warning("User input: '" + userInput + "' was found with parse errors!");
        }
    }
}
