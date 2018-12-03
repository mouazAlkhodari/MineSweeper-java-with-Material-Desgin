package GUIGame;

import Models.ScoreBoard.PlayerBoard;
import Models.ScoreBoard.ScoreBoard;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIScoreBoard extends ScoreBoard {
    public Scene scene;
    BorderPane layout = new BorderPane();
    HBox Top = new HBox();
    JFXTreeTableView table = new JFXTreeTableView();
    Label Title = new Label("SCOREBOARD");
    public GUIScoreBoard() {
        initScene();
    }
    void initScene() {

        //init Top
        Title.getStyleClass().addAll("h1");
        Top.getStyleClass().addAll("playerboard");
        Top.getChildren().addAll(Title);
        layout.setTop(Top);

        layout.getStylesheets().addAll("Styles/style.css");
        layout.getStyleClass().addAll("windowsize");
        initLayout();
        scene = new Scene(layout);
    }

    void initLayout() {
        for (PlayerBoard _player : scoreboard) {
            //init PlayerBoard

        }
    }
}
