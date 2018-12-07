import GUIGame.GUIGameMainMenu;
import SaveLoadPackage.StringID;
import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


public class GUIMinesweeperMain extends Application {
    private Stage window;
    private GUIGameMainMenu currentGame;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(StringID.ReplayID());
        System.out.println(StringID.SaveID());
        System.out.println(StringID.ScoreBoardID());
        window=primaryStage;
        currentGame=new GUIGameMainMenu();
        currentGame.start(window);

    }
}
