package Models.ScoreBoard;

import GUIGame.GUIGame;
import Models.Player.Player;
import Models.Player.Score;
import SaveLoadPackage.Directories;
import SaveLoadPackage.SaveLoadGame;
import SaveLoadPackage.StringID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ScoreBoard implements Serializable {
    protected ObservableList<PlayerBoard> scoreboard = FXCollections.observableArrayList();

    public ScoreBoard() {
        List<String> names = Directories.getItems(Directories.scoreboard);
        for (String name : names) {
            scoreboard.add(SaveLoadGame.loadGame(Directories.scoreboard,name+".score"));
        }
    }

    public void AddBoard(GUIGame game, Player winner) {
        String ReplayfileName = StringID.ReplayID();
        SaveLoadGame.saveGame(Directories.replay,ReplayfileName,game);
        PlayerBoard _board = new PlayerBoard(winner.getName(),0,winner.getCurrentScore().getScore(),winner.getNumberOfShield(),game.getGrid().getWidth(),game.getGrid().getHeight(),ReplayfileName);
        scoreboard.add(_board);
        SaveLoadGame.saveGame(Directories.scoreboard,StringID.ScoreBoardID(),_board);
        UpdateView();
    }
    public abstract void UpdateView();

}
