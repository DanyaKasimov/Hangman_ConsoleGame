package backend.academy.controller.impl;

import backend.academy.config.Config;
import backend.academy.controller.UserController;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UserControllerImpl implements UserController {

    private final InputStream inputStream;
    private final PrintStream printStream;

    public UserControllerImpl(InputStream inputStream, PrintStream printStream) {
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    @Override
    public String read(String text) {
        Scanner scanner = new Scanner(inputStream);
        printStream.print(text);
        return scanner.nextLine();
    }

    @Override
    public boolean isValidLetter(String input) {
        return input.matches("[А-яЁё\\s" + Config.HINT_SYMBOL + "]+");
    }

    @Override
    public boolean isValidExitLetter(String input) {
        return input.toUpperCase().equals(String.valueOf(Config.CONTINUE_LETTER))
            || input.toUpperCase().equals(String.valueOf(Config.EXIT_LETTER));
    }

    @Override
    public boolean idValidNumeric(String input, int listSize) {
        try {
            int value = Integer.parseInt(input);
            return value >= 1 && value <= listSize;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void write(String text) {
        printStream.println(text);
    }
}
