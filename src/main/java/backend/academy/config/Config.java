package backend.academy.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Config {

    public static final int ATTEMPTS_EASY = 10;

    public static final int ATTEMPTS_MEDIUM = 7;

    public static final int ATTEMPTS_HARD = 5;

    public static final char HINT_SYMBOL = '*';

    public static final String FILE_PATH = "dictionary.txt";

    public static final char CONTINUE_LETTER = 'Y';

    public static final char EXIT_LETTER = 'N';

    public static final int KEYBOARD_LENGTH = 24;

    public static final int MAX_LENGTH_WORD = 10;

    public static final int MIN_LENGTH_WORD = 3;
}
