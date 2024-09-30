package backend.academy.handler.impl;

import backend.academy.config.Config;
import backend.academy.dictionary.Dictionary;
import backend.academy.dictionary.Word;
import backend.academy.handler.GameHandler;
import backend.academy.state.Parameters;
import backend.academy.state.State;

public class GameHandlerImpl implements GameHandler {

    private State state;
    private final Dictionary dictionary;

    public GameHandlerImpl(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void init(Parameters parameters) {
        this.state = new State();
        state.isRunning(true);
        state.parameters(parameters);
        state.word(getWord());
        state.attempts(getAttempts());
        state.isWinner(false);
        state.guessedWord("_".repeat(state.word().word().length()));
    }

    @Override
    public void handle(char letter) {
        boolean isInWord = state.word().isLetterInWord(letter);

        if (!isInWord) {
            state.attempts(state.attempts() - 1);
        }

        state.lastLetter(letter);
        updateGuessedWordWithLetter();
        if (state.lastLetter() != Config.HINT_SYMBOL) {
            state.addUsedLetter(state.lastLetter());
        }
        state.isLetterInWord(isInWord);
    }

    @Override
    public State getState() {
        return state;
    }

    private Word getWord() {
        return dictionary.getWordByParameters(state.parameters());
    }

    private int getAttempts() {
        return switch (state.parameters().difficult()) {
            case EASY -> Config.ATTEMPTS_EASY;
            case MEDIUM -> Config.ATTEMPTS_MEDIUM;
            case HARD -> Config.ATTEMPTS_HARD;
        };
    }

    private void updateGuessedWordWithLetter() {
        char[] letters = state.word().word().toUpperCase().toCharArray();
        char[] guessedLetters = state.guessedWord().toUpperCase().toCharArray();

        for (int i = 0; i < letters.length; i++) {
            if (state.lastLetter() == letters[i]) {
                guessedLetters[i] = state.lastLetter();
            }
        }

        state.guessedWord(new String(guessedLetters));
    }
}
