import backend.academy.controller.UserController;
import backend.academy.state.State;
import backend.academy.ui.Hangman;
import backend.academy.ui.Keyboard;
import backend.academy.ui.impl.GameInterfaceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameInterfaceTest {

    private GameInterfaceImpl gameInterface;
    private UserController userController;
    private State state;

    @BeforeEach
    public void setUp() {
        userController = mock(UserController.class);
        state = mock(State.class);
        gameInterface =
            new GameInterfaceImpl(userController, mock(Keyboard.class), mock(Hangman.class));
    }

    @Test
    void testEmptyInput() {
        String input = "   ";
        boolean result = gameInterface.validateInput(input, state);
        assertFalse(result, "Пустой ввод должен быть невалидным");
    }

    @Test
    void testInvalidLetter() {
        String input = "1";
        when(userController.isValidLetter(input)).thenReturn(false);
        boolean result = gameInterface.validateInput(input, state);
        assertFalse(result, "Невалидный символ должен возвращать false");
    }

    @Test
    void testInputLengthMoreThanOne() {
        String input = "аб";
        when(userController.isValidLetter(input)).thenReturn(true);
        boolean result = gameInterface.validateInput(input, state);
        assertFalse(result, "Ввод больше одной буквы должен быть невалидным");
    }

    @Test
    void testLetterAlreadyUsed() {
        String input = "а";
        when(userController.isValidLetter(input)).thenReturn(true);
        when(state.isUsedLetter(input.charAt(0))).thenReturn(true);
        boolean result = gameInterface.validateInput(input, state);
        assertFalse(result, "Буква, которая уже использована, должна возвращать false");
    }

    @Test
    void testValidInput() {
        String input = "а";
        when(userController.isValidLetter(input)).thenReturn(true);
        when(state.isUsedLetter(input.charAt(0))).thenReturn(false);
        boolean result = gameInterface.validateInput(input, state);
        assertTrue(result, "Валидный ввод должен возвращать true");
    }
}
