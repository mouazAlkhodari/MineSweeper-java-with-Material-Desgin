package Models.ScoreBoard;

import Models.Player.Score;
import SaveLoadPackage.Directories;
import SaveLoadPackage.SaveLoadGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ScoreBoard {
    protected ObservableList<PlayerBoard> scoreboard = FXCollections.observableArrayList();

    public ScoreBoard() {

    }

    public void AddPlayer(PlayerBoard _player) {
        scoreboard.add(_player);
        SaveLoadGame.saveGame(Directories.scoreboard,"test",_player);
        UpdateView();
}
    public abstract void UpdateView();

}
