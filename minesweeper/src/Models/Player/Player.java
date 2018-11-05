package Models.Player;

import Models.Move.PlayerMove;

import java.awt.*;

public abstract class Player {

    private String name;
    private Score currentScore;
    private PlayerStatus currentStatus;
    private String color;

    // <__ CONSTRUCTOR __> \\
    public Player(){
        name="PC";
        currentScore=new Score();
        color = "#9B59B6";
        currentStatus=PlayerStatus.waiting;
    }
    public Player(String _name,String _color){
        name=_name;
        currentScore=new Score();
        color = _color;
        currentStatus=PlayerStatus.waiting;

    }
    public Player(String _name,Score _currentScore,String _color){
        name=_name;
        color = _color;
        currentScore=_currentScore;
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
