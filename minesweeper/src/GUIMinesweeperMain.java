import GUIGame.GUIGameMainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;


public class GUIMinesweeperMain extends Application {
    private Stage window;
    private GUIGameMainMenu currentGame;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window=primaryStage;
        currentGame=new GUIGameMainMenu();
        currentGame.start(window);

    }
}
