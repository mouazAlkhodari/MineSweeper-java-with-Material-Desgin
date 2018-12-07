package GUIGame;

import GUIGame.GUIElements.MenuButton;
import GUIGame.GUIElements.Top;
import Models.ScoreBoard.PlayerBoard;
import Models.ScoreBoard.ScoreBoard;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class GUIScoreBoard extends ScoreBoard {
    public Scene scene;
    protected BorderPane layout = new BorderPane();
    protected Top Top = new Top("SCOREBOARD");
    protected HBox footer;
    protected MenuButton BackButton = new MenuButton("Back");
    protected MenuButton ReplayButton = new MenuButton("Replay");
    protected TableView <PlayerBoard> table;
    protected  GUIGameMainMenu Begin;

    public GUIScoreBoard(GUIGameMainMenu menu) {
        initScene();
        UpdateView();
        Begin = menu;
    }
    void initScene() {
        initLayout();
        scene = new Scene(layout);
        table.prefWidthProperty().bind(scene.widthProperty());
    }

    void initLayout() {
             //init Tabele
        initTable();

        //inti FOOTER
        BackButton.setOnAction(e -> {
            Begin.Window.setScene(Begin.getWelcomescene());
            Begin.Window.centerOnScreen();
    });
        ReplayButton.setOnAction(e -> {
            Begin.replayGame(table.getSelectionModel().getSelectedItem().getReplayedGame());
        });

        footer=new HBox(80);
        footer.setPadding(new Insets(20));
        footer.getStyleClass().addAll("center");
        footer.getChildren().addAll(BackButton,ReplayButton);


        layout.setTop(Top);
        layout.setCenter(table);
        layout.setBottom(footer);
        layout.getStylesheets().addAll("Styles/style.css");
        layout.getStyleClass().addAll("windowsize750");
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
        table.getStylesheets().addAll("Styles/style.css","Styles/table.css");
        table.setItems(scoreboard);
        table.setFixedCellSize(25);
        table.getColumns().addAll(PlayerNameColumn,GameTimeColumn,FinalScoreColumn,ShieldsRemainingColumn,GameDifficultyColumn);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));
    }

    @Override
    public void UpdateView() {
            table.setItems(scoreboard);
    }
}
