package minesweeper;
enum PlayerStatus{
    Playing,waiting,Lose,win
}
public abstract class Player {

    private String name;
    private int currentScore;

    public void setCurrentStatus(PlayerStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public PlayerStatus getCurrentStatus() {
        return currentStatus;
    }

    private PlayerStatus currentStatus;

    // <__ CONSTRUCTER __> \\
    public Player(String _name,int _currentScore){
        name=_name;
        currentScore=_currentScore;
    }

    // Implemented In each Kind Of Players Like Console Or GUI Player
    public abstract PlayerMove GetPlayerMove();
    //Getters
    public int getCurrentScore() {
        return currentScore;
    }
    public String getName() {
        return name;
    }

    public void addScore(int scoreChange){
        currentScore+=scoreChange;
    }
}
