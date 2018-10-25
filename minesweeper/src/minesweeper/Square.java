package minesweeper;

import java.util.ArrayList;

enum SquareStatus { 
    Closed,OpenedEmpty,OpenedNumber,OpenedMine,Marked;
}
public class Square {
    private int x;
    private int y;
    private Mine mine;
    private ArrayList playersMoves;

    public Square(int x, int y,Boolean hasMine) {
        this.x = x;
        this.y = y;
        this.mine = hasMine ? new Mine() : null;
        this.playersMoves = new ArrayList();
    }
    
    public Boolean isMine() { 
        return mine == null ? false : true;
    }
         
}
