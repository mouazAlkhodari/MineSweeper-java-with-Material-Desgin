package Models.Player;

public class Score {
    private int score;

    public Score(){
        score=0;
    }
    //Getters And Setters
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void addPoints(int points) {
        score+=points;
    }
}
