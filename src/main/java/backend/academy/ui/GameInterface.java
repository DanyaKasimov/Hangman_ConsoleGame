package backend.academy.ui;

import backend.academy.state.State;

public interface GameInterface {
     void startGame(State state);

     void updateGame(State state);

     void endGame(State state);

     char gameUserInput(State state);

     void write(String message);

     boolean continueGame();
}
