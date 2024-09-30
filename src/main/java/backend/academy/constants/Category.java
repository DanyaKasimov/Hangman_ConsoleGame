package backend.academy.constants;


public enum Category {
    CITY,
    COUNTRY,
    FOOD,
    ANIMALS;

    public static String convertToString(Category category) {
        return switch (category) {
            case CITY -> "Город";
            case COUNTRY -> "Страна";
            case FOOD -> "Еда";
            case ANIMALS -> "Животные";
        };
    }

}
