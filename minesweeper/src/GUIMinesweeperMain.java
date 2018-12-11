import GUIGame.GUIGameMainMenu;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.JFXAnimationTimer;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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
        VBox layout=new VBox();

        JFXSpinner spinner = new JFXSpinner();
        layout.getChildren().addAll(spinner);
        Scene scene = new Scene(layout);
//        window.setScene(scene);
// window.show();
    }
}
