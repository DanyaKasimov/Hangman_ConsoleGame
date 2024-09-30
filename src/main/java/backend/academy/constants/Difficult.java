package backend.academy.constants;


public enum Difficult {
    EASY,
    MEDIUM,
    HARD;

    public static String convertToString(Difficult level) {
        return switch (level) {
            case EASY -> "Легкий";
            case MEDIUM -> "Средний";
            case HARD -> "Сложный";
        };
    }
}
