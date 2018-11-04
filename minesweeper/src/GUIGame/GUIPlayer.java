package GUIGame;

import Models.Player.Player;
import Models.Move.PlayerMove;
import Models.Player.Score;

public class GUIPlayer extends Player {

    public GUIPlayer(String _name, Score _currentScore) {
        super(_name, _currentScore);
    }
    public GUIPlayer(String _name) { super(_name); }

    @Override
    public PlayerMove GetPlayerMove() {
        return  new PlayerMove(this);
    }
}
