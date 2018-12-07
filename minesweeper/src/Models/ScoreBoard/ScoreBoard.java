package Models.ScoreBoard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public abstract class ScoreBoard {
    protected ObservableList<PlayerBoard> scoreboard = FXCollections.observableArrayList();

    public void AddPlayer(PlayerBoard _player) {
        scoreboard.add(_player);
        UpdateView();
    }
    public abstract void UpdateView();

}
