package minesweeper;

public abstract class Player {
    public String getName() {
        return name;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    // <__ DATA MEMBERS __> \\
    private String name;
    private int currentScore;

    // <__ CONSTRUCTER __> \\
    public Player(String _name,int _currentScore){
        name=_name;
        currentScore=_currentScore;
    }

    // <__ SETTER-GETTERS __> \\

    //Getters
    abstract PlayerMove GetPlayerMove();
}
