package Models.Player;

import Models.Move.PlayerMove;

public abstract class AutoPlayer extends Player {

    // Constructors
    public AutoPlayer() {
        super();
    }
    public AutoPlayer(String _name) {
        super(_name);
    }
    public AutoPlayer(String _name, Score _currentScore) {
        super(_name, _currentScore);
    }

    @Override
    public abstract PlayerMove GetPlayerMove();
}
