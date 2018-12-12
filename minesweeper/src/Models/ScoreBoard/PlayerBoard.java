package Models.ScoreBoard;

import SaveLoad.Directories;
import SaveLoad.SaveLoadGame;
import SaveLoad.StringID;

import java.io.Serializable;

import static extensions.BaseAlphabit.Converter.TimeIntToString;

public class PlayerBoard implements Serializable {
    protected String PlayerName;
    protected String GameTime;
    protected int FinalScore;
    protected String GameDifficulty;
    protected String ReplayedGame;
    protected int Mines;
    protected int Shields;

    public String getScoreboardReg() {
        return ScoreboardReg;
    }

    String ScoreboardReg;

    public PlayerBoard(String _playerName,double _timeInSeconds,int _finalScore,int _gameWidth,int _gameHeight,int _mines,int _shields) {
        PlayerName = _playerName;
        GameTime = TimeIntToString(_timeInSeconds);
        FinalScore = _finalScore;
        GameDifficulty = GameDifficultyToString(_gameWidth,_gameHeight);
        Mines = _mines;
        Shields = _shields;
        ReplayedGame = StringID.ReplayID();
        ScoreboardReg=StringID.ScoreBoardID();
        SaveLoadGame.saveObject(Directories.scoreboard,ScoreboardReg,this);
    }

    public String getPlayerName() { return PlayerName; }
    public String getGameTime() { return GameTime; }
    public int getFinalScore() { return FinalScore; }
    public String getGameDifficulty() { return GameDifficulty; }
    public String getReplayedGame() { return ReplayedGame; }
    public int getMines() { return Mines; }
    public int getShields() { return Shields; }





    private String GameDifficultyToString(int width,int height) {
        return "" + width + " x " + height;
    }


}
