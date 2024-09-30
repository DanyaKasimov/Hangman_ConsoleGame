import backend.academy.controller.UserController;
import backend.academy.constants.Category;
import backend.academy.constants.Difficult;
import backend.academy.state.Parameters;
import backend.academy.ui.Menu;
import backend.academy.ui.impl.MenuImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuTest {

    private UserController controller;
    private Menu menu;

    @BeforeEach
    public void setUp() {
        controller = Mockito.mock(UserController.class);
        menu = new MenuImpl(controller);
    }

    @Test
    public void testHandleStartGame() {
        when(controller.read(anyString())).thenReturn("1");
        when(controller.idValidNumeric("1", 4)).thenReturn(true);

        Parameters parameters = menu.handle();
        assertNotNull(parameters, "Параметры должны быть не null");
    }

    @Test
    public void testHandleChooseDifficult() {
        when(controller.read(anyString())).thenReturn("2", "1", "1");
        when(controller.idValidNumeric("2", 4)).thenReturn(true);
        when(controller.idValidNumeric("1", 5)).thenReturn(true);
        when(controller.idValidNumeric("1", 4)).thenReturn(true);

        Parameters parameters = menu.handle();
        assertEquals(Difficult.EASY, parameters.difficult(), "Ожидаемая сложность EASY");
    }

    @Test
    public void testHandleChooseCategory() {
        when(controller.read(anyString())).thenReturn("3", "1", "1");
        when(controller.idValidNumeric("3", 4)).thenReturn(true);
        when(controller.idValidNumeric("1", 6)).thenReturn(true);
        when(controller.idValidNumeric("1", 4)).thenReturn(true);

        Parameters parameters = menu.handle();
        assertEquals(Category.CITY, parameters.category(), "Ожидаемая категория CITY");
    }

    @Test
    public void testHandleExit() {
        when(controller.read(anyString())).thenReturn("4");
        when(controller.idValidNumeric("4", 4)).thenReturn(true);

        Parameters parameters = menu.handle();
        assertNull(parameters, "Ожидается завершение программы (null)");
    }

    @Test
    public void testInvalidInputForMenu() {
        when(controller.read(anyString())).thenReturn("5", "1");
        when(controller.idValidNumeric("5", 4)).thenReturn(false);
        when(controller.idValidNumeric("1", 4)).thenReturn(true);

        Parameters parameters = menu.handle();
        assertNotNull(parameters, "Параметры не должны быть null после корректного ввода");
    }
}
