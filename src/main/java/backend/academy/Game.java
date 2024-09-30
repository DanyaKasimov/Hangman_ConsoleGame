package backend.academy;

import backend.academy.config.Config;
import backend.academy.controller.UserController;
import backend.academy.controller.impl.UserControllerImpl;
import backend.academy.dictionary.Dictionary;
import backend.academy.exceptions.InvalidDataException;
import backend.academy.handler.GameHandler;
import backend.academy.handler.impl.GameHandlerImpl;
import backend.academy.state.Parameters;
import backend.academy.state.State;
import backend.academy.ui.GameInterface;
import backend.academy.ui.Menu;
import backend.academy.ui.constants.Styles;
import backend.academy.ui.impl.GameInterfaceImpl;
import backend.academy.ui.impl.HangmanImpl;
import backend.academy.ui.impl.KeyboardImpl;
import backend.academy.ui.impl.MenuImpl;


public class Game {

    private final GameInterface gameInterface;
    private final GameHandler gameHandler;
    private final Menu menu;
    private Boolean dictionaryInit;

    public Game() {
        UserController userController = new UserControllerImpl(System.in, System.out);
        this.gameInterface = new GameInterfaceImpl(userController, new KeyboardImpl(), new HangmanImpl());
        Dictionary dictionary = initDictionary();
        this.gameHandler = new GameHandlerImpl(dictionary);
        this.menu = new MenuImpl(userController);
    }

    public void start() {
        if (!dictionaryInit) {
            return;
        }
        playGame();
        if (gameInterface.continueGame()) {
            new Game().start();
        }
    }

    private void playGame() {
        Parameters parameters = menu.handle();

        if (parameters == null) {
            gameInterface.write("Игра завершена.");
            return;
        }

        gameHandler.init(parameters);
        State state = gameHandler.getState();
        gameInterface.startGame(state);
        while (state.isRunning()) {
            char letter = gameInterface.gameUserInput(state);
            gameHandler.handle(letter);
            gameInterface.updateGame(state);
            state.checkGameStatus();
        }
        gameInterface.endGame(state);
    }

    private Dictionary initDictionary() {
        Dictionary dictionary = new Dictionary();
        try {
            dictionary.loadFromFile(Config.FILE_PATH);
            dictionaryInit = true;
        } catch (InvalidDataException e) {
            gameInterface.write(Styles.RED_TEXT + "Ошибка словаря: " + Styles.RESET + e.getMessage());
            dictionaryInit = false;
        }
        return dictionary;
    }
}
