package minesweeper;

public abstract class Player {
    private String name;
    private int currentScore;

    public Player(String _name,int _currentScore){
        name=_name;
        currentScore=_currentScore;
    }

    // Implemented In each Kind Of Players Like Console Or GUI Player
    abstract PlayerMove GetPlayerMove();

    public String getName() {
        return name;
    }
}
