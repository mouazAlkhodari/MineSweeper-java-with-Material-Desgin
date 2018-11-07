package GUIGame;


import ConsoleGame.ConsolePlayer;
import Models.Player.DumbPlayer;
import Models.Player.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class GUIGameWithOptions{
    WelcomeScene welcomescene = new WelcomeScene();
    OptionScene optionsScene = new OptionScene();



    GUIGame guiGame;
    Stage Window;

    public void start(Stage primaryStage) {
        Window = primaryStage;
        Window.setScene(welcomescene.scene);
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
                _width = Integer.valueOf(optionsScene.WidthInput.getText());
                _height = Integer.valueOf(optionsScene.HeightInput.getText());
                _mines = Integer.valueOf(optionsScene.MinesInput.getText());
                break;
        }

        //Getting Values Of PlayerOptions
        ArrayList<Player> _players= new ArrayList<Player>();
        switch (optionsScene.PlayerType.getSelectionModel().getSelectedItem()) {
            //"Single Player","VS Dump PC","Custom"
            case "Single Player":
                _players.add(new GUIPlayer("Your Score"));
                break;
            case "VS Dump PC":
                _players.add(new GUIPlayer("You","#00695c"));
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

    class OptionScene {
        Scene scene;
        //SettingLabels;
        VBox OptionLayout = new VBox(20);
        Label optionsLabel = new Label("Enter your Game Properties");
        HBox GridOption = new HBox();
        HBox PlayerOption = new HBox();
        HBox PointOption = new HBox();
        //initializing gridOptions
        ComboBox<String> difficulty = new ComboBox<>();
        VBox CustomGrid = new VBox();
        //Elements
        TextField WidthInput = new TextField();
        TextField HeightInput = new TextField();
        TextField MinesInput = new TextField();

        //initializing PlayerOptions
        int ConstNumOfPlayers=4;
        ComboBox<String> PlayerType = new ComboBox<>();
        VBox CustomPlayer = new VBox();
        //Elements
        ArrayList<TextField> _playerFields=new ArrayList<>();
        ArrayList<String> _playersColor=new ArrayList<>();

        //initializing PointOptions
        ComboBox<String> PointsType = new ComboBox<>();
        VBox CustomPoint = new VBox();
        //Elements
        TextField RevealFloodFill = new TextField();
        TextField RevealEmpty = new TextField();
        TextField RevealMine = new TextField();
        TextField MarkMine = new TextField();
        TextField MarkNotMine = new TextField();
        TextField Unmarkmine = new TextField();
        TextField UnmarkNotMine = new TextField();
        TextField LastNumber = new TextField();

        Button startGameButtom = new Button("START GAME");
        Button SaveBtn=new Button("Save");

        public OptionScene() {
            initScene();
        }

        private void initScene() {
            scene = new Scene(OptionLayout);
            initLayout();
            CustomGrid.setDisable(true);
        }

        private void initLayout() {
            initGridOptions();
            initPlayerOptions();
            initPointOptions();
            OptionLayout.getChildren().addAll(optionsLabel,GridOption,PlayerOption,PointOption,startGameButtom,SaveBtn);
            startGameButtom.setOnAction(e-> initGame());
            SaveBtn.setOnAction(e->{
                Window.setScene(welcomescene.scene);
            });
        }

        private void initGridOptions() {
            difficulty.getItems().addAll("Easy","Medium","Hard","Custom");
            difficulty.setPromptText("Choose Difficulty");
            difficulty.getSelectionModel().select(0);
            difficulty.setOnAction(e -> {
                if (difficulty.getSelectionModel().getSelectedItem() == "Custom") { CustomGrid.setDisable(false);}
                else {CustomGrid.setDisable(true);}
            });

            CustomGrid.setDisable(true);
            CustomGrid.getChildren().addAll(WidthInput,HeightInput,MinesInput);

            WidthInput.setPromptText("Enter Width");
            HeightInput.setPromptText("Enter Height");
            MinesInput.setPromptText("Enter Number Of Mines");

            GridOption.getChildren().addAll(difficulty,CustomGrid);
        }

        private void initPlayerOptions() {
            PlayerType.getItems().addAll("Single Player","VS Dump PC","Custom");
            PlayerType.setPromptText("Choose Players");
            PlayerType.getSelectionModel().select(0);
            PlayerType.setOnAction(e -> {
                if (PlayerType.getSelectionModel().getSelectedItem() == "Custom") { CustomPlayer.setDisable(false);}
                else {CustomPlayer.setDisable(true);}
            });

            CustomPlayer.setDisable(true);
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

            PlayerOption.getChildren().addAll(PlayerType,CustomPlayer);
        }

        private void initPointOptions() {
            PointsType.getItems().addAll("Default","Custom");
            PointsType.setPromptText("Choose Players");
            PointsType.getSelectionModel().select(0);
            PointsType.setOnAction(e -> {
                if (PointsType.getSelectionModel().getSelectedItem() == "Custom") { CustomPoint.setDisable(false);}
                else {CustomPoint.setDisable(true);}
            });


            CustomPoint.setDisable(true);
            CustomPoint.getChildren().addAll(RevealFloodFill,RevealEmpty,RevealMine,MarkMine,MarkNotMine,Unmarkmine,UnmarkNotMine,LastNumber);
            RevealFloodFill.setPromptText("RevealFloodFill: e.g.: 1");
            RevealEmpty.setPromptText("RevealEmpty: e.g.: 10");
            RevealMine.setPromptText("RevealMine: e.g.: -250");
            MarkMine.setPromptText("MarkMine: e.g.: 5");
            MarkNotMine.setPromptText("MarkNotMine: e.g.: -1");
            Unmarkmine.setPromptText("Unmarkmine: e.g.: -5");
            UnmarkNotMine.setPromptText("UnmarkNotMine: e.g.: 1");
            LastNumber.setPromptText("LastNumber: e.g.: 0");

            PointOption.getChildren().addAll(PointsType,CustomPoint);
        }

    }
    public Scene getWelcomescene() { return welcomescene.scene; }

    public Scene getOptionsScene() { return optionsScene.scene; }
}