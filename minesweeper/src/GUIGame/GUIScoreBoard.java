package GUIGame;

import Models.ScoreBoard.PlayerBoard;
import Models.ScoreBoard.ScoreBoard;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIScoreBoard extends ScoreBoard {
    public Scene scene;
    protected BorderPane layout = new BorderPane();
    protected HBox Top = new HBox();
    protected HBox footer;
    protected Button BackButton;
    protected TableView <PlayerBoard> table;
    protected  GUIGameMainMenu Begin;

    Label Title = new Label("SCOREBOARD");
    public GUIScoreBoard(GUIGameMainMenu menu) {
        initScene();
        Begin = menu;
    }
    void initScene() {
        initLayout();
        scene = new Scene(layout);
        table.prefWidthProperty().bind(scene.widthProperty());
    }

    void initLayout() {
        //init TOP
        Title.getStyleClass().addAll("h1");
        Top.getStyleClass().addAll("playerboard");
        Top.getChildren().addAll(Title);

        //init Tabele
        initTable();

        //inti FOOTER
        BackButton =new Button("Back");
        BackButton.getStyleClass().addAll("menubutton","h3");
        BackButton.setPrefSize(60,40);
        BackButton.setOnAction(e -> {
            Begin.Window.setScene(Begin.getWelcomescene());
            Begin.Window.centerOnScreen();
    });

        footer=new HBox(80);
        footer.setPadding(new Insets(20));
        footer.getStyleClass().addAll("center");
        footer.getChildren().addAll(BackButton);


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
