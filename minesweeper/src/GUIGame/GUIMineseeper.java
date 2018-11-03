package GUIGame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import minesweeper.Player;

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
        guiGame.getFXgrid().setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("Styles/style.css");
        guiGame.StartGame();
        window.setTitle("MineSweeper");
        window.setScene(scene);
        window.show();
    }
}
