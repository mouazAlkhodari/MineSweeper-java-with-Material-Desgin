package Models.Player;

import Models.Move.PlayerMove;

public abstract class Player {

    private String name;
    private Score currentScore;
    private PlayerStatus currentStatus;

    // <__ CONSTRUCTOR __> \\
    public Player(){
        name="PC";
        currentScore=new Score();
        currentStatus=PlayerStatus.waiting;
    }
    public Player(String _name){
        name=_name;
        currentScore=new Score();
        currentStatus=PlayerStatus.waiting;
    }
    public Player(String _name,Score _currentScore){
        name=_name;
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
}
