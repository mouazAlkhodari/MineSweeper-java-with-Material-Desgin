package GUIGame;

import Models.ScoreBoard.PlayerBoard;
import Models.ScoreBoard.ScoreBoard;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
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
        initTable();
        layout.setTop(Top);
        layout.setCenter(table);
        layout.getStylesheets().addAll("Styles/style.css");
        layout.getStyleClass().addAll("windowsize");
    }

    void initTable() {
      //init Columns
        TableColumn<PlayerBoard, String> PlayerNameColumn = new TableColumn<>("Player Name");
        PlayerNameColumn.setMinWidth(150);
        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("PlayerName"));
        TableColumn<PlayerBoard, String> GameTimeColumn = new TableColumn<>("Game Time");
        GameTimeColumn.setMinWidth(150);
        GameTimeColumn.setCellValueFactory(new PropertyValueFactory<>("GameTime"));
        TableColumn<PlayerBoard, Integer> FinalScoreColumn = new TableColumn<>("Final Score");
        FinalScoreColumn.setMinWidth(150);
        FinalScoreColumn.setCellValueFactory(new PropertyValueFactory<>("FinalScore"));
        TableColumn<PlayerBoard, Integer> ShieldsRemainingColumn = new TableColumn<>("Shields Remaining");
        ShieldsRemainingColumn.setMinWidth(150);
        ShieldsRemainingColumn.setCellValueFactory(new PropertyValueFactory<>("ShieldsRemaining"));
        TableColumn<PlayerBoard, String> GameDifficultyColumn = new TableColumn<>("Game Difficulty");
        GameDifficultyColumn.setMinWidth(150);
        GameDifficultyColumn.setCellValueFactory(new PropertyValueFactory<>("GameDifficulty"));

        table = new TableView<>();
        table.setItems(scoreboard);
        table.getColumns().addAll(PlayerNameColumn,GameTimeColumn,FinalScoreColumn,ShieldsRemainingColumn,GameDifficultyColumn);
    }


    @Override
    public void UpdateView() {
            table.setItems(scoreboard);
    }
}
