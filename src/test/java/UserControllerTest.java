import backend.academy.config.Config;
import backend.academy.controller.impl.UserControllerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {

    private OutputStream outputStream;
    private UserControllerImpl userController;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        InputStream inputStream = new ByteArrayInputStream("Пример".getBytes());
        userController = new UserControllerImpl(inputStream, printStream);
    }

    @Test
    public void testRead() {
        String prompt = "Введите текст: ";
        String result = userController.read(prompt);
        assertEquals("Пример", result, "Метод read() должен возвращать ввод пользователя");
        assertEquals("Введите текст: ", outputStream.toString(), "Сообщение должно быть выведено в консоль");
    }

    @Test
    public void testIsValidLetterValid() {
        assertTrue(userController.isValidLetter("А"), "Буква 'А' должна быть валидной");
        assertTrue(userController.isValidLetter("ё"), "Буква 'ё' должна быть валидной");
        assertTrue(userController.isValidLetter(" "), "Пробел должен быть валидным символом");
        assertTrue(userController.isValidLetter("*"), "Символ '*' должен быть валидным");
    }

    @Test
    public void testIsValidLetterInvalid() {
        assertFalse(userController.isValidLetter("1"), "Цифра '1' не должна быть валидной");
        assertFalse(userController.isValidLetter(";"), "Символ ';' не должен быть валидным");
        assertFalse(userController.isValidLetter("f"), "Латинская буква 'f' не должна быть валидной");
    }

    @Test
    public void testIsValidExitLetterValid() {
        String continueLetter = String.valueOf(Config.CONTINUE_LETTER).toUpperCase();
        String exitLetter = String.valueOf(Config.EXIT_LETTER).toUpperCase();
        assertTrue(userController.isValidExitLetter(continueLetter), "Буква для продолжения должна быть валидной");
        assertTrue(userController.isValidExitLetter(exitLetter), "Буква для выхода должна быть валидной");
    }

    @Test
    public void testIsValidExitLetterInvalid() {
        assertFalse(userController.isValidExitLetter("X"), "Неверная буква не должна быть валидной");
    }

    @Test
    public void testIdValidNumericValid() {
        assertTrue(userController.idValidNumeric("1", 5), "Число '1' должно быть валидным в диапазоне [1, 5]");
        assertTrue(userController.idValidNumeric("5", 5), "Число '5' должно быть валидным в диапазоне [1, 5]");
    }

    @Test
    public void testIdValidNumericInvalid() {
        assertFalse(userController.idValidNumeric("0", 5), "Число '0' не должно быть валидным в диапазоне [1, 5]");
        assertFalse(userController.idValidNumeric("6", 5), "Число '6' не должно быть валидным в диапазоне [1, 5]");
        assertFalse(userController.idValidNumeric("abc", 5), "Некорректная строка 'abc' не должна быть валидной");
    }

    @Test
    public void testWrite() {
        String outputText = "Тест";
        userController.write(outputText);
        assertEquals(outputText + "\n", outputStream.toString(), "Метод write() должен выводить сообщение с новой строки");
    }
}
