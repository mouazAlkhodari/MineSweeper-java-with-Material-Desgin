package GUIGame;


import Models.Game.WhenHitMine;
import Models.Player.DumbPlayer;
import Models.Player.Player;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.text.NumberFormat;
import java.util.ArrayList;

import static java.text.NumberFormat.getNumberInstance;


public class GUIGameMainMenu {
    WelcomeScene welcomescene = new WelcomeScene();
    OptionScene optionsScene = new OptionScene();



    GUIGame guiGame;
    Stage Window;

    public void start(Stage primaryStage) {
        Window = primaryStage;
        Window.setScene(welcomescene.scene);
        Window.centerOnScreen();
        Controller controller=new Controller();
        Window.show();
    }

    void initGame() {
        //Getting Values Of GridOptions
        int _width = 10,_height = 10,_mines = 10;
        switch (optionsScene.difficulty.getSelectionModel().getSelectedItem()) {
            case "Easy":
                _width = _height = _mines = 8;
                break;
            case "Medium":
                _width = _height = 16;
                _mines = 40;
                break;
            case "Hard":
                _width = _height = 24;
                _mines = 150;
                break;
            case "Custom":
                _width = (int) optionsScene.WidthInput.getValue();
                _height = (int) optionsScene.HeightInput.getValue();
                _mines = (int) optionsScene.MinesInput.getValue();
                break;
        }

        //Getting Values Of PlayerOptions
        ArrayList<Player> _players= new ArrayList<Player>();
        switch (optionsScene.PlayerType.getSelectionModel().getSelectedItem()) {
            //"Single Player","VS Dump PC","Custom"
            case "Single Player":
                _players.add(new GUIPlayer("Your Score","#ffe082"));
                break;
            case "VS Dump PC":
                _players.add(new GUIPlayer("You","#ffe082"));
                _players.add(new DumbPlayer(_width,_height));
                break;
            case "Custom":
                int i=0;
                for(TextField _playerName:optionsScene._playerFields){
                    if(_playerName.getText().length()!=0) {
                        _players.add(new GUIPlayer(_playerName.getText(), optionsScene._playersColor.get(i++)));
                    }
                }
                break;
        }
        //Getting Values Of PlayerOptions
        switch (optionsScene.PointsType.getSelectionModel().getSelectedItem()) {
            //"Default","Custom")
            case "Default":
                guiGame=new GUIGame(_width,_height,_mines,_players);
                break;
            case "Custom":
                guiGame=new GUIGame(_width,_height,_mines,_players,
                        Integer.valueOf(optionsScene.RevealFloodFill.getText()),
                        Integer.valueOf(optionsScene.RevealEmpty.getText()),
                        Integer.valueOf(optionsScene.RevealMine.getText()),
                        Integer.valueOf(optionsScene.MarkMine.getText()),
                        Integer.valueOf(optionsScene.MarkNotMine.getText()),
                        Integer.valueOf(optionsScene.Unmarkmine.getText()),
                        Integer.valueOf(optionsScene.UnmarkNotMine.getText()),
                        Integer.valueOf(optionsScene.LastNumber.getText())
                );
            break;
        }
        guiGame.setBegin(this);
        Window.setScene(guiGame.getScene());
        Window.centerOnScreen();
    }

