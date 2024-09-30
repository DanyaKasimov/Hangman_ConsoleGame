package backend.academy.dictionary;

import backend.academy.config.Config;
import backend.academy.constants.Category;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Word {
    private String word;

    private Category category;

    private String hint;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) && category == word1.category && Objects.equals(hint, word1.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, category, hint);
    }

    public boolean isLetterInWord(char letter) {
        char letterUp =  String.valueOf(letter).toUpperCase().charAt(0);
        char[] letters = word.toUpperCase().toCharArray();
        for (char l : letters) {
            if (l == letterUp || letterUp == Config.HINT_SYMBOL) {
                return true;
            }
        }
        return false;
    }
}
