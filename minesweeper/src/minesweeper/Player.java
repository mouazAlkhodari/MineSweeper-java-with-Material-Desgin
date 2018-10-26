package minesweeper;

public abstract class Player {
    private String name;
    private int currentScore;

    public Player(String _name,int _currentScore){
        name=_name;
        currentScore=_currentScore;
    }

    abstract PlayerMove GetPlayerMove();
}