    void fadeIn(Node node) {
        node.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(500),node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
    void fadeOut(Node node) {
        node.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(500),node);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        ft.onFinishedProperty().set(event -> node.setVisible(false));
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
            CustomGame.setOnAction(e -> {Window.setScene(optionsScene.scene);Window.centerOnScreen();});
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

    class OptionScene {
        Scene scene;
        //SettingLabels;
        VBox OptionLayout = new VBox(20);
        Label optionsLabel = new Label("Enter your Game Properties");
        HBox GridOption = new HBox(20);
        HBox PlayerOption = new HBox(20);
        HBox PointOption = new HBox(20);
        HBox CustomRulesOption = new HBox(10);

        //initializing gridOptions
        ComboBox<String> difficulty = new ComboBox<>();
        VBox CustomGrid = new VBox(10);
        //Elements
        JFXSlider WidthInput = new JFXSlider(5,30,10);
        JFXSlider HeightInput = new JFXSlider(5,30,10);
        JFXSlider MinesInput = new JFXSlider(5,75,15);

        //initializing PlayerOptions
        int ConstNumOfPlayers=4;
        ComboBox<String> PlayerType = new ComboBox<>();
        VBox CustomPlayer = new VBox(10);
        //Elements
        ArrayList<TextField> _playerFields=new ArrayList<>();
        ArrayList<String> _playersColor=new ArrayList<>();

        //initializing PointOptions
        ComboBox<String> PointsType = new ComboBox<>();
        GridPane CustomPoint = new GridPane();
        //Elements
        TextField RevealFloodFill = new TextField();
        TextField RevealEmpty = new TextField();
        TextField RevealMine = new TextField();
        TextField MarkMine = new TextField();
        TextField MarkNotMine = new TextField();
        TextField Unmarkmine = new TextField();
        TextField UnmarkNotMine = new TextField();
        TextField LastNumber = new TextField();

        //initializing CustomRulesOptions
        //Elements
        JFXCheckBox EndGameWhenHitMine = new JFXCheckBox("End Game When Hit Mine");
        JFXCheckBox FloodfillWhenHitMine = new JFXCheckBox("Flood fill When Hit Mine");
        JFXCheckBox ContinuePlayinginNegativeScore = new JFXCheckBox("Continue Playing in Negative Score");


        Button startGameButton = new Button("START GAME");
        Button SaveButton =new Button("Save");

        public OptionScene() {
            initScene();
        }

        private void initScene() {
            scene = new Scene(OptionLayout);
            initLayout();
        }

        private void initLayout() {
            initOptionsLabel();
            initGridOptions();
            initPlayerOptions();
            initPointOptions();
            initCustomRulesOption();
            initOptionsButtons();
            OptionLayout.getStyleClass().addAll("windowsize","padding");
            OptionLayout.getStylesheets().add("Styles/style.css");
            OptionLayout.getChildren().addAll(optionsLabel,GridOption,PlayerOption,PointOption, startGameButton, CustomRulesOption,SaveButton);
           
        }
        private void initOptionsLabel() {
//            optionsLabel.getStylesheets().add("Styles/style.css");
            optionsLabel.getStyleClass().add("h2");
        }

        private void initGridOptions() {
            difficulty.getItems().addAll("Easy","Medium","Hard","Custom");
            difficulty.setPromptText("Choose Difficulty");
            difficulty.getSelectionModel().select(0);
            difficulty.setOnAction(e -> {
                if (difficulty.getSelectionModel().getSelectedItem() == "Custom") { fadeIn(CustomGrid);}
                else { fadeOut(CustomGrid); }
                Window.sizeToScene();
            });

            WidthInput.valueProperty().addListener( (v,oldValue,NewValue) -> {
                MinesInput.setMax((NewValue.doubleValue() * HeightInput.getValue())* 0.75);
            });
            HeightInput.valueProperty().addListener( (v,oldValue,NewValue) -> {
                MinesInput.setMax((NewValue.doubleValue() * WidthInput.getValue()) * 0.75);
            });
            CustomGrid.setVisible(false);
            CustomGrid.managedProperty().bind(CustomGrid.visibleProperty());
            CustomGrid.getChildren().addAll(WidthInput,HeightInput,MinesInput);
            GridOption.getStyleClass().add("center");
            GridOption.getChildren().addAll(difficulty,CustomGrid);
        }

        private void initPlayerOptions() {
            PlayerType.getItems().addAll("Single Player","VS Dump PC","Custom");
            PlayerType.setPromptText("Choose Players");
            PlayerType.getSelectionModel().select(0);
            PlayerType.setOnAction(e -> {
                if (PlayerType.getSelectionModel().getSelectedItem() == "Custom") { fadeIn(CustomPlayer);}
                else {fadeOut(CustomPlayer);}
                CustomPlayer.managedProperty().bind(CustomPlayer.visibleProperty());
                Window.sizeToScene();
            });

            CustomPlayer.setVisible(false);
            CustomPlayer.managedProperty().bind(CustomPlayer.visibleProperty());
            for(int i=1;i<= ConstNumOfPlayers;i++){
                TextField _playerField=new TextField("");
                _playerField.setPromptText("player " + i);
                _playerFields.add(_playerField);
                CustomPlayer.getChildren().add(_playerField);
            }
            _playersColor.add("#6a1b9a");
            _playersColor.add("#00695c");
            _playersColor.add("#9e9d24");
            _playersColor.add("#00838f");
            _playersColor.add("#972e0e");

            PlayerOption.getStyleClass().add("center");
            PlayerOption.getChildren().addAll(PlayerType,CustomPlayer);
        }

        private void initPointOptions() {
            PointsType.getItems().addAll("Default","Custom");
            PointsType.setPromptText("Choose Players");
            PointsType.getSelectionModel().select(0);
            PointsType.setOnAction(e -> {
                if (PointsType.getSelectionModel().getSelectedItem() == "Custom") { fadeIn(CustomPoint);}
                else {fadeOut(CustomPoint);}
                CustomPoint.managedProperty().bind(CustomPoint.visibleProperty());
                Window.sizeToScene();
            });
            CustomPoint.setVisible(false);
            CustomPoint.managedProperty().bind(CustomPoint.visibleProperty());
            CustomPoint.setVgap(10);
            CustomPoint.setHgap(10);
            CustomPoint.add(RevealFloodFill,0,0);
            CustomPoint.add(RevealEmpty,1,0);
            CustomPoint.add(RevealMine,0,1);
            CustomPoint.add(MarkMine,1,1);
            CustomPoint.add(MarkNotMine,0,2);
            CustomPoint.add(Unmarkmine,1,2);
            CustomPoint.add(UnmarkNotMine,0,3);
            CustomPoint.add(LastNumber,1,3);

            RevealFloodFill.setPromptText("RevealFloodFill: e.g.: 1");
            RevealEmpty.setPromptText("RevealEmpty: e.g.: 10");
            RevealMine.setPromptText("RevealMine: e.g.: -250");
            MarkMine.setPromptText("MarkMine: e.g.: 5");
            MarkNotMine.setPromptText("MarkNotMine: e.g.: -1");
            Unmarkmine.setPromptText("Unmarkmine: e.g.: -5");
            UnmarkNotMine.setPromptText("UnmarkNotMine: e.g.: 1");
            LastNumber.setPromptText("LastNumber: e.g.: 0");

            PointOption.getStyleClass().add("center");
            PointOption.getChildren().addAll(PointsType,CustomPoint);
        }

        private void initCustomRulesOption() {
            CustomRulesOption.getChildren().addAll(EndGameWhenHitMine,FloodfillWhenHitMine,ContinuePlayinginNegativeScore);
        }

        private void initOptionsButtons(){
            startGameButton.getStyleClass().addAll("menubutton","h3");
            SaveButton.getStyleClass().addAll("menubutton","h3");

            startGameButton.setOnAction(e-> initGame());
            SaveButton.setOnAction(e->{
                Window.setScene(welcomescene.scene);
            });
        }

    }
    public Scene getWelcomescene() { return welcomescene.scene; }

    public Scene getOptionsScene() { return optionsScene.scene; }
}