package backend.academy.ui.impl;

import backend.academy.ui.Hangman;

public class HangmanImpl implements Hangman {

    private final String[] hangmanViews = {
        """

                +-------+
                |       |
                0       |
               /|\\      |
               / \\      |
                        |
        =========================""",
        """

                +-------+
                |       |
                0       |
               /|\\      |
               /        |
                        |
        =========================""",
        """

                +-------+
                |       |
                0       |
               /|\\      |
                        |
                        |
        =========================""",

        """

                +-------+
                |       |
                0       |
               /|       |
                        |
                        |
        =========================""",

        """

                +-------+
                |       |
                0       |
                |       |
                        |
                        |
        =========================""",

        """

                +-------+
                |       |
                0       |
                        |
                        |
                        |
        =========================""",
        """

                +-------+
                |       |
                        |
                        |
                        |
                        |
        =========================""",

        """

                +-------+
                        |
                        |
                        |
                        |
                        |
        =========================""",
        """


                        |
                        |
                        |
                        |
                        |
        =========================""",
        """






        ========================="""

    };

    @Override
    public String getHangmanByAttempts(int attempts) {
        if (attempts < 0 || attempts > hangmanViews.length) {
            throw new RuntimeException();
        }
        return hangmanViews[attempts];
    }
}
