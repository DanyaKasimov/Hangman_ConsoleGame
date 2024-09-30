package backend.academy.handler;

import backend.academy.state.Parameters;
import backend.academy.state.State;

public interface GameHandler {
    void init(Parameters parameters);

    void handle(char letter);

    State getState();
}
