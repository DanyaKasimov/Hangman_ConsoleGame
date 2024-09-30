package backend.academy.ui.impl;

import backend.academy.ui.Keyboard;
import backend.academy.ui.constants.Styles;

public class KeyboardImpl implements Keyboard {

    private String keyboard = Styles.BOLD +  """

        Й Ц У К Е Н Г Ш Щ З Х Ъ
         Ф Ы В А П Р О Л Д Ж Э
           Я Ч С М И Т Ь Б Ю
       """ + Styles.RESET;

    @Override
    public void paintLetterRed(char letter) {
        String l = (letter + "").toUpperCase();
        keyboard = keyboard.replace(l, Styles.RED_TEXT + l + Styles.RESET);
    }

    @Override
    public void paintLetterGreen(char letter) {
        String l = (letter + "").toUpperCase();
        keyboard = keyboard.replace(l, Styles.GREEN_TEXT + l + Styles.RESET);
    }

    @Override
    public String getKeyboard() {
        return keyboard;
    }
}
