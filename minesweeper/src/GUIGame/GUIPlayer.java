package GUIGame;

import Models.Player.Player;
import Models.Move.PlayerMove;
import Models.Player.Score;

public class GUIPlayer extends Player {

    public GUIPlayer(String _name, Score _currentScore,String _color) {
        super(_name, _currentScore,_color);
    }
    public GUIPlayer(String _name,String _color) { super(_name,_color); }

    @Override
    public PlayerMove GetPlayerMove() {
        return  new PlayerMove(this);
    }
}
