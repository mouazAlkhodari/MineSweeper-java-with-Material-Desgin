package GUIGame;


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

    void initGame() {
        Player guiplayer1=new GUIPlayer("Your Score","#5f2");
        List<Player> Players=new ArrayList<Player>();
        Players.add(guiplayer1);
        guiGame =new GUIGame(Players);
        guiGame.StartGame();
        Window.setScene(guiGame.getScene());
    }
    void initGame(int _width,int _height,int _mines) {
        Player guiplayer1=new GUIPlayer("Your Score","#F58F84");
        List<Player> Players=new ArrayList<Player>();
        Players.add(guiplayer1);
        guiGame =new GUIGame(_width,_height,_mines,Players);
        guiGame.StartGame();
        Window.setScene(guiGame.getScene());
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
            CustomGame.setOnAction(e -> Window.setScene(optionsScene.scene));
            SimpleGame.setOnAction(e -> initGame());
            //Setting Style
            WelcomeLayout.getStyleClass().add("windowsize");
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
        GridPane optionsGrid = new GridPane();
        //Grid Elements
        Label WidthLabel = new Label("Width: ");
        Label HeightLabel = new Label("Height: ");
        Label MinesLabel = new Label("Mines Count: ");

        TextField WidthInput = new TextField();
        TextField HeightInput = new TextField();
        TextField MinesInput = new TextField();

        Label optionsLabel = new Label("Enter your Game Properties");
        Button startGameButtom = new Button("START GAME");

        VBox OptionLayout = new VBox(20);


        public OptionsScene() {
           initScene();
        }
        private void initScene() {
            scene = new Scene(OptionLayout);
            initLayout();
        }

        private void initOptionsGrid() {
            optionsGrid.getStyleClass().addAll("optiongrid","center");
            optionsGrid.getStylesheets().add("Styles/style.css");
            optionsGrid.add(WidthLabel,0,0);
            optionsGrid.add(WidthInput,1,0);
            optionsGrid.add(HeightLabel,0,1);
            optionsGrid.add(HeightInput,1,1);
            optionsGrid.add(MinesLabel,0,2);
            optionsGrid.add(MinesInput,1,2);
        }
        private void initLayout() {
            OptionLayout.getStylesheets().add("Styles/style.css");
            OptionLayout.getStyleClass().add("windowsize");

            optionsLabel.getStyleClass().addAll("h1");

            startGameButtom.getStyleClass().addAll("menubuttom");
            startGameButtom.setOnAction(e -> {
                initGame(Integer.valueOf(WidthInput.getText()),Integer.valueOf(HeightInput.getText()),Integer.valueOf(MinesInput.getText()));
            });
            initGridComponents();
            initOptionsGrid();
            OptionLayout.getChildren().addAll(optionsLabel,optionsGrid,startGameButtom);

        }
        private void initGridComponents() {
            WidthLabel.getStyleClass().add("h3");
            HeightLabel.getStyleClass().add("h3");
            MinesLabel.getStyleClass().add("h3");
            startGameButtom.getStyleClass().addAll("menubutton","h3");
        }




    }
}