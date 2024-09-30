package backend.academy.ui.impl;

import backend.academy.config.Config;
import backend.academy.controller.UserController;
import backend.academy.state.State;
import backend.academy.ui.GameInterface;
import backend.academy.ui.Hangman;
import backend.academy.ui.Keyboard;
import backend.academy.ui.constants.Interface;
import backend.academy.ui.constants.Styles;

@SuppressWarnings({"ReturnCount"})
public class GameInterfaceImpl implements GameInterface {

    private final UserController controller;
    private final Keyboard keyboard;
    private final Hangman hangman;
    private boolean hangmanFlag;

    public GameInterfaceImpl(UserController controller, Keyboard keyboard, Hangman hangman) {
        this.controller = controller;
        this.keyboard = keyboard;
        this.hangman = hangman;
    }

    @Override
    public void startGame(State state) {
        showGameStatus(state);
    }

    @Override
    public void updateGame(State state) {
        updateHangman(state);
        updateKeyboard(state);
        showGameStatus(state);
        showHint(state);
    }

    @Override
    public void endGame(State state) {
        write(state.isWinner() ? Interface.WIN : Interface.DEFEAT);
        write(state.isWinner() ? "" : "Правильное слово: " + state.word().word().toUpperCase());
    }

    @Override
    public char gameUserInput(State state) {
        String input;
        do {
            input = readInput("Введите букву или '" + Config.HINT_SYMBOL + "' для подсказки: ").toUpperCase();
        } while (!validateInput(input, state));
        return input.charAt(0);
    }

    @Override
    public void write(String message) {
        controller.write(message);
    }

    @Override
    public boolean continueGame() {
        String input = "";
        String message = String.format("Хотите начать заново? (%s|%s): ",
            Styles.GREEN_TEXT + Styles.BOLD + Config.CONTINUE_LETTER + Styles.RESET,
            Styles.RED_TEXT + Styles.BOLD + Config.EXIT_LETTER + Styles.RESET);
        boolean isValidInput = false;
        while (!isValidInput) {
            input = readInput(message);
            if (controller.isValidExitLetter(input)) {
                isValidInput = true;
            } else {
                write(Interface.ERROR + " " + "Введите Y или N.");
            }
        }
        return input.equalsIgnoreCase(String.valueOf(Config.CONTINUE_LETTER));
    }

    private void showKeyboard() {
        write(keyboard.getKeyboard());
    }

    private void showGameStatus(State state) {
        showKeyboard();
        int leftMargin = (Config.KEYBOARD_LENGTH / 2) - (state.word().word().length() / 2);
        write(" ".repeat(leftMargin) + state.guessedWord() + "\n");
        write(Interface.ATTEMPTS + " " + state.attempts());
        write(state.parameters().toString());
    }

    private void updateHangman(State state) {
        if (!state.isLetterInWord()) {
            hangmanFlag = true;
        }

        if (hangmanFlag) {
            write(hangman.getHangmanByAttempts(state.attempts()));
        }
    }

    private void updateKeyboard(State state) {
        if (state.isLetterInWord()) {
            keyboard.paintLetterGreen(state.lastLetter());
        } else {
            keyboard.paintLetterRed(state.lastLetter());
        }
    }

    private void showHint(State state) {
        if (state.lastLetter() == Config.HINT_SYMBOL) {
            write(Interface.HINT + " " + state.word().hint());
        }
    }


    private String readInput(String message) {
        return controller.read(message);
    }

    public boolean validateInput(String input, State state) {
        if (input.trim().isEmpty()) {
            write(Interface.ERROR + " Ввод не может быть пустым.");
            return false;
        }
        if (!controller.isValidLetter(input)) {
            write(Interface.ERROR + " Введите букву кириллицей.");
            return false;
        }

        if (input.length() != 1) {
            write(Interface.ERROR + " Введите только одну букву.");
            return false;
        }

        char letter = input.charAt(0);
        if (state.isUsedLetter(letter)) {
            write(Interface.ERROR + " Вы уже вводили эту букву.");
            return false;
        }
        return true;
    }
}
