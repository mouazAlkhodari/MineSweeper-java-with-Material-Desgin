package Models.ScoreBoard;

import GUIGame.GUIGame;
import Models.Player.Player;
import SaveLoad.Directories;
import SaveLoad.SaveLoadGame;
import SaveLoad.StringID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;

public abstract class ScoreBoard implements Serializable {
    protected ObservableList<PlayerBoard> scoreboard = FXCollections.observableArrayList();

    public ScoreBoard() {
        for (String name : Directories.scoreboard.list()) {
            PlayerBoard _board = SaveLoadGame.loadGame(Directories.scoreboard,name);
            if (_board != null) { scoreboard.add(_board); }
        }
    }

    public void AddBoard(GUIGame game, Player winner) {
        String ReplayfileName = StringID.ReplayID();
        SaveLoadGame.saveGame(Directories.replay,ReplayfileName,game);
        PlayerBoard _board = new PlayerBoard(winner.getName(),game.getGameTime(),winner.getCurrentScore().getScore(),winner.getNumberOfShield(),game.getGrid().getWidth(),game.getGrid().getHeight(),ReplayfileName);
        scoreboard.add(_board);
        SaveLoadGame.saveGame(Directories.scoreboard,StringID.ScoreBoardID(),_board);
        UpdateView();
    }
    public abstract void UpdateView();

}
