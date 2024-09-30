package backend.academy.ui.impl;

import backend.academy.constants.Category;
import backend.academy.constants.Difficult;
import backend.academy.controller.UserController;
import backend.academy.exceptions.InvalidFormatException;
import backend.academy.state.Parameters;
import backend.academy.ui.Menu;
import backend.academy.ui.constants.Interface;

@SuppressWarnings({"MissingSwitchDefault", "MagicNumber"})
public class MenuImpl implements Menu {

    private final UserController controller;

    public MenuImpl(UserController controller) {
        this.controller = controller;
    }

    public Parameters handle() {
        Parameters parameters = new Parameters();
        boolean exit = true;
        write(Interface.START);
        while (exit) {
            write(Interface.MENU);
            if (parameters.category() != null || parameters.difficult() != null) {
                write(parameters.toString());
            }
            switch (userInput(4)) {
                case "1":
                    exit = false;
                    break;
                case "2":
                    parameters.difficult(levelListHandler(parameters.difficult()));
                    break;
                case "3":
                    parameters.category(categoryListHandler(parameters.category()));
                    break;
                case "4":
                    return null;
            }
        }
        return parameters;
    }

    private Difficult levelListHandler(Difficult difficult) {
        write(Interface.DIFFICULT);
        return switch (userInput(5)) {
            case "1" -> Difficult.EASY;
            case "2" -> Difficult.MEDIUM;
            case "3" -> Difficult.HARD;
            case "4" -> null;
            case "5" -> difficult;
            default -> throw new InvalidFormatException("Выбран недопустимый вариант из списка уровня сложности.");
        };
    }

    private Category categoryListHandler(Category category) {
        write(Interface.CATEGORY);
        return switch (userInput(6)) {
            case "1" -> Category.CITY;
            case "2" -> Category.COUNTRY;
            case "3" -> Category.FOOD;
            case "4" -> Category.ANIMALS;
            case "5" -> null;
            case "6" -> category;
            default -> throw new InvalidFormatException("Выбран недопустимый вариант списка категории.");
        };
    }

    private void write(String message) {
        controller.write(message);
    }

    private String userInput(int listSize) {
        String input;
        do {
            input = controller.read("Введите пункт: ").toUpperCase();
            if (!controller.idValidNumeric(input, listSize)) {
                write(Interface.ERROR + " Введите цифру из списка.");
            }
        } while (!controller.idValidNumeric(input, listSize) || input.length() != 1);

        return input;
    }
}

