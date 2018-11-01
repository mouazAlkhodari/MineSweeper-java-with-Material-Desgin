package minesweeper;
enum PlayerStatus{
    Activ,Lose,win
}
public abstract class Player {

    private String name;
    private int currentScore;

    // <__ CONSTRUCTER __> \\
    public Player(String _name,int _currentScore){
        name=_name;
        currentScore=_currentScore;
    }

    // Implemented In each Kind Of Players Like Console Or GUI Player
    abstract PlayerMove GetPlayerMove();
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
