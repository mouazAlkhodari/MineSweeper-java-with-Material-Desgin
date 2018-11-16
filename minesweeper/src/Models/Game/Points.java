package Models.Game;

import Models.Player.Player;

public class Points {
        int RevealFloodFill;
        int RevealEmpty;
        int RevealMine;
        int MarkMine;
        int MarkNotMine;
        int Unmarkmine;
        int UnmarkNotMine;
        int LastNumber;

    public Points() { this(1,10,-250,5,-1,-5,1,0); }

    public Points(int revealFloodFill, int revealEmpty, int revealMine, int markMine, int markNotMine, int unmarkmine, int unmarkNotMine, int lastNumber) {
        RevealFloodFill = revealFloodFill;
        RevealEmpty = revealEmpty;
        RevealMine = revealMine;
        MarkMine = markMine;
        MarkNotMine = markNotMine;
        Unmarkmine = unmarkmine;
        UnmarkNotMine = unmarkNotMine;
        LastNumber = lastNumber;
    }

    public void addRevealEmptyPoints(Player currentPlayer) { currentPlayer.getCurrentScore().addPoints(RevealEmpty); }
    public void addRevealMinePoints(Player currentPlayer) { currentPlayer.getCurrentScore().addPoints(RevealMine); }
    public void addMarkMinePoints(Player currentPlayer) { currentPlayer.getCurrentScore().addPoints(MarkMine); }
    public void addMarkNotMinePoints(Player currentPlayer) { currentPlayer.getCurrentScore().addPoints(MarkNotMine); }
    public void addUnmarkMinePoints(Player currentPlayer) { currentPlayer.getCurrentScore().addPoints(Unmarkmine); }
    public void addUnmarkNotMinePoints(Player currentPlayer) { currentPlayer.getCurrentScore().addPoints(UnmarkNotMine); }
    public void addRevealEmptyPontis(Player currentPlayer) { currentPlayer.getCurrentScore().addPoints(RevealEmpty); }
    public void addRevealFloodFill(Player currentPlayer,int SquaresNumber) { currentPlayer.getCurrentScore().addPoints(RevealFloodFill*(SquaresNumber - 1) + RevealEmpty); }
}