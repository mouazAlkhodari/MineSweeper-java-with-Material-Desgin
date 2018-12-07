package Models.Grid;

import Models.Move.MoveType;
import Models.Player.Player;
import Models.Shield.HeroShield;
import Models.Shield.NormalShield;
import Models.Shield.Shield;

import java.io.Serializable;
import java.util.ArrayList;

public class Square implements Serializable {
    // <__ DATA MEMBERS __> \\
    private int x;
    private int y;
    private int NumberOfSurroundedMines;
    private Mine mine;
    private Shield shield;
    private ArrayList playersMoves;
    private SquareStatus status;
    private String Color;

    private SquareType Type;
    // <__ CONSTRUCTER __> \\
    public Square() { this(0,0,SquareType.Empty,0); }
    public Square(int x, int y) { this(x,y,SquareType.Empty,0); }
    public Square(int x, int y, SquareType type, int NOSM) {
        this.x = x;
        this.y = y;
        this.mine = type == SquareType.Mine ? new Mine() : null;
        this.shield = type == SquareType.Shield ? new NormalShield() : type == SquareType.HeroShield ? new HeroShield() : null;
        this.playersMoves = new ArrayList();
        this.status = SquareStatus.Closed;
        this.NumberOfSurroundedMines = NOSM;
        this.Type=type;
    }
    // <__ METHODS __> \\
    public void ChangeStatus(Player PlayerWhoMadeTheMove, MoveType Type) {
        playersMoves.add(PlayerWhoMadeTheMove);
        Color=PlayerWhoMadeTheMove.getColor();
        switch (Type) { 
            case Mark: 
                status = (status == SquareStatus.Marked ? SquareStatus.Closed : SquareStatus.Marked);
                break;
            case Reveal:
                this.status = (this.mine != null ? SquareStatus.OpenedMine : this.NumberOfSurroundedMines == 0 ? SquareStatus.OpenedEmpty :SquareStatus.OpenedNumber);
                break;
        }
    }
    // <__ SETTERS-GETTERS __> \\

    public void setColor(String color) { Color = color; }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void reset(){
        this.playersMoves = new ArrayList();
        this.status = SquareStatus.Closed;
    }
    // Getters

    public int getNumberOfSurroundedMines() { return this.NumberOfSurroundedMines;}
    public int getX() { return this.x;}
    public int getY() { return this.y;}
    public Boolean isMine() { return mine == null ? false : true; }
    public Boolean hasNormalSield() { return shield == null ? false : (shield instanceof NormalShield); }
    public Boolean hasHeroSield() { return shield == null ? false : (shield instanceof HeroShield); }
    public SquareStatus getStatus() { return status; }
    public String getColor() { return Color; }

}
