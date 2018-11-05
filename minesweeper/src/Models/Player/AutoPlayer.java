package Models.Player;

import Models.Move.PlayerMove;

public abstract class AutoPlayer extends Player {

    // Constructors
    public AutoPlayer() { super(); }
    public AutoPlayer(String _name){ super(_name); }
    public AutoPlayer(String _name,String _color){ super(_name,_color); }
    public AutoPlayer(String _name,PlayerStatus _playerStatus){ super(_name,_playerStatus); }
    public AutoPlayer(String name, Score currentScore, PlayerStatus currentStatus,String _color) { super(name,currentScore,currentStatus,_color); }
    public AutoPlayer(String name,PlayerStatus currentStatus,String _color) { super(name,currentStatus,_color); }


    @Override
    public abstract PlayerMove GetPlayerMove();
}
