package minesweeper;

public abstract class Player {
    public String getName() {
        return name;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    private String name;
    private int currentScore;

    public Player(String _name,int _currentScore){
        name=_name;
        currentScore=_currentScore;
    }

    abstract PlayerMove GetPlayerMove();
}
