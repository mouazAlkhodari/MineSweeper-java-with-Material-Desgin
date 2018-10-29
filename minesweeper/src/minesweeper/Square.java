package minesweeper;

import java.util.ArrayList;

enum SquareStatus { 
    Closed,OpenedEmpty,OpenedNumber,OpenedMine,Marked;
}
public class Square {
    private int x;
    private int y;
    private int NumberOfSurroundedMines;
    private Mine mine;
    private ArrayList playersMoves;
    SquareStatus status;

    // <__ CONSTRUCTOR __> \\
    public Square(int x, int y,Boolean hasMine,int NOSM) {
        this.x = x;
        this.y = y;
        this.mine = hasMine ? new Mine() : null;
        this.playersMoves = new ArrayList();
        this.status = SquareStatus.Closed;
        this.NumberOfSurroundedMines = NOSM;
        
    }
    public void ChangeStatus(Player PlayerWhoMadeTheMove,MoveType Type) { 
        playersMoves.add(PlayerWhoMadeTheMove);
        switch (Type) { 
            case Mark: 
                this.status = (this.status == SquareStatus.Marked ? SquareStatus.Closed : SquareStatus.Marked);
                break;
            case Reveal:
                this.status = (this.mine != null ? SquareStatus.OpenedMine : this.NumberOfSurroundedMines == 0 ? SquareStatus.OpenedEmpty :SquareStatus.OpenedNumber);
                break;
        }
    }
    public Boolean isMine() {
        return mine == null ? false : true;
    }
    // Getters
    public int getNumberOfSurroundedMines() { return this.NumberOfSurroundedMines;}
    public int getX() { return this.x;}
    public int getY() { return this.y;}
    public SquareStatus getStatus() {
        return status;
    }
}
