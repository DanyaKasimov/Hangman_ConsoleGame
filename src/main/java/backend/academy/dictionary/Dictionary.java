package backend.academy.dictionary;

import backend.academy.config.Config;
import backend.academy.constants.Category;
import backend.academy.constants.Difficult;
import backend.academy.exceptions.InvalidDataException;
import backend.academy.state.Parameters;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings({"MagicNumber"})
public class Dictionary {
    private final Map<Difficult, List<Word>> dictionary;

    public Dictionary() {
        dictionary = new HashMap<>();
    }

    public void defaultInitialization() {
        dictionary.put(Difficult.EASY, new ArrayList<>(Arrays.asList(
            new Word("яблоко", Category.FOOD, "Плод дерева в саду"),
            new Word("Казань", Category.CITY, "Татарстан"),
            new Word("Китай", Category.COUNTRY, "Дракон"),
            new Word("Кошка", Category.ANIMALS, "Гроза мышей")
        )));

        dictionary.put(Difficult.MEDIUM, new ArrayList<>(Arrays.asList(
            new Word("тарантул", Category.ANIMALS, "Паук"),
            new Word("Бобер", Category.ANIMALS, "Строит плотину"),
            new Word("Аргентина", Category.COUNTRY, "Родина Леонеля Месси"),
            new Word("Амстердам", Category.CITY, "Город в Нидерландах"),
            new Word("Холодец", Category.FOOD, "Желе из мяса")
        )));

        dictionary.put(Difficult.HARD, new ArrayList<>(Arrays.asList(
            new Word("Анадырь", Category.CITY, "Город на востоке"),
            new Word("касатка", Category.ANIMALS, "Черно-белый кит"),
            new Word("Индонезия", Category.COUNTRY, "Java"),
            new Word("Сельдерей", Category.FOOD, "Похож на петрушку")
        )));
    }

    public void addDictionary(Difficult difficult, List<Word> words) {
        if (dictionary.containsKey(difficult)) {
            dictionary.get(difficult).addAll(words);
        } else {
            dictionary.put(difficult, words);
        }
    }

    public void loadFromFile(String filename) throws InvalidDataException {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + filename);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Difficult difficult = Difficult.valueOf(parts[0]);
                    String word = parts[1];
                    Category category = Category.valueOf(parts[2]);
                    String description = parts[3];

                    if (word.length() > Config.MAX_LENGTH_WORD || word.length() < Config.MIN_LENGTH_WORD) {
                       throw new InvalidDataException("Длина слова должна быть не меньше "
                           + Config.MIN_LENGTH_WORD
                           + " и не больше " + Config.MAX_LENGTH_WORD + " символов.");
                    }

                    Word newWord = new Word(word, category, description);
                    dictionary.computeIfAbsent(difficult, k -> new ArrayList<>()).add(newWord);
                }
            }
        } catch (IOException e) {
            throw new InvalidDataException("Ошибка ввода/вывода данных из файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Ошибка при обработке данных из файла: " + e.getMessage());
        } catch (NullPointerException e) {
            throw new InvalidDataException("Ошибка поиска файла: " + e.getMessage());
        }
    }

    public Word getWordByDifficultAndCategory(Difficult difficult, Category category) {
        return getRandomWordFromList(getAllWordsByDifficultAndCategory(difficult, category));
    }

    public Word getWordByCategory(Category category) {
        return getRandomWordFromList(getAllWordsByCategory(category));
    }

    public Word getWordByDifficult(Difficult difficult) {
        return getRandomWordFromList(getAllWordsByDifficult(difficult));
    }

    public Word getWord() {
        return getRandomWordFromList(getAllWords());
    }

    public Word getWordByParameters(Parameters parameters) {
        Word word;

        if (parameters.difficult() != null && parameters.category() != null) {
            return getWordByDifficultAndCategory(parameters.difficult(), parameters.category());
        }

        if (parameters.difficult() != null) {
            word = getWordByDifficult(parameters.difficult());
            parameters.category(word.category());
            return word;
        }

        if (parameters.category() != null) {
            word = getWordByCategory(parameters.category());
            parameters.difficult(getDifficultByWord(word));
            return word;
        }

        word = getWord();
        parameters.difficult(getDifficultByWord(word));
        parameters.category(word.category());

        return word;
    }

    public void clear() {
        dictionary.clear();
    }

    public Difficult getDifficultByWord(Word word) {
        for (Map.Entry<Difficult, List<Word>> entry : dictionary.entrySet()) {
            Difficult difficult = entry.getKey();
            List<Word> words = entry.getValue();

            if (words.contains(word)) {
                return difficult;
            }
        }
        throw new InvalidDataException("Слово не найдено в словаре");
    }

    public Word getRandomWordFromList(List<Word> wordsList) {
        Random random = new Random();
        int randomIndex = random.nextInt(wordsList.size());
        return wordsList.get(randomIndex);
    }

    public List<Word> getAllWords() {
        List<Word> allWords = new ArrayList<>();

        for (List<Word> wordList : dictionary.values()) {
            allWords.addAll(wordList);
        }

        if (allWords.isEmpty()) {
            throw new InvalidDataException("Ошибка получения полного списка слов из словаря");
        }

        return allWords;
    }

    public List<Word> getAllWordsByDifficult(Difficult level) {
        List<Word> wordList = dictionary.get(level);

        if (wordList == null || wordList.isEmpty()) {
            throw new InvalidDataException("Ошибка получения списка слов по уровню из словаря");
        }

        return wordList;
    }

    public List<Word> getAllWordsByDifficultAndCategory(Difficult level, Category category) {
        List<Word> filteredList = getAllWordsByDifficult(level).stream()
            .filter(word -> word.category().equals(category))
            .toList();

        if (filteredList.isEmpty()) {
            throw new InvalidDataException("Ошибка получения списка слов по уровню и категории из словаря");
        }

        return filteredList;
    }

    public List<Word> getAllWordsByCategory(Category category) {
        List<Word> filteredList = getAllWords().stream()
            .filter(word -> word.category().equals(category))
            .toList();

        if (filteredList.isEmpty()) {
            throw new InvalidDataException("Ошибка получения списка слов по категории из словаря");
        }

        return filteredList;
    }

}
