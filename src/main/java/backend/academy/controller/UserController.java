package backend.academy.controller;

public interface UserController {

    String read(String text);

    boolean isValidLetter(String input);

    boolean isValidExitLetter(String input);

    boolean idValidNumeric(String input, int listSize);

    void write(String text);
}
