package GUIGame;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Models.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class GUIMineseeper extends Application {
    private Stage window;
    GUIGame guiGame;
    BorderPane layout;
    private static final Player guiplayer=new GUIPlayer("flan");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window=primaryStage;
        // For Start The Game
        List<Player> Players=new ArrayList<Player>();
        Players.add(guiplayer);

        guiGame=new GUIGame(Players);
        layout=new BorderPane();
        layout.setCenter(guiGame.getFXgrid());
        layout.setRight(guiGame.getScoreBoard());

        // testing
        VBox testPanel=new VBox();
        Label playerNameLabel=new Label(guiplayer.getName());
        Label playerScoreLabel=new Label(String.valueOf(guiplayer.getCurrentScore().getScore()));
        playerScoreLabel.textProperty().bind(new SimpleIntegerProperty(guiplayer.getCurrentScore().getScore()).asString());
        testPanel.getChildren().addAll(playerNameLabel,playerScoreLabel);
        layout.setLeft(testPanel);
        guiplayer.getCurrentScore().addPoints(2);

        guiGame.getFXgrid().setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("Styles/style.css");
        guiGame.StartGame();
        window.setTitle("MineSweeper");
        window.setScene(scene);
        window.show();
    }
}
