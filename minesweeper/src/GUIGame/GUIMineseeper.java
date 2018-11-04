package GUIGame;

import ConsoleGame.ConsolePlayer;
import Models.Player.DumbPlayer;
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
    private static final Player guiplayer1=new GUIPlayer("Kareem");
    private static final Player guiplayer2=new GUIPlayer("Mouaz");
    private static final Player dumbplayer=new DumbPlayer(10,10);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window=primaryStage;
        // For Start The Game
        List<Player> Players=new ArrayList<Player>();
        Players.add(guiplayer1);
        Players.add(guiplayer2);
        Players.add(dumbplayer);
        guiGame=new GUIGame(Players);
        guiGame.StartGame();
        
        window.setTitle("MineSweeper");
        window.setScene(guiGame.getScene());
        window.show();
    }
}
