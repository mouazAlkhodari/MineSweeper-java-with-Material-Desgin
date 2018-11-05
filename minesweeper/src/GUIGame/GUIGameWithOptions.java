package GUIGame;


import Models.Game.Game;
import Models.Player.DumbPlayer;
import Models.Player.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class GUIGameWithOptions extends Application {
    WelcomeScene welcomescene = new WelcomeScene();
    OptionsScene optionsScene = new OptionsScene();
    GUIGame guiGame;
    Stage Window;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Window = primaryStage;
        Window.setScene(welcomescene.scene);
        Window.show();
    }

    class WelcomeScene {
        Scene scene;
        VBox WelcomeLayout;
        Label Welcome;
        Button CustomGame;
        Button SimpleGame;

        private void initLayout() {
            WelcomeLayout = new VBox(20);
            Welcome = new Label("MineSweeper");
            CustomGame = new Button("CUSTOM GAME");
            SimpleGame = new Button("SIMPLE GAME");
            CustomGame.getStyleClass().addAll("menubutton","h3");
            SimpleGame.getStyleClass().addAll("menubutton","h3");
            CustomGame.setOnAction(e -> {
                Window.setScene(optionsScene.scene);
            });
            SimpleGame.setOnAction(e -> {
                Player guiplayer1=new GUIPlayer("Your Score","#F58F84");
                List<Player> Players=new ArrayList<Player>();
                Players.add(guiplayer1);
                guiGame =new GUIGame(Players);
                guiGame.StartGame();
                Window.setScene(guiGame.getScene());
            });
            //Setting Style
            WelcomeLayout.getStyleClass().add("vbox");
            Welcome.getStyleClass().add("h1");
            Welcome.getStylesheets().add("Styles/style.css");
            CustomGame.getStylesheets().add("Styles/style.css");
            WelcomeLayout.getStylesheets().add("Styles/style.css");
            //Adding Components to layout
            WelcomeLayout.getChildren().addAll(Welcome, SimpleGame,CustomGame);
        }

        public WelcomeScene() {
            initLayout();
            scene = new Scene(WelcomeLayout);

        }
    }

    class OptionsScene {
        Scene scene;
        //SettingLabels;
        Label WidthLabel = new Label("Width: ");
        Label HeightLabel = new Label("Height: ");
        Label MinesLabel = new Label("Mines Count: ");

        TextField WidthInput = new TextField();
        TextField HeightInput = new TextField();
        TextField MinesInput = new TextField();

        GridPane optionsGrid = new GridPane();
        public OptionsScene() {
           initScene();
        }

        private void initOptionsGrid() {
            optionsGrid.add(WidthLabel,0,0);
            optionsGrid.add(WidthInput,1,0);
            optionsGrid.add(HeightLabel,0,1);
            optionsGrid.add(HeightInput,1,1);
            optionsGrid.add(MinesLabel,0,2);
            optionsGrid.add(MinesInput,1,2);
        }

        private void initScene() {
            scene = new Scene(optionsGrid);
            initOptionsGrid();

        }
    }
}