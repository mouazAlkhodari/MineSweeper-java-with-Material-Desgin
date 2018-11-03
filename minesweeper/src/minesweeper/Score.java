package minesweeper;

public class Score {
    class Points {
        int RevealFloodFill;
        int RevealEmpty;
        int RevealMine;
        int MarkMine;
        int MarkNotMine;
        int Unmark;
        int LastNumber;

        public Points() {
            RevealFloodFill = 1;
            RevealEmpty = 10;
            RevealMine = -250;
            MarkMine = 5;
            MarkNotMine = -1;
            Unmark = -1;
        }
    }
    private int score;
    private Points points;
    public Score(){
        score=0;
        points=new Points();
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addRevealEmptyPoints() {
        score+=points.RevealEmpty;
    }
    public void addRevealMinePoints() {
        score+=points.RevealMine;
    }
    public void addMarkMinePoints() {
        score+=points.MarkMine;
    }
    public void addMarkNotMinePoints() {
        score+=points.MarkNotMine;
    }
    public void addUnmarkPoints() {
        score+=points.Unmark;
    }
    public void addRevealEmptyPontis() {
        score+=points.RevealEmpty;
    }
    public void addRevealFloodFill(int SquaresNumber) {
        score+=points.RevealFloodFill*SquaresNumber;
    }
    public void addPoints(int points) {
        score+=points;
    }
}
