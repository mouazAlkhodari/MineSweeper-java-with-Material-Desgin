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
    private static final int ConstMines = 10;
    private static final int ConstHeight = 10;
    private static final int ConstWidth = 10;
    private static final Player guiplayer=new GUIPlayer("flan",0);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window=primaryStage;
        // For Start The Game
        List<Player> Players=new ArrayList<Player>();
        Players.add(guiplayer);
        guiGame=new GUIGame(ConstWidth,ConstHeight,ConstMines,Players);

        layout=new BorderPane();
        layout.setCenter(guiGame.getFXgrid());
        guiGame.getFXgrid().setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        guiGame.StartGame();
        window.setTitle("Mine Sweeper");
        window.setScene(scene);
        window.show();
    }
}
