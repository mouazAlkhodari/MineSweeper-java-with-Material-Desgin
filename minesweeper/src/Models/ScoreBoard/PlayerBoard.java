package Models.ScoreBoard;

import Models.Game.Game;

import java.io.Serializable;
import java.sql.Time;

import static extensions.BaseAlphabit.Converter.TimeIntToString;

public class PlayerBoard implements Serializable {
    String PlayerName;
    String GameTime;
    int FinalScore;
    int ShieldsRemaining;
    String GameDifficulty;
    String ReplayedGame;

    public PlayerBoard(String _playerName,double _timeInSeconds,int _finalScore,int _shieldsRemaining,int _gameWidth,int _gameHeight,String _replayerdGame) {
        PlayerName = _playerName;
        GameTime = TimeIntToString(_timeInSeconds);
        FinalScore = _finalScore;
        ShieldsRemaining = _shieldsRemaining;
        GameDifficulty = GameDifficultyToString(_gameWidth,_gameHeight);
        ReplayedGame = _replayerdGame;
    }

    public String getPlayerName() { return PlayerName; }
    public String getGameTime() { return GameTime; }
    public int getFinalScore() { return FinalScore; }
    public int getShieldsRemaining() { return ShieldsRemaining; }
    public String getGameDifficulty() { return GameDifficulty; }
    public String getReplayedGame() { return ReplayedGame; }




    private String GameDifficultyToString(int width,int height) {
        return "" + width + " x " + height;
    }


}
