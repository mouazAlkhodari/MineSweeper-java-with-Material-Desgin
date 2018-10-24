package minesweeper;

public abstract class Player {
    String name;
    int currentScore;
    abstract PlayerMove GetPlayerMove();
}
