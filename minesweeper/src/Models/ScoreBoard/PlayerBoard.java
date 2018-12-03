package Models.ScoreBoard;

import Models.Game.Game;

import java.sql.Time;

public class PlayerBoard {
    String PlayerName;
    String GameTime;
    int FinalScore;
    int ShieldsRemaining;
    String GameDifficulty;

    public PlayerBoard(String _playerName,int _timeInSeconds,int _finalScore,int _shieldsRemaining,int _gameWidth,int _gameHeight) {
        PlayerName = _playerName;
        GameTime = TimeIntToString(_timeInSeconds);
        FinalScore = _finalScore;
        ShieldsRemaining = _shieldsRemaining;
        GameDifficulty = GameDifficultyToString(_gameWidth,_gameHeight);
    }

    public String getPlayerName() { return PlayerName; }
    public String getGameTime() { return GameTime; }
    public int getFinalScore() { return FinalScore; }
    public int getShieldsRemaining() { return ShieldsRemaining; }
    public String getGameDifficulty() { return GameDifficulty; }


    private String TimeIntToString(int time) {
        int seconds = time%60;
        time /= 60;
        int minutes = time%60;
        time /= 60;
        int hours = time%60;
        return new Time(hours,minutes,seconds).toString().replaceFirst("00:","");
    }
    private String GameDifficultyToString(int width,int height) {
        return "" + width + " x " + height;
    }


}
