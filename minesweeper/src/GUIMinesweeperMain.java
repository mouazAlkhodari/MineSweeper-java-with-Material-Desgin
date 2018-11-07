import ConsoleGame.ConsolePlayer;
import GUIGame.GUIGameWithOptions;
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

public class GUIMinesweeperMain extends Application {
    private Stage window;
    private GUIGameWithOptions currentGame;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window=primaryStage;
        currentGame=new GUIGameWithOptions();
        currentGame.start(window);
    }
}
