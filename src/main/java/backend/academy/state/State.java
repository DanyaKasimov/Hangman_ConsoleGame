package backend.academy.state;

import backend.academy.dictionary.Word;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class State {
    private int attempts;
    private Word word;
    private String guessedWord;
    private Boolean isRunning;
    private Boolean isWinner;
    private Boolean isLetterInWord;
    private char lastLetter;
    private Parameters parameters;
    private Set<Character> usedLetters;

    public State() {
        this.usedLetters = new HashSet<>();
    }

    public void addUsedLetter(char letter) {
        usedLetters.add(letter);
    }

    public boolean isUsedLetter(char letter) {
        return usedLetters.contains(letter);
    }

    public boolean isRunning() {
        return attempts() > 0 && isRunning;
    }

    public void checkGameStatus() {
        if (isRunning() && word().word().equalsIgnoreCase(guessedWord)) {
            isWinner(true);
            isRunning(false);
        }
        if (!isRunning()) {
            isRunning(false);
        }
    }
}
