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
    

    public Square(int x, int y,Boolean hasMine,int NOSM) {
        this.x = x;
        this.y = y;
        this.mine = hasMine ? new Mine() : null;
        this.playersMoves = new ArrayList();
        this.status = SquareStatus.Closed;
        this.NumberOfSurroundedMines = NOSM;
        
    }
    
    public Boolean isMine() { 
        return mine == null ? false : true;
    }
     public void ApplyChangesToSquare(SquareStatus newStatus,Player PlayerWhoMadeChanges) { 
         this.status = newStatus;
         this.playersMoves.add(PlayerWhoMadeChanges);
     }

    public int getX() { return this.x;}
    public int getY() { return this.x;}
}
