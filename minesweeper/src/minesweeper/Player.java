package minesweeper;

public abstract class Player {
    String name;
    int currentScore;

    public Player(String _name,int _currentScore){
        name=_name;
        currentScore=_currentScore;
    }

    abstract PlayerMove GetPlayerMove();
}
