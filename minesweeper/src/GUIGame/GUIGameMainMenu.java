package GUIGame;

import GUIGame.GUIElements.Footer;
import GUIGame.GUIElements.MenuButton;
import Models.Game.Points;
import Models.Game.SaveMode;
import Models.Game.WhenHitMine;
import Models.Game.WhenScoreNegative;
import Models.Player.DumbPlayer;
import Models.Player.Player;
import SaveLoad.Directories;
import SaveLoad.SaveLoadGame;
import SaveLoad.StringID;
import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class GUIGameMainMenu {
    WelcomeScene welcomescene = new WelcomeScene();
    OptionScene optionsScene = new OptionScene();
    GUIScoreBoard scoreboard = new GUIScoreBoard(this);
    GUIGameLoad gameLoader=new GUIGameLoad(this);
    JFXDecorator decorator;
    GUIGame guiGame;
    Stage Window;

    public void QuickLoad(){
        guiGame = SaveLoadGame.loadObject(Directories.quicksave,Directories.QuickGame);
        guiGame.initscene();
        guiGame.setBegin(this);
        Window.setScene(guiGame.getScene());
        Window.centerOnScreen();
        guiGame.ContinueGame();
    }
    public void QuickSave(){
        SaveLoadGame.saveObject(Directories.quicksave, Directories.QuickGame,guiGame);
    }

    public void deleteSavedGame(){        // delete last saved if exists
        gameLoader.delete(Directories.getVal(guiGame.SavedName));
    }
    public void saveGame(){
        String name=guiGame.Name;
        if(guiGame.SavedName==null){
            if(guiGame.saveMode==SaveMode.askUser) {
                TextInputDialog dialog = new TextInputDialog("walter");
                dialog.setTitle("Text Input Dialog");
                dialog.setHeaderText("Look, a Text Input Dialog");
                dialog.setContentText("Please enter your name:");

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getStylesheets().addAll("Styles/style.css");
                dialogPane.getStyleClass().add("myDialog");
                // Traditional way to get the response value.
                guiGame.interruptThreads();
                Optional<String> result = dialog.showAndWait();
                guiGame.ContinueGame();
                if (result.isPresent()) {
                    name=result.get();
                    guiGame.Name=name;
                }
                else{
                    return;
                }
            }
        }
        saveGame(name+StringID.SaveID());
    }
    public void saveGame(String name){
        deleteSavedGame();

        guiGame.SavedName=name;
        SaveLoadGame.saveObject(Directories.save, name,guiGame);
        gameLoader.addGame(name);
    }
    void loadGame(String name){
        guiGame=SaveLoadGame.loadObject(Directories.save,name+".save");
        guiGame.initscene();
        guiGame.setBegin(this);
        Window.setScene(guiGame.getScene());
        Window.centerOnScreen();
        guiGame.ContinueGame();
    }
    void replayGame(String name){
        guiGame=SaveLoadGame.loadObject(Directories.replay,name);
        guiGame.initscene();
        guiGame.setBegin(this);
        Window.setScene(guiGame.getScene());
        Window.centerOnScreen();
        guiGame.showGame();
    }

    public GUIGameMainMenu() { Directories.CheckDir();}

    public void start(Stage primaryStage) throws IOException {
        Window = primaryStage;
        Window.setScene(welcomescene.scene);
//        decorator = new JFXDecora/tor(Window,welcomescene.WelcomeLayout);
//        decorator.getStylesheets().addAll("Styles/style.css");
//        welcomescene.scene.setRoot(decorator);
        Window.centerOnScreen();
        Window.show();

    }
    static int getVal(Node text,int begin){
        try {
            int val=begin;
            if(text instanceof JFXTextField)
                val=Integer.valueOf(((JFXTextField)text).getText());
            else if(text instanceof Slider){
                val=(int)((Slider)text).getValue();
            }
            else {
                // TODO: if some Thing Change:)
            }
            return val;
        }
        catch (Exception e){
            return begin;
        }
    }
    void initGame(){
        try {

            //Getting Values Of GridOptions
            int _width = 10,_height = 10,_mines = 10,_shields = 4,_heroShields = 0;
            switch (optionsScene.gridOption.difficulty.getSelectionModel().getSelectedItem()) {
                case "Easy":
                    _width = _height = _mines = 8;
                    break;
                case "Medium":
                    _width = _height = 16;
                    _mines = 40;
                    _shields=20;
                    break;
                case "Hard":
                    _width = _height = 24;
                    _mines = 150;
                    _shields=40;
                    break;
                case "Custom":
                    _width = getVal(optionsScene.gridOption.WidthInput,10);
                    _height =getVal(optionsScene.gridOption.HeightInput,10);
                    _mines = getVal(optionsScene.gridOption.MinesInput,10);
                    _shields = getVal(optionsScene.gridOption.ShieldsInput,10);
                    _heroShields = getVal(optionsScene.gridOption.HeroShieldsInput,10);
                    break;
            }
            int Timer=10;
            Timer=getVal(optionsScene.customRulesOption.TimerField,10);
            //Getting Values Of PlayerOptions
            ArrayList<Player> _players= new ArrayList<Player>();
            switch (optionsScene.playersOption.PlayerType.getSelectionModel().getSelectedItem()) {
                //"Single Player","VS Dump PC","Custom"
                case "Single Player":
                    Player _player=new GUIPlayer("You ","#ffe082");
                    _player.setTimeforTimer(Timer);
                    _players.add(_player);
                    break;
                case "VS Dump PC":
                    Player _player1=new GUIPlayer("You ","#ffe082");
                    _player1.setTimeforTimer(Timer);
                    _players.add(_player1);
                    Player _player2=new DumbPlayer(_width,_height);
                    _player2.setTimeforTimer(Timer);
                    _players.add(_player2);
                    break;
                case "Custom":
                    int i=0;
                    for(Node playerfield:optionsScene.playersOption.playerFields.getChildren()){
                        JFXTextField _playerName=(JFXTextField)(((HBox)playerfield).getChildren().get(0));
                        JFXTextField _NumberOfShield=(JFXTextField)(((HBox)playerfield).getChildren().get(1));
                        JFXCheckBox DisableShield = (JFXCheckBox)(((HBox) playerfield).getChildren().get(2));
                        if(_playerName.getText().length()!=0) {
                            int numberShields=0,maxnumberShields=1000000;
                            if (DisableShield.isSelected()) { maxnumberShields=0;}
                            numberShields=getVal(_NumberOfShield,1);
                            Player currentPlayer=new GUIPlayer(_playerName.getText(), optionsScene.playersOption._playersColor.get(i++),numberShields,maxnumberShields);
                            currentPlayer.setTimeforTimer(Timer);
                            _players.add(currentPlayer);
                        }
                    }
                    break;
            }
            //Getting Values Of RulesOption
            WhenHitMine pressMineBehavior=WhenHitMine.Lose;
            WhenScoreNegative scoreNegativeBehavior=WhenScoreNegative.End;
            if(!optionsScene.customRulesOption.EndGameWhenHitMine.isSelected())
                pressMineBehavior=WhenHitMine.Continue;
            if(optionsScene.customRulesOption.ContinuePlayinginNegativeScore.isSelected())
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
            int _hasNormalShield;
            int _lostNormalShield;
            Points points;
            switch (optionsScene.pointsOption.PointsType.getSelectionModel().getSelectedItem()) {
                //"Default","Custom")
                case "Default":
                    guiGame=new GUIGameCustom(_width,_height,_mines,_shields,_players,pressMineBehavior,scoreNegativeBehavior);
                    break;
                case "Custom":
                    _RevealFloodFill=getVal(optionsScene.pointsOption.RevealFloodFill,1);
                    _RevealEmpty=getVal(optionsScene.pointsOption.RevealEmpty,10);
                    _RevealMine=getVal(optionsScene.pointsOption.RevealMine,-250);
                    _MarkMine=getVal(optionsScene.pointsOption.MarkMine,5);
                    _MarkNotMine=getVal(optionsScene.pointsOption.MarkNotMine,-1);
                    _Unmarkmine=getVal(optionsScene.pointsOption.Unmarkmine,-5);
                    _UnmarkNotMine=getVal(optionsScene.pointsOption.UnmarkNotMine,1);
                    _LastNumber=getVal(optionsScene.pointsOption.LastNumber,100);
                    _hasNormalShield=getVal(optionsScene.pointsOption.hasNormlShield,50);
                    _lostNormalShield=getVal(optionsScene.pointsOption.lostNormalShield,250);
                    points=new Points(_RevealFloodFill, _RevealEmpty, _RevealMine, _MarkMine, _MarkNotMine, _Unmarkmine, _UnmarkNotMine, _LastNumber,_hasNormalShield,_lostNormalShield);
                    guiGame=new GUIGameCustom(_width,_height,_mines,_shields,_players, points,pressMineBehavior,scoreNegativeBehavior);
                    break;
            }
            SaveMode saveMode=SaveMode.auto;
            if(!optionsScene.customRulesOption.AutoSaveName.isSelected()) saveMode=SaveMode.askUser;
            guiGame.setSaveMode(saveMode);

            guiGame.setBegin(this);
            Window.setScene(guiGame.getScene());
            Window.centerOnScreen();
            guiGame.StartGame();
        }
        catch (Exception ex){
            System.out.println("not Valid input");
        }
    }

    void fadeIn(Node node) {
        node.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(500),node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
    void fadeOut(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(500),node);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        ft.onFinishedProperty().set(event -> node.setVisible(false));
    }
    public Node getWelcomelayout() {
        return welcomescene.WelcomeLayout;
    }

    class WelcomeScene {
        Scene scene;
        VBox WelcomeLayout;
        Label Welcome;
        MenuButton NewGame,LoadGame, Scoreboard,Profile;

        MenuButton QuickLoad;
        private void initLayout() {

            WelcomeLayout = new VBox(20);

            Welcome = new Label("MineSweeper");

            QuickLoad =new MenuButton("Quick Load");

            QuickLoad.setOnAction(e->{
                try {
                    QuickLoad();
                }
                catch (Exception ex){
                    System.out.println("Not Saved Quick Game");
                }
            });

            NewGame = new MenuButton("NEW GAME");
            NewGame.getStyleClass().addAll("custombutton");
            NewGame.setOnAction(e -> {
                Window.setScene(optionsScene.scene);Window.centerOnScreen();
            });
            //Setting Style
            LoadGame = new MenuButton("LOAD GAME");
            //LoadGame.setDisable(true);
            LoadGame.setOnAction(event -> {
                Window.setScene(gameLoader.getScene());
            });

            Scoreboard = new MenuButton("SCOREBOARD");
            Scoreboard.setOnAction(e -> {Window.setScene(scoreboard.scene);Window.centerOnScreen();});

            Profile = new MenuButton("PROFILE");
            Profile.setDisable(true);

            WelcomeLayout.getStyleClass().add("windowsize");
            Welcome.getStyleClass().add("h1");
            Welcome.getStylesheets().add("Styles/style.css");
            WelcomeLayout.getStylesheets().add("Styles/style.css");
            //Adding Components to layout

            WelcomeLayout.getChildren().addAll(Welcome, NewGame,QuickLoad,LoadGame,Scoreboard,Profile);

        }

        public WelcomeScene() {
            initLayout();
            scene = new Scene(WelcomeLayout);

        }
    }

    class OptionScene {
        Scene scene;
        //SettingLabels;
        BorderPane OptionLayout = new BorderPane();
        VBox EnabledLayout = new VBox(20);
        JFXTabPane OptionsTabs = new JFXTabPane();
        Label optionsLabel = new Label("Enter your Game Properties");

        GridOption gridOption=new GridOption();
        PlayersOption playersOption=new PlayersOption();
        PointsOption pointsOption=new PointsOption();
        CustomRulesOption customRulesOption = new CustomRulesOption();


        //initializing CustomRulesOptions
        //Elements

        Footer footer;
        MenuButton startGameButton = new MenuButton("START GAME");
        MenuButton SaveButton =new MenuButton("BACK");

        public OptionScene() {
            initScene();
        }

        private void initScene() {
            scene = new Scene(OptionLayout);

            scene.widthProperty().addListener((v,oldValue,newValue) -> {
                OptionsTabs.setTabMinWidth(((double)newValue - 50)/(OptionsTabs.getTabs().size()));
            });
            initLayout();
        }

        private void initLayout() {
            initOptionsLabel();
            initFooter();
            initEnabledLayout();
            initVerticalSide();
            OptionLayout.getStyleClass().addAll("windowsize","bottomPadding");
            OptionLayout.getStylesheets().add("Styles/style.css");
            OptionLayout.setCenter(gridOption.Option);
            OptionLayout.setTop(OptionsTabs);
            OptionLayout.setBottom(footer);
           }

        private void initVerticalSide() {
            Tab Grid = new Tab("Grid");
            Tab Players = new Tab("Players");
            Tab Points = new Tab("Points");
            Tab CustomRules = new Tab("Custom Rules");
            OptionsTabs.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
                if (newValue == Grid) {
                    OptionLayout.setCenter(gridOption.Option);
                } else if (newValue == Players) {
                    OptionLayout.setCenter(playersOption.Option);
                } else if (newValue == Points) {
                    OptionLayout.setCenter(pointsOption.Option);
                } else if (newValue == CustomRules) {
                    OptionLayout.setCenter(customRulesOption.Option);
                }
            });

            OptionsTabs.getStylesheets().add("Styles/style.css");
            OptionsTabs.getTabs().addAll(Grid,Players,Points,CustomRules);

//            VerticalSide.getChildren().addAll(GridButton,PlayersButton,PointsButton,CustomRulesButton);

         }

        private void initEnabledLayout() {

            EnabledLayout.getChildren().addAll(optionsLabel,gridOption.Option,playersOption.Option, pointsOption.Option, customRulesOption.Option);
        }



        private void initFooter(){
            startGameButton.getStyleClass().addAll("custombutton");
            startGameButton.setOnAction(e-> initGame());
            SaveButton.setOnAction(e->{
                Window.setScene(welcomescene.scene);
            });
            footer = new Footer(SaveButton,startGameButton);
        }

        private void initOptionsLabel() {
//            optionsLabel.getStylesheets().add("Styles/style.css");
            optionsLabel.getStyleClass().add("h2");
        }

    }
    public Scene getWelcomescene() { return welcomescene.scene; }

    public Scene getOptionsScene() { return optionsScene.scene; }



    class GridOption{
        VBox Option = new VBox(30);

        ComboBox<String> difficulty = new ComboBox<>();
        VBox CustomGrid = new VBox(5);
        //Elements
        JFXSlider WidthInput = new JFXSlider(5,30,10);
        JFXSlider HeightInput = new JFXSlider(5,30,10);
        JFXSlider MinesInput = new JFXSlider(5,450,15);
        JFXSlider ShieldsInput = new JFXSlider(0,50,8);
        JFXSlider HeroShieldsInput = new JFXSlider(0,10,3);
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
                ShieldsInput.setMax((NewValue.doubleValue() * HeightInput.getValue())* 0.25);
                HeroShieldsInput.setMax((NewValue.doubleValue() * HeightInput.getValue())* 0.25);
            });
            HeightInput.valueProperty().addListener( (v,oldValue,NewValue) -> {
                MinesInput.setMax((NewValue.doubleValue() * WidthInput.getValue()) * 0.75);
                ShieldsInput.setMax((NewValue.doubleValue() * WidthInput.getValue())* 0.25);
                HeroShieldsInput.setMax((NewValue.doubleValue() * WidthInput.getValue())* 0.25);
            });
            MinesInput.valueProperty().addListener((v,oldValue,NewValue) -> {
                ShieldsInput.setMax(WidthInput.getValue() * HeightInput.getValue() - MinesInput.getValue() - HeroShieldsInput.getValue());
                HeroShieldsInput.setMax(WidthInput.getValue() * HeightInput.getValue() - MinesInput.getValue() - ShieldsInput.getValue());
            });
            ShieldsInput.valueProperty().addListener((v,oldValue,NewValue) -> {
                MinesInput.setMax(WidthInput.getValue() * HeightInput.getValue() - ShieldsInput.getValue() - HeroShieldsInput.getValue());
                HeroShieldsInput.setMax(WidthInput.getValue() * HeightInput.getValue() - MinesInput.getValue() - ShieldsInput.getValue());
            });
            HeroShieldsInput.valueProperty().addListener((v,oldValue,NewValue) -> {
                MinesInput.setMax(WidthInput.getValue() * HeightInput.getValue() - ShieldsInput.getValue() - HeroShieldsInput.getValue());
                ShieldsInput.setMax(WidthInput.getValue() * HeightInput.getValue() - MinesInput.getValue() - HeroShieldsInput.getValue());
            });

            CustomGrid.setVisible(false);
            CustomGrid.managedProperty().bind(CustomGrid.visibleProperty());
            Label WidthLabel=new Label("Width");
            Label HeightLabel=new Label("Height");
            Label MinesLabel=new Label("Mines");
            Label ShieldsLabel=new Label("Shields");
            Label HeroShieldsLabel=new Label("Hero Shields");
            CustomGrid.getChildren().addAll(WidthLabel,WidthInput,HeightLabel,HeightInput,MinesLabel,MinesInput,ShieldsLabel,ShieldsInput);
            Option.getStyleClass().addAll("center","maxwidth250");
            JFXRippler difficultyLabel=new JFXRippler(new Label("Please Select Difficulty: "));
            difficultyLabel.getStyleClass().addAll("minwidth","h2");
            Option.getChildren().addAll(difficultyLabel,difficulty,CustomGrid);
        }
    }
    class PlayersOption{
        //initializing PlayerOptions
        VBox Option = new VBox(30);
        int ConstNumOfPlayers=4;
        ComboBox<String> PlayerType = new ComboBox<>();
        VBox CustomPlayer = new VBox(10);
        //Elements
        VBox playerFields=new VBox(20);
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
                HBox currentbox=new HBox(20);
                JFXTextField _playerField=new JFXTextField("");
                _playerField.setPromptText("player " + i);
                JFXTextField _playerShield=new JFXTextField("");
                _playerShield.setPromptText("Begin with Shields e.g 0");
                JFXCheckBox _disableShield = new JFXCheckBox("Disable Shields for this player");
                _disableShield.selectedProperty().addListener((v,oldValue,newValue)-> {
                    _playerShield.setDisable(newValue == true ? true : false);
                });
                currentbox.getChildren().addAll(_playerField,_playerShield,_disableShield);
                playerFields.getChildren().add(currentbox);
            }
            CustomPlayer.getChildren().add(playerFields);
            _playersColor.add("#6a1b9a");
            _playersColor.add("#00695c");
            _playersColor.add("#9e9d24");
            _playersColor.add("#00838f");
            _playersColor.add("#972e0e");

            Option.getStyleClass().addAll("center","padding");
            JFXRippler PlayersLabel=new JFXRippler(new Label("Select Players Type:"));
            PlayersLabel.getStyleClass().addAll("minwidth","h2");
            Option.getChildren().addAll(PlayersLabel,PlayerType,CustomPlayer);
        }
    }
    class PointsOption{
        VBox Option = new VBox(30);

        //initializing PointOptions
        ComboBox<String> PointsType = new ComboBox<>();
        GridPane CustomPoint = new GridPane();
        //Elements
        JFXTextField RevealFloodFill = new JFXTextField();
        JFXTextField RevealEmpty = new JFXTextField();
        JFXTextField RevealMine = new JFXTextField();
        JFXTextField MarkMine = new JFXTextField();
        JFXTextField MarkNotMine = new JFXTextField();
        JFXTextField Unmarkmine = new JFXTextField();
        JFXTextField UnmarkNotMine = new JFXTextField();
        JFXTextField LastNumber = new JFXTextField();
        JFXTextField hasNormlShield = new JFXTextField();
        JFXTextField lostNormalShield = new JFXTextField();

        PointsOption(){
            PointsType.getItems().addAll("Default","Custom");
            PointsType.setPromptText("Choose Points");
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
            CustomPoint.add(hasNormlShield,0,4);
            CustomPoint.add(lostNormalShield,1,4);
            CustomPoint.getStyleClass().addAll("center");

            RevealFloodFill.setPromptText("RevealFloodFill: e.g.: 1");
            RevealEmpty.setPromptText("RevealEmpty: e.g.: 10");
            RevealMine.setPromptText("RevealMine: e.g.: -250");
            MarkMine.setPromptText("MarkMine: e.g.: 5");
            MarkNotMine.setPromptText("MarkNotMine: e.g.: -1");
            Unmarkmine.setPromptText("Unmarkmine: e.g.: -5");
            UnmarkNotMine.setPromptText("UnmarkNotMine: e.g.: 1");
            LastNumber.setPromptText("LastNumber: e.g.: 0");
            hasNormlShield.setPromptText("hasNormalShield: e.g.: 50");
            lostNormalShield.setPromptText("lostNormalShield: e.g.: 250");

            Option.getStyleClass().addAll("center");
            JFXRippler PointsLabel=new JFXRippler(new Label("Choose Points Type:"));
            PointsLabel.getStyleClass().addAll("minwidth","h2");

            Option.getChildren().addAll(PointsLabel,PointsType,CustomPoint);
        }
    }
    class CustomRulesOption {
        JFXCheckBox EndGameWhenHitMine = new JFXCheckBox("End game when hit mine");
        JFXCheckBox FloodfillWhenHitMine = new JFXCheckBox("Flood fill when hit mine");
        JFXCheckBox ContinuePlayinginNegativeScore = new JFXCheckBox("Continue playing in negative score");
        JFXCheckBox AutoSaveName=new JFXCheckBox("Auto save name");
        JFXTextField TimerField = new JFXTextField();

        VBox Option = new VBox(20);

        public CustomRulesOption() {
                EndGameWhenHitMine.setSelected(true);
                FloodfillWhenHitMine.setSelected(true);
                FloodfillWhenHitMine.setDisable(true);
                AutoSaveName.setSelected(true);
            TimerField.setPromptText("Time Waiting for Player: e.g.: 10");
            Option.getChildren().addAll(EndGameWhenHitMine,FloodfillWhenHitMine,ContinuePlayinginNegativeScore,AutoSaveName,TimerField);
            Option.getStyleClass().addAll("center","maxwidth300");
            EndGameWhenHitMine.getStyleClass().addAll("h3","right");
            FloodfillWhenHitMine.getStyleClass().addAll("h3","right");
            ContinuePlayinginNegativeScore.getStyleClass().addAll("h3");
            AutoSaveName.getStyleClass().addAll("h3");
        }
    }


}
