package GUIGame;

import minesweeper.*;

public class GUIPlayer extends Player {


    public GUIPlayer(String _name, int _currentScore) { super(_name, _currentScore); }
    @Override
    public PlayerMove GetPlayerMove() {
        return new PlayerMove(this,new Square(0,0,false,0), MoveType.Reveal,new MoveResult());
    }
}
