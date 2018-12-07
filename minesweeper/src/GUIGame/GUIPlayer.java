package GUIGame;

import Models.Player.Player;
import Models.Move.PlayerMove;
import Models.Player.PlayerStatus;
import Models.Player.Score;

public class GUIPlayer extends Player {

    public GUIPlayer() { super(); }
    public GUIPlayer(String _name){ super(_name); }
    public GUIPlayer(String _name,String _color){ super(_name,_color); }
    public GUIPlayer(String _name, PlayerStatus _playerStatus){ super(_name,_playerStatus); }
    public GUIPlayer(String name,PlayerStatus currentStatus,String _color) { super(name,currentStatus,_color); }
    public GUIPlayer(String name, Score currentScore, PlayerStatus currentStatus,String _color) { super(name,currentScore,currentStatus,_color); }

    public GUIPlayer(String name,String color,int numberOfShield,int maxNumberOfShild){
        super(name,new Score(),PlayerStatus.waiting,color,numberOfShield,maxNumberOfShild);
    }
    @Override
    public PlayerMove GetPlayerMove() {
        return  new PlayerMove(this);
    }
}
