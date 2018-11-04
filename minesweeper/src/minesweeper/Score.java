package minesweeper;

public class Score {
    private int score;
    public Points points;

    // Inner Class
    class Points {

        int RevealFloodFill;
        int RevealEmpty;
        int RevealMine;
        int MarkMine;
        int MarkNotMine;
        int Unmark;
        int LastNumber;

        public Points(){
            this(1,10,-250,5,-1,-1,0);
            RevealFloodFill = 1;
            RevealEmpty = 10;
            RevealMine = -250;
            MarkMine = 5;
            MarkNotMine = -1;
            Unmark = -1;
        }
        public Points(int revealFloodFill, int revealEmpty, int revealMine, int markMine, int markNotMine, int unmark, int lastNumber) {
            RevealFloodFill = revealFloodFill;
            RevealEmpty = revealEmpty;
            RevealMine = revealMine;
            MarkMine = markMine;
            MarkNotMine = markNotMine;
            Unmark = unmark;
            LastNumber = lastNumber;
        }

    }

    // Constructor
    public Score(Points points) {
        score=0;
        this.points = points;
    }
    public Score(){
        score=0;
        points=new Points();
    }

    //Getters And Setters
    public int getScore() {
        return score;
    }
    public Points getPoints() {
        return points;
    }
    public void setPoints(Points points) {
        this.points = points;
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
