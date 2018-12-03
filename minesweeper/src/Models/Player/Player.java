package Models.Player;

import Models.Move.PlayerMove;

import java.awt.*;
import java.io.Serializable;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class Player implements Serializable {

    private String name;
    private Score currentScore;
    private PlayerStatus currentStatus;
    private String color;

    int numberOfShield;
    int ShieldCountBegin;
    int maxNumberOfShild;
    boolean canPlay;


    int TimeforTimer=5;
    public int getTimeforTimer() {
        return TimeforTimer;
    }

    public void setTimeforTimer(int timeforTimer) {
        TimeforTimer = timeforTimer;
    }
    public void addNormalshild(int num){
        numberOfShield=min(numberOfShield+num,maxNumberOfShild);
    }
    // <__ CONSTRUCTOR __> \\
    public Player() { this("PC",new Score(),PlayerStatus.waiting,"#b39ddb",0,100000); }

    public Player(String _name){ this(_name,new Score(),PlayerStatus.waiting,"#4527a0",0,100000); }
    public Player(String _name,String _color){ this(_name,new Score(),PlayerStatus.waiting,_color,0,100000); }
    public Player(String _name,PlayerStatus _playerStatus){ this(_name,new Score(),_playerStatus,"#4527a0",0,100000); }
    public Player(String name,PlayerStatus currentStatus,String _color,int _maxNumberOfShild) { this(name,new Score(),currentStatus,_color,0,_maxNumberOfShild); }
    public Player(String name,PlayerStatus currentStatus,String _color) { this(name,new Score(),currentStatus,_color,0,100000); }
    public Player(String name, Score currentScore, PlayerStatus currentStatus,String _color){
        this(name,currentScore,currentStatus,_color,0,100000);
    }
    public Player(String name, Score currentScore, PlayerStatus currentStatus,String _color,int _numberOfShield,int _maxNumberOfShild) {
        this.name = name;
        this.currentScore = currentScore;
        this.currentStatus = currentStatus;
        this.color=_color;
        numberOfShield=_numberOfShield;
        ShieldCountBegin=_numberOfShield;
        maxNumberOfShild=_maxNumberOfShild;
    }

    // Implemented In each Kind Of Players Like Console Or GUI Player
    public abstract PlayerMove GetPlayerMove();

    //Getters And Setters
    public void setCurrentStatus(PlayerStatus currentStatus) {
        this.currentStatus = currentStatus;
        canPlay=true;
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
    public int getNumberOfShield() {
        return numberOfShield;
    }
    public int getMaxNumberOfShild() {
        return maxNumberOfShild;
    }

    public void setMaxNumberOfShields(int maxNumberOfShild) {
        this.maxNumberOfShild = maxNumberOfShild;
    }
    public void setNumberOfShild(int NumberOfShild) {
        this.numberOfShield = min(maxNumberOfShild,Math.max(0,NumberOfShild));
    }

    public void stop(){
        canPlay=false;
    }
    public void Play(){canPlay=true;}

    public void reset(){
        currentStatus=PlayerStatus.waiting;
        currentScore.setScore(0);
        setNumberOfShild(ShieldCountBegin);
    }
}
