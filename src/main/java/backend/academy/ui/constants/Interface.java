package backend.academy.ui.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Interface {
    public static final String START =  Styles.RED_TEXT + """

        ██|   ██|   █████|    ███|    ██|   ███████|   ███|     ███|   █████|    ███|    ██|
        ██|   ██|  ██|   ██|  ██|██|  ██|  ██|         ████|   ████|  ██|   ██|  ██|██|  ██|
        ████████|  ████████|  ██| ██| ██|  ██|    ██|  ██| ████| ██|  ████████|  ██| ██| ██|
        ██|   ██|  ██|   ██|  ██|  ██|██|  ██|    ██|  ██|  ██|  ██|  ██|   ██|  ██|  ██|██|
        ██|   ██|  ██|   ██|  ██|    ███|   ███████|   ██|       ██|  ██|   ██|  ██|    ███|
        """ + Styles.RESET;

    public static final String MENU =  Styles.BOLD +  """

      █▀▄▀█  █▀▀▀  █▄  █  █  █
      █ █ █  █▀▀▀  █ █ █  █  █
      █   █  █▄▄▄  █  ▀█  ▀▄▄▀

       1. Начать
       2. Выбрать уровень сложности
       3. Выбрать категорию
       4. Выйти
      """ + Styles.RESET;

    public static final String DIFFICULT = Styles.BOLD +  """

       ************************
          Уровень сложности
       ************************
       1. Легкий
       2. Средний
       3. Сложный
       4. Любой
       5. Назад
       """ + Styles.RESET;

    public static final String CATEGORY = Styles.BOLD +  """

       ************************
               Категория
       ************************
       1. Город
       2. Страна
       3. Еда
       4. Животные
       5. Любая
       6. Назад
       """ + Styles.RESET;

    public static final String WIN = Styles.GREEN_TEXT + """

         █   █ ▀█▀  █▄  █
         █ █ █  █   █ █ █
         █▄▀▄█ ▄█▄  █  ▀█
        """ + Styles.RESET;

    public static final String DEFEAT = Styles.RED_TEXT + """

         █▀▀▄  █▀▀▀  █▀▀▀  █▀▀▀  █▀▀█ ▀▀█▀▀
         █  █  █▀▀▀  █▀▀▀  █▀▀▀  █▀▀█   █
         █▄▄▀  █▄▄▄  █     █▄▄▄  █  █   █

        """ + Styles.RESET;

    public static final String ERROR = Styles.RED_TEXT + Styles.BOLD + """
        Ошибка:""" + Styles.RESET;

    public static final String HINT = Styles.PURPLE_TEXT + Styles.BOLD + """
        Подсказка:""" + Styles.RESET;

    public static final String DIFFICULT_TEXT = Styles.ITALIC + Styles.BOLD + """
        Сложность:""" + Styles.RESET;

    public static final String CATEGORY_TEXT = Styles.ITALIC + Styles.BOLD + """
        Категория:""" + Styles.RESET;

    public static final String ATTEMPTS = Styles.ITALIC + Styles.BOLD + """
        Осталось попыток:""" + Styles.RESET;
}
