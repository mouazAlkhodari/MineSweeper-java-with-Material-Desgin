package GUIGame;

import minesweeper.*;

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
