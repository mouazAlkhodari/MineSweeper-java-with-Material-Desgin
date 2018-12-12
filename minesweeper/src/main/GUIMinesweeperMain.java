package main;

import GUIGame.GUIGameMainMenu;
import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;


public class GUIMinesweeperMain extends Application {
    private Stage window;
    private GUIGameMainMenu currentGame;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window=primaryStage;
        window.setResizable(false);
        window.initStyle(StageStyle.DECORATED);
        currentGame=new GUIGameMainMenu();
        currentGame.start(window);
//        VBox layout=new VBox();
//
//        JFXSpinner spinner = new JFXSpinner();
//        layout.getChildren().addAll(spinner);
//        Scene scene = new Scene(layout);
//        window.setScene(scene);
// window.show();
    }
}
