package Models.Player;

import Models.Move.PlayerMove;

import java.awt.*;

public abstract class Player {

    private String name;
    private Score currentScore;
    private PlayerStatus currentStatus;
    private String color;

    // <__ CONSTRUCTOR __> \\
    public Player() { this("PC",new Score(),PlayerStatus.waiting,"#9B59B6"); }
    public Player(String _name){ this(_name,new Score(),PlayerStatus.waiting,"#9B59B6"); }
    public Player(String _name,String _color){ this(_name,new Score(),PlayerStatus.waiting,_color); }
    public Player(String _name,PlayerStatus _playerStatus){ this(_name,new Score(),_playerStatus,"#9B59B6"); }
    public Player(String name,PlayerStatus currentStatus,String _color) { this(name,new Score(),currentStatus,_color); }
    public Player(String name, Score currentScore, PlayerStatus currentStatus,String _color) {
        this.name = name;
        this.currentScore = currentScore;
        this.currentStatus = currentStatus;
        this.color=_color;
    }

    // Implemented In each Kind Of Players Like Console Or GUI Player
    public abstract PlayerMove GetPlayerMove();

    //Getters And Setters
    public void setCurrentStatus(PlayerStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
    public PlayerStatus getCurrentStatus() {
        return currentStatus;
    }
    public Score getCurrentScore() {
        return currentScore;
    }
    public String getName() {
        return name;
    }
    public String getColor() { return color; }
}
