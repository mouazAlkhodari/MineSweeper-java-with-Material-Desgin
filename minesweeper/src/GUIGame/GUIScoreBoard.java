package GUIGame;

import Models.ScoreBoard.PlayerBoard;
import Models.ScoreBoard.ScoreBoard;
import com.jfoenix.controls.JFXRippler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIScoreBoard extends ScoreBoard {
    public Scene scene;
    BorderPane layout = new BorderPane();
    JFXRippler Title = new JFXRippler(new Label("SCOREBOARD"));
    public GUIScoreBoard() {
        initScene();
    }
    void initScene() {
        Title.getStyleClass().addAll("h2");
        layout.setTop(Title);
        layout.getStylesheets().addAll("Styles/style.css");
        layout.getStyleClass().addAll("windowsize");
        initLayout();
        scene = new Scene(layout);
    }

    void initLayout() {
        for (PlayerBoard _player : scoreboard) {

        }
    }
}
