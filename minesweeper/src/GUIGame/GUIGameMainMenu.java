package GUIGame;


import Models.Game.Points;
import Models.Game.WhenHitMine;
import Models.Game.WhenScoreNegative;
import Models.Player.DumbPlayer;
import Models.Player.Player;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXSnackbar;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


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
        switch (optionsScene.gridOption.difficulty.getSelectionModel().getSelectedItem()) {
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
                _width = (int) optionsScene.gridOption.WidthInput.getValue();
                _height = (int) optionsScene.gridOption.HeightInput.getValue();
                _mines = (int) optionsScene.gridOption.MinesInput.getValue();
                break;
        }

        //Getting Values Of PlayerOptions
        ArrayList<Player> _players= new ArrayList<Player>();
        switch (optionsScene.playersOption.PlayerType.getSelectionModel().getSelectedItem()) {
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
                for(TextField _playerName:optionsScene.playersOption._playerFields){
                    if(_playerName.getText().length()!=0) {
                        _players.add(new GUIPlayer(_playerName.getText(), optionsScene.playersOption._playersColor.get(i++)));
                    }
                }
                break;
        }
        //Getting Values Of RulesOption
        WhenHitMine pressMineBehavior=WhenHitMine.Lose;
        WhenScoreNegative scoreNegativeBehavior=WhenScoreNegative.End;
        if(!optionsScene.EndGameWhenHitMine.isSelected())
            pressMineBehavior=WhenHitMine.Continue;
        if(optionsScene.ContinuePlayinginNegativeScore.isSelected())
            scoreNegativeBehavior=WhenScoreNegative.Continue;
        //Getting Values Of PointsOption
        int _RevealFloodFill;
        int _RevealEmpty;
        int _RevealMine;
        int _MarkMine;
        int _MarkNotMine;
        int _Unmarkmine;
        int _UnmarkNotMine;
        int _LastNumber;
        Points points;
        switch (optionsScene.pointsOption.PointsType.getSelectionModel().getSelectedItem()) {
            //"Default","Custom")
            case "Default":
                guiGame=new GUIGame(_width,_height,_mines,_players);
                break;
            case "Custom":
                _RevealFloodFill=Integer.valueOf(optionsScene.pointsOption.RevealFloodFill.getText());
                _RevealEmpty=Integer.valueOf(optionsScene.pointsOption.RevealEmpty.getText());
                _RevealMine=Integer.valueOf(optionsScene.pointsOption.RevealMine.getText());
                _MarkMine=Integer.valueOf(optionsScene.pointsOption.MarkMine.getText());
                _MarkNotMine=Integer.valueOf(optionsScene.pointsOption.MarkNotMine.getText());
                _Unmarkmine=Integer.valueOf(optionsScene.pointsOption.Unmarkmine.getText());
                _UnmarkNotMine=Integer.valueOf(optionsScene.pointsOption.UnmarkNotMine.getText());
                _LastNumber=Integer.valueOf(optionsScene.pointsOption.LastNumber.getText());
                points=new Points(_RevealFloodFill, _RevealEmpty, _RevealMine, _MarkMine, _MarkNotMine, _Unmarkmine, _UnmarkNotMine, _LastNumber);
                guiGame=new GUIGame(_width,_height,_mines,_players, points,pressMineBehavior,scoreNegativeBehavior);
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
        Button StartGame;

        private void initLayout() {
            WelcomeLayout = new VBox(20);
            Welcome = new Label("MineSweeper");
            CustomGame = new Button("CUSTOM GAME");
            StartGame = new Button("START GAME");
            CustomGame.getStyleClass().addAll("menubutton","h3");
            StartGame.getStyleClass().addAll("menubutton","h3");
            CustomGame.setOnAction(e -> {Window.setScene(optionsScene.scene);Window.centerOnScreen();});
            StartGame.setOnAction(e -> initGame());
            //Setting Style
            WelcomeLayout.getStyleClass().add("windowsize");
            Welcome.getStyleClass().add("h1");
            Welcome.getStylesheets().add("Styles/style.css");
            CustomGame.getStylesheets().add("Styles/style.css");
            WelcomeLayout.getStylesheets().add("Styles/style.css");
            //Adding Components to layout
            WelcomeLayout.getChildren().addAll(Welcome, StartGame,CustomGame);
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
        GridOption gridOption=new GridOption();
        PlayersOption playersOption=new PlayersOption();
        PointsOption pointsOption=new PointsOption();
        HBox CustomRulesOption = new HBox(10);

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
            initOptionsButtons();
            initCustomRulesOption();
            OptionLayout.getStyleClass().addAll("windowsize","padding");
            OptionLayout.getStylesheets().add("Styles/style.css");
            OptionLayout.getChildren().addAll(optionsLabel,gridOption.Option,playersOption.Option, pointsOption.Option, CustomRulesOption, startGameButton, SaveButton);
        }
        private void initCustomRulesOption() {
            EndGameWhenHitMine.setSelected(true);
            FloodfillWhenHitMine.setSelected(true);
            FloodfillWhenHitMine.setDisable(true);
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

        private void initOptionsLabel() {
//            optionsLabel.getStylesheets().add("Styles/style.css");
            optionsLabel.getStyleClass().add("h2");
        }

    }
    public Scene getWelcomescene() { return welcomescene.scene; }

    public Scene getOptionsScene() { return optionsScene.scene; }

    class GridOption{
        HBox Option = new HBox(30);

        ComboBox<String> difficulty = new ComboBox<>();
        VBox CustomGrid = new VBox(5);
        //Elements
        JFXSlider WidthInput = new JFXSlider(5,30,10);
        JFXSlider HeightInput = new JFXSlider(5,30,10);
        JFXSlider MinesInput = new JFXSlider(5,450,15);
        public GridOption() {
            difficulty.getItems().addAll("Easy","Medium","Hard","Custom");
            difficulty.setPromptText("Choose Difficulty");
            difficulty.getSelectionModel().select(0);
            difficulty.setOnAction(e -> {
                if (difficulty.getSelectionModel().getSelectedItem() == "Custom") { fadeIn(CustomGrid);}
                else if (difficulty.getSelectionModel().getSelectedItem() != "Custom" && CustomGrid.isVisible()) { fadeOut(CustomGrid); }
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
            Label WidthLabel=new Label("Width");
            Label HeightLabel=new Label("Heght");
            Label MinesLabel=new Label("Mines");
            CustomGrid.getChildren().addAll(WidthLabel,WidthInput,HeightLabel,HeightInput,MinesLabel,MinesInput);
            Option.getStyleClass().add("center");
            Label difficultyLabel=new Label("Difficulty: ");
            difficultyLabel.getStyleClass().addAll("minwidth","h4");
            Option.getChildren().addAll(difficultyLabel,difficulty,CustomGrid);
        }
    }
    class PlayersOption{
        //initializing PlayerOptions
        HBox Option = new HBox(30);
        int ConstNumOfPlayers=4;
        ComboBox<String> PlayerType = new ComboBox<>();
        VBox CustomPlayer = new VBox(10);
        //Elements
        ArrayList<TextField> _playerFields=new ArrayList<>();
        ArrayList<String> _playersColor=new ArrayList<>();
        PlayersOption(){
            PlayerType.getItems().addAll("Single Player","VS Dump PC","Custom");
            PlayerType.setPromptText("Choose Players");
            PlayerType.getSelectionModel().select(0);
            PlayerType. setOnAction(e -> {
                if (PlayerType.getSelectionModel().getSelectedItem() == "Custom") { fadeIn(CustomPlayer);}
                else if (PlayerType.getSelectionModel().getSelectedItem() != "Custom" && CustomPlayer.isVisible()) {fadeOut(CustomPlayer);}
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

            Option.getStyleClass().add("center");
            Label PlayersLabel=new Label("Players: ");
            PlayersLabel.getStyleClass().addAll("minwidth","h4");
            Option.getChildren().addAll(PlayersLabel,PlayerType,CustomPlayer);
        }
    }
    class PointsOption{
        HBox Option = new HBox(30);

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
        PointsOption(){
            PointsType.getItems().addAll("Default","Custom");
            PointsType.setPromptText("Choose Players");
            PointsType.getSelectionModel().select(0);
            PointsType.setOnAction(e -> {
                if (PointsType.getSelectionModel().getSelectedItem() == "Custom") { fadeIn(CustomPoint);}
                else if (PointsType.getSelectionModel().getSelectedItem() != "Custom" && CustomPoint.isVisible()) {fadeOut(CustomPoint);}
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

            Option.getStyleClass().addAll("center");
            Label PointsLabel=new Label("Points: ");
            PointsLabel.getStyleClass().addAll("minwidth","h4");
            Option.getChildren().addAll(PointsLabel,PointsType,CustomPoint);
        }
    }
}
