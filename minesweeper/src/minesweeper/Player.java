package minesweeper;

public abstract class Player {
<<<<<<< HEAD
=======
    public String getName() {
        return name;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    // <__ DATA MEMBERS __> \\
>>>>>>> 558f50464b994835ad819fb8d3c9b4816051c794
    private String name;
    private int currentScore;

    // <__ CONSTRUCTER __> \\
    public Player(String _name,int _currentScore){
        name=_name;
        currentScore=_currentScore;
    }

<<<<<<< HEAD
    // Implemented In each Kind Of Players Like Console Or GUI Player
=======
    // <__ SETTER-GETTERS __> \\

    //Getters
>>>>>>> 558f50464b994835ad819fb8d3c9b4816051c794
    abstract PlayerMove GetPlayerMove();

    public String getName() {
        return name;
    }
}
