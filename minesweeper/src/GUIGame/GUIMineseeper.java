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
        guiGame.StartGame();
        window.setTitle("MineSweeper");
        window.setScene(guiGame.getScene());
        window.show();
    }
}
