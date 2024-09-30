import backend.academy.config.Config;
import backend.academy.constants.Category;
import backend.academy.dictionary.Dictionary;
import backend.academy.constants.Difficult;
import backend.academy.dictionary.Word;
import backend.academy.exceptions.InvalidDataException;
import backend.academy.state.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class DictionaryTest {

    private Dictionary dictionary;

    @BeforeEach
    public void setUp() {
        dictionary = new Dictionary();
        dictionary.defaultInitialization();
    }

    @Test
    public void testDefaultInitialization() {
        List<Word> easyWords = dictionary.getAllWordsByDifficult(Difficult.EASY);
        assertEquals(4, easyWords.size(), "Количество слов для уровня EASY должно быть 4");

        List<Word> mediumWords = dictionary.getAllWordsByDifficult(Difficult.MEDIUM);
        assertEquals(5, mediumWords.size(), "Количество слов для уровня MEDIUM должно быть 5");

        List<Word> hardWords = dictionary.getAllWordsByDifficult(Difficult.HARD);
        assertEquals(4, hardWords.size(), "Количество слов для уровня HARD должно быть 4");
    }

    @Test
    public void testGetWordByLevelAndCategory() {
        Word word = dictionary.getWordByDifficultAndCategory(Difficult.HARD, Category.COUNTRY);
        assertNotNull(word, "Слово для уровня HARD и категории COUNTRY не должно быть null");
        assertEquals(Category.COUNTRY, word.category(), "Слово должно принадлежать к категории COUNTRY");
        assertTrue(dictionary.getAllWordsByDifficult(Difficult.HARD).contains(word),
            "Слово должно принадлежать к уровню MEDIUM");
    }

    @Test
    public void testGetWordByParameters() {
        Parameters parameters = new Parameters();
        parameters.difficult(Difficult.HARD);
        parameters.category(Category.CITY);

        Word word = dictionary.getWordByParameters(parameters);
        assertNotNull(word, "Слово по параметрам не должно быть null");
        assertEquals(Difficult.HARD, parameters.difficult(), "Параметры должны содержать уровень HARD");
        assertEquals(Category.CITY, parameters.category(), "Параметры должны содержать категорию CITY");
    }

    @Test
    public void testGetDifficultByWord() {
        Word word = new Word("яблоко", Category.FOOD, "Плод дерева в саду");
        Difficult level = dictionary.getDifficultByWord(word);
        assertEquals(Difficult.EASY, level, "Уровень для слова 'Кошка' должен быть EASY");
    }

    @Test
    public void testAddDictionary() {
        List<Word> newWords = List.of(
            new Word("Москва", Category.CITY, "Столица России"),
            new Word("Волк", Category.ANIMALS, "Лесная собака")
        );
        dictionary.addDictionary(Difficult.EASY, newWords);

        List<Word> easyWords = dictionary.getAllWordsByDifficult(Difficult.EASY);
        assertTrue(easyWords.containsAll(newWords), "Новые слова должны быть добавлены в уровень EASY");
    }

    @Test
    public void testClear() {
        dictionary.clear();
        assertThrows(InvalidDataException.class, () -> dictionary.getAllWords(),
            "После очистки словаря должен быть выброшен InvalidDataException");
        assertThrows(InvalidDataException.class, () -> dictionary.getAllWordsByDifficult(Difficult.EASY),
                "После очистки словаря должен быть выброшен InvalidDataException");
        assertThrows(InvalidDataException.class, () -> dictionary.getAllWordsByCategory(Category.FOOD),
                "После очистки словаря должен быть выброшен InvalidDataException");
        assertThrows(InvalidDataException.class, () -> dictionary.getAllWordsByDifficultAndCategory(Difficult.HARD, Category.CITY),
                "После очистки словаря должен быть выброшен InvalidDataException");
    }

    @Test
    public void testLoadFromFile() {
        dictionary.clear();
        dictionary.loadFromFile(Config.FILE_PATH);

        List<Word> easyWords = dictionary.getAllWordsByDifficult(Difficult.EASY);
        List<Word> mediumWords = dictionary.getAllWordsByDifficult(Difficult.MEDIUM);
        List<Word> hardWords = dictionary.getAllWordsByDifficult(Difficult.HARD);
        assertFalse(easyWords.isEmpty(), "Должны быть загружены слова для уровня EASY");
        assertFalse(mediumWords.isEmpty(), "Должны быть загружены слова для уровня MEDIUM");
        assertFalse(hardWords.isEmpty(), "Должны быть загружены слова для уровня HARD");

        dictionary.clear();
        assertThrows(InvalidDataException.class, () ->  dictionary.loadFromFile("unknown.txt"),
                "При неизвестном файле должен быть выброшен InvalidDataException");

        assertThrows(InvalidDataException.class, () -> dictionary.loadFromFile("dictionary_test.txt"),
            "При некорректной длине слова должны быть выброшен InvalidDataException");
    }
}
