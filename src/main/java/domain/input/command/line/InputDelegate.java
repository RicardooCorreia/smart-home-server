package domain.input.command.line;

import middleware.cmd.CommandMessageParser;

import java.util.Scanner;

import static utils.Properties.CHARSET_NAME;

public class InputDelegate implements Runnable {

    @Override
    public void run() {
        Scanner userInputScanner = new Scanner(System.in, CHARSET_NAME);
        String userInput;
        while ((userInput = userInputScanner.nextLine()) != null) {
            dispatchInput(userInput);
        }
    }

    /**
     * Dispatch user input
     * @param userInput input line
     * @return true if message is processed
     */
    private boolean dispatchInput(String userInput) {
        return CommandMessageParser.delegateMessage(userInput);
    }
}
