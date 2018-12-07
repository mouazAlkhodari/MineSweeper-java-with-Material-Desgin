package GUIGame;

import Models.ScoreBoard.PlayerBoard;
import Models.ScoreBoard.ScoreBoard;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIScoreBoard extends ScoreBoard {
    public Scene scene;
    BorderPane layout = new BorderPane();
    HBox Top = new HBox();
    TableView <PlayerBoard> table;

    Label Title = new Label("SCOREBOARD");
    public GUIScoreBoard() {
        initScene();
    }
    void initScene() {
        initLayout();
        scene = new Scene(layout);
    }

    void initLayout() {
        //init TOP
        Title.getStyleClass().addAll("h1");
        Top.getStyleClass().addAll("playerboard");
        Top.getChildren().addAll(Title);
        layout.setTop(Top);
        initTable();
        layout.getStylesheets().addAll("Styles/style.css");
        layout.getStyleClass().addAll("windowsize");
        for (PlayerBoard _player : scoreboard) {
            //init PlayerBoard

        }
    }

    void initTable() {
      //init Columns

    }
}
