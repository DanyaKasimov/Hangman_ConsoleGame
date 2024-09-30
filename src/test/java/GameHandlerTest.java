import backend.academy.config.Config;
import backend.academy.constants.Category;
import backend.academy.dictionary.Dictionary;
import backend.academy.constants.Difficult;
import backend.academy.dictionary.Word;
import backend.academy.handler.GameHandler;
import backend.academy.handler.impl.GameHandlerImpl;
import backend.academy.state.Parameters;
import backend.academy.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GameHandlerTest {

    private GameHandler gameHandler;

    @BeforeEach
    public void setUp() {
        Dictionary dictionary = new Dictionary();
        List<Word> newWords = List.of(
            new Word("Москва", Category.CITY, "Столица России"),
            new Word("Волк", Category.ANIMALS, "Лесная собака")
        );
        dictionary.addDictionary(Difficult.EASY, newWords);

        Parameters params = new Parameters();
        params.difficult(Difficult.EASY);

        gameHandler = new GameHandlerImpl(dictionary);
        gameHandler.init(params);
    }

    @Test
    public void testGameStateAfterInit() {
        State state = gameHandler.getState();

        assertTrue(state.isRunning(), "Игра должна быть запущена после инициализации");
        assertFalse(state.isWinner(), "После инициализации игрок еще не победитель");
        assertNotNull(state.word(), "После инициализации должно быть получено слово");
        assertEquals(Config.ATTEMPTS_EASY, state.attempts(),
            "После инициализации количество попыток должно равняться выбранной сложности");
        assertEquals("_".repeat(state.word().word().length()), state.guessedWord(),
            "Загаданное слово должно быть отображено как пустые символы");
    }

    @Test
    public void testLetterCaseHandling() {
        gameHandler.handle('О');
        State state = gameHandler.getState();
        boolean isInWordUpperCase = state.isLetterInWord();

        gameHandler.handle('о');
        boolean isInWordLowerCase = state.isLetterInWord();

        assertEquals(isInWordUpperCase, isInWordLowerCase,
            "Результат должен быть одинаковым для заглавной и строчной буквы");
    }

    @Test
    public void testGameOverAfterMaxAttempts() {
        State state = gameHandler.getState();

        char[] letters = {'Й','Ц','У','Ш','Щ','З','Ъ','Ю','Ь','Ч'};

        for (int i = 0; i < Config.ATTEMPTS_EASY; i++) {
            gameHandler.handle(letters[i]);
        }

        assertFalse(state.isRunning(),
            "Игра должна завершиться после достижения максимального количества попыток");
        assertFalse(state.isWinner(),
            "Игрок должен быть проигравшим после траты всех попыток");
        assertEquals(0, state.attempts(),
            "Количество оставшихся попыток должно быть 0");
    }

    @Test
    public void testStateChange() {
        State state = gameHandler.getState();

        gameHandler.handle('О');
        assertTrue(state.isLetterInWord(), "Буква должна быть в слове");
        assertTrue(state.guessedWord().contains("О"), "Отгаданное слово должно содержать букву 'О'");
        assertEquals(Config.ATTEMPTS_EASY, state.attempts(),
            "Количество попыток не должно измениться при правильной догадке");

        gameHandler.handle('Щ');
        assertFalse(state.isLetterInWord(), "Буквы 'Щ' не должно быть в слове");
        assertFalse(state.guessedWord().contains("Щ"), "Отгаданное слово не должно содержать букву 'Щ'");
        assertEquals(Config.ATTEMPTS_EASY - 1, state.attempts(),
            "Количество попыток должно уменьшиться при неправильной догадке");
    }
}
