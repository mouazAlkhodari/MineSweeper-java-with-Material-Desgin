package GUIGame;

import MineSweeperGameDefineException.IllegalGameMove;
import Models.Game.*;
import Models.Grid.Grid;
import Models.Grid.Square;
import Models.Grid.SquareStatus;
import Models.Move.MoveType;
import Models.Move.PlayerMove;
import Models.Player.Player;
import Models.Player.PlayerStatus;
import Models.ScoreBoard.PlayerBoard;
import SaveLoadPackage.Directories;
import SaveLoadPackage.SaveLoadGame;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.max;

public class GUIGame extends NormalGame implements Serializable {
    protected PlayerMove currentPlayerMove;
    protected class UIGameElements{
        protected List<PlayerPanel> PlayersPanel = new ArrayList<>() ;
        protected PlayerPanel currentPanel;
        // <__ DATA MEMBERS __> \\

        protected Double ConstBorder=400.0;

        protected GridPane FXgrid;
        protected VBox ScoreBoard;
        protected HBox footer;
        protected HBox top;
        protected VBox left;

        protected Scene scene;
        protected BorderPane layout;

        protected Button BackButton;
        protected Button SaveButton;
        protected Button ReplayButton;

        protected GUIGameMainMenu Begin;

        // in footer
        protected Label LastMoveLabel,FlagsNumberLabel,shieldNumberLabel;

        public UIGameElements(){
            initScene();
        }
        // <__ GETTERS-SETTERS __> \\
        //Getters;

        public GridPane getFXgrid() { return FXgrid; }
        public VBox getScoreBoard() { return ScoreBoard; }
        public Scene getScene() { return scene; }
        public HBox getFooter() {return footer; }
        public UIGameElements getUIELements() { return UIElements;}
        public void setTop(PlayerPanel _panel) {
            this.top =_panel.getTopPanel();
            currentPanel=_panel;
        }
        public void setLayoutLeft(){
            layout.setLeft(left);
        }
        public void setLayoutTop(){
            layout.setTop(top);
        }
        public GUIGameMainMenu getBegin() { return Begin; }

        public void setBegin(GUIGameMainMenu begin) { Begin = begin; }

        protected void initScene() {
            initFXComponoents();
            scene = new Scene(layout);
            scene.getStylesheets().add("Styles/style.css");
        }



        private void initFXComponoents() {
            initGrid();
            initScoreBoard();
            initfooter();

            layout=new BorderPane();
            layout.setCenter(FXgrid);
            layout.setRight(ScoreBoard);
            layout.setBottom(footer);
            layout.setTop(top);
            layout.setLeft(left);
            layout.getLeft().getStyleClass().add("center");
            layout.getStyleClass().add("padding");
        }

        private void initGrid() {
            ConstBorder= Double.valueOf(Math.min(max(grid.getWidth(),grid.getHeight()) * 50,600));
            // initialize Grid
            FXgrid=new GridPane();
            FXgrid.getStyleClass().add("grid");
            FXgrid.getStylesheets().add("Styles/style.css");
            for(int i=1;i<grid.getHeight();i++){
                for(int j=1;j<grid.getWidth();j++){
                    Button currentbutton=new Button();

                    currentbutton.getStylesheets().add("Styles/style.css");
                    //SettingSize
                    double buttonborder = ConstBorder / max(grid.getHeight()-1,grid.getWidth()-1);
                    //System.out.println(buttonborder + " " + grid.getHeight() + " " +grid.getWidth());
                    currentbutton.setMaxSize(buttonborder, buttonborder);
                    currentbutton.setMinSize(buttonborder, buttonborder);
                    //Set Action
                    currentbutton.setOnMouseClicked(e->{
                        if(currentPlayer instanceof GUIPlayer) {
                            currentPlayerMove = new PlayerMove(currentPlayer, new Square(GridPane.getRowIndex(currentbutton), GridPane.getColumnIndex(currentbutton)));
                            if (e.getButton() == MouseButton.PRIMARY) currentPlayerMove.setType(MoveType.Reveal);
                            else currentPlayerMove.setType(MoveType.Mark);
                            GetMove();
                        }
                        // TODO: Some Exception for Thar :p
                    });
                    FXgrid.add(currentbutton, j, i);
                }
            }
        }

        protected void initScoreBoard() {
            // Initialize ScoreBoard
            ScoreBoard = new VBox(20);
            ScoreBoard.setMinWidth(200);
            PlayersPanel=new ArrayList<PlayerPanel>();
            ScoreBoard.setStyle("-fx-alignment: CENTER;");
            for(Player _player:players){
                PlayerPanel _playerPanel=new PlayerPanel(_player);
                PlayersPanel.add(_playerPanel);
                ScoreBoard.getChildren().add(_playerPanel.getRightPanel());
                if(_player==currentPlayer){
                    setTop(_playerPanel);
                    left= _playerPanel.getLeftPanel();
                }
            }
        }
        private void initfooter() {
            // init Last Move Label
            footer=new HBox();
            footer.setPadding(new Insets(20));
            footer.setSpacing(80);
            footer.setAlignment(Pos.CENTER);

            LastMoveLabel =new Label();
            FlagsNumberLabel =new Label("Flags: "+ FlagsNumber +"");
            shieldNumberLabel=new Label("Shields: " +ShildNumber + "");
            FlagsNumberLabel.getStyleClass().addAll("buttonlabel","h3","padding-sm");
            LastMoveLabel.getStyleClass().addAll("buttonlabel","h3","padding-sm");
            shieldNumberLabel.getStyleClass().addAll("buttonlabel","h3","padding-sm");

            BackButton =new Button("Back");
            BackButton.getStyleClass().addAll("menubutton","h3");
            BackButton.setPrefSize(60,40);

            SaveButton =new Button("Save");
            SaveButton =new Button("Save");
            SaveButton.getStyleClass().addAll("menubutton","h3");
            SaveButton.setPrefSize(60,40);

            SaveButton.setOnAction(event -> {
                SaveGame();
            });

            BackButton.setOnAction(e->{
                Begin.Window.setScene(Begin.getWelcomescene());
                Begin.Window.centerOnScreen();
            });

            ReplayButton=new Button("Replay");
            ReplayButton.getStyleClass().addAll("menubutton","h3");
            ReplayButton.setPrefSize(60,40);

            ReplayButton.setOnAction(e->{
                currentTimer.interrupt();
                showGame();
            });
            footer.getChildren().addAll(FlagsNumberLabel,shieldNumberLabel,LastMoveLabel, BackButton, SaveButton,ReplayButton);
        }

        public void reset(){
            Thread resetThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            initFXComponoents();
                            scene.setRoot(layout);
                            UIElements.BackButton.setDisable(true);
                            UIElements.SaveButton.setDisable(true);
                            UIElements.ReplayButton.setDisable(true);
                        }
                    });
                }
            });
            GUIGameThreadStart(resetThread);
            try {
                resetThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void SaveGame(){
        UIElements.getBegin().saveGame();

    }
    transient protected UIGameElements UIElements;

    class GUITimer extends Timer implements Serializable{
        public GUITimer(){
            super();
        }
        public GUITimer(double t){
            super(t);
        }
        @Override
        public void Show(double Time) {
            UIElements.currentPanel.setTime(Time);
        }

        @Override
        public void EndTimer() {
            currentPlayer.stop();
            moves=new ArrayList<>();
            currentRules.DecideNextPlayer(moves);
            updateTimer();
        }
    }
    public void updateTimer() {
        UpdateVeiw(moves);
        if (status != GameStatus.Finish) {
            currentTimer = new GUITimer(currentPlayer.getTimeforTimer());
            GUIGameThreadStart(currentTimer);
            if (!(currentPlayer instanceof GUIPlayer))
                GetMove();
        }
        else{
            EndGame();
        }
    }

    // <__ CONSTRUCTOR __> \\
    public GUIGame(List _players){
        super(_players);
        UIElements=new UIGameElements();
    }
    public GUIGame(int Width, int Height, int NumMines, List ListOfPlayers) {// Constructor
        super(Width,Height,NumMines,ListOfPlayers);
        UIElements=new UIGameElements();
    }
    public GUIGame(int Width, int Height, int NumMines, List _players, Points points, WhenHitMine pressMineBehavior,WhenScoreNegative scoreNegativeBehavior){
        super(Width,Height,NumMines,_players,points,pressMineBehavior,scoreNegativeBehavior);
        UIElements=new UIGameElements();
    }


    public void setBegin(GUIGameMainMenu MainMenueGame){
        UIElements.setBegin(MainMenueGame);
    }
    public Scene getScene(){
        return UIElements.getScene();
    }
    public List<Player> getPlayers(){return players;}
    void GUIGameThreadStart(Thread thread){
        thread.setDaemon(true);
        thread.start();
    }

    void CalcGameTime() {
        Thread GameTimerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (status != GameStatus.Finish) {
                    GameTime++;
                    //System.out.print(GameTime);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                       //TODO: Some Handling way

                        return;
                    }
                }
            }
        });
        GUIGameThreadStart(GameTimerThread);
    }
    public void initscene(){
        UIElements=new UIGameElements();
    }


    @Override
    public void StartGame() {
        Thread StartGameThread=new Thread(new Runnable() {
            @Override
            public void run() {
                UpdateVeiw(moves);
                updateTimer();
            }
        });
        GUIGameThreadStart(StartGameThread);
    }
    @Override
    public void GetMove(){
        Thread GetMoveThread=new Thread(new Runnable() {
            @Override
            public void run() {
                PlayerMove move = currentPlayer.GetPlayerMove();
                if(!(currentPlayer instanceof GUIPlayer))
                    currentPlayerMove=move;
                try {
                    AcceptMove(currentPlayerMove);
                } catch (IllegalGameMove illegalGameMove) {
                    illegalGameMove.handle();
                    if(status== GameStatus.Finish){
                        UpdateVeiw(moves);
                        EndGame();
                    }
                    else if (!(currentPlayer instanceof GUIPlayer)){
                        GetMove();
                    }
                    return;
                }

                // need else some thing wrong input Or Some Thing Like that :3
                if(status== GameStatus.Finish){
                    UpdateVeiw(moves);
                    EndGame();
                }
                else{
                    updateTimer();
                }
            }
        });
        GUIGameThreadStart(GetMoveThread);
    }
    @Override
    protected void UpdateVeiw(List<PlayerMove> Moves){
        Thread UpdateViewThread=new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //     Update Grid View
                        for(PlayerMove currentmove:Moves) {
                            int i = currentmove.getSquare().getX();
                            int j = currentmove.getSquare().getY();
                            int Position = (i - 1) * (grid.getWidth() - 1) + (j - 1);
                            Button currentButton = (Button) UIElements.FXgrid.getChildren().get(Position);
                            Square currentSquare = currentmove.getSquare();
                            if(currentSquare.hasNormalSield()){
                                ShildNumber--;
                            }
                            switch (currentSquare.getStatus()) {
                                case Closed:
                                    currentButton.getStyleClass().removeAll("pressed", "openedMine", "marked");
                                    currentButton.getStyleClass().add("notpressed");
                                    break;
                                case OpenedEmpty:
                                    currentButton.setStyle("-fx-background-color: " + currentSquare.getColor() + "");
                                    currentButton.getStyleClass().add("pressed");
                                    break;
                                case OpenedNumber:
                                    currentButton.getStyleClass().add("f" + (String.valueOf(currentSquare.getNumberOfSurroundedMines())) + "");
                                    currentButton.setText("" + currentSquare.getNumberOfSurroundedMines());
                                    currentButton.setStyle("-fx-background-color: " + currentSquare.getColor() + "");
                                    currentButton.getStyleClass().add("pressed");

                                    break;
                                case OpenedMine:
                                    currentButton.getStyleClass().addAll("pressed", "openedMine");
                                    break;
                                case Marked:
                                    currentButton.getStyleClass().removeAll("notpressed", "closed");
                                    currentButton.getStyleClass().addAll("pressed", "marked");
                                    break;
                            }
                        }

                        //Update ScoreBoard
                        for(int i=0;i<players.size();i++){
                            Player _player=players.get(i);
                            PlayerPanel _currentpanel=UIElements.PlayersPanel.get(i);
                            _currentpanel.Update();
                            if(_player==currentPlayer){
                                UIElements.setTop(_currentpanel);
                                UIElements.left = _currentpanel.getLeftPanel();
                            }
                            UIElements.setLayoutLeft();
                            UIElements.setLayoutTop();
                        }

                        // Update footer Move Label
                        String LastMove="--";
                        if(currentPlayerMove!=null)
                            LastMove=String.valueOf(currentPlayerMove.getSquare().getX()) + " --- " + String.valueOf((currentPlayerMove.getSquare().getY()));

                        UIElements.LastMoveLabel.setText(LastMove);

                        UIElements.FlagsNumberLabel.setText("Flags: "+ FlagsNumber + "");
                        UIElements.shieldNumberLabel.setText("Shigelds: "+ShildNumber + "");
                    }
                });
            }
        });
        GUIGameThreadStart(UpdateViewThread);
    }

    @Override
    protected void EndGame() {
        // show All The mines in The Game
        // and Update View For Shows
        Thread EndGameThread=new Thread(new Runnable() {
            @Override
            public void run() {
                Player winner = players.get(0);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        currentTimer.interrupt();
                        List<PlayerMove> curr = new ArrayList<>();
                        for (Square mineSqauer : grid.getMines()) {
                            mineSqauer.ChangeStatus(currentPlayer, MoveType.Reveal);
                            curr.add(new PlayerMove(currentPlayer, mineSqauer));
                        }
                        // Get The Winner
                        Player winner = players.get(0);
                        for (int i = 0; i < players.size(); i++) {
                            Player _player=players.get(i);
                            PlayerPanel _Panel=UIElements.PlayersPanel.get(i);
                            if(_player.getNumberOfShield()>0){
                                setCurrentPlayer(_player);
                                moves=new ArrayList<>();
                                currentRules.DecideNextPlayer(moves);
                                _Panel.Update();
                            }
                        }
                        for (int i = 0; i < players.size(); i++) {
                            Player _player=players.get(i);
                            if (_player.getCurrentScore().getScore() > winner.getCurrentScore().getScore()) {
                                winner = players.get(i);
                            }
                         }
                        UpdateVeiw(curr);
                        // Update footer Move Label

                        Label LastMoveLabel = (Label) UIElements.footer.getChildren().get(0);
                        String WinnerStr = winner.getName() + " Win The Game";
                        if (players.size() == 1) {
                            WinnerStr = winner.getCurrentStatus() == PlayerStatus.Lose ? "You Lose" : "You Win";
                        }
                        winner.setCurrentStatus(PlayerStatus.win);
                        LastMoveLabel.setText(WinnerStr);

                        for (int i = 1; i < grid.getHeight(); i++) {
                            for (int j = 1; j < grid.getWidth(); j++) {
                                int H = (i - 1) * (grid.getWidth() - 1) + (j - 1);
                                Button currentButton = (Button) UIElements.FXgrid.getChildren().get(H);
                                currentButton.setDisable(true);
                            }
                        }
                    }
                });
                if (Replay != GameReplay.on) {
                    AddToScoreBoard(winner);
                }
            }
        });

        GUIGameThreadStart(EndGameThread);
    }

    protected void showGame(){
        Thread showGameThread= new Thread(new Runnable() {
            @Override
            public void run() {
                // reset component
                currentTimer.interrupt();
                for(Player _player:players){
                    _player.reset();
                }
                currentPlayer=players.get(0);

                grid.reset();
                FlagsNumber=grid.getMinesCount();
                ShildNumber=grid.getShieldsCount();
                status=GameStatus.Running;
                UIElements.reset();


                Replay=GameReplay.on;
                System.out.println(GameMoves.getMoves().size());
                for(PlayerMove _move:GameMoves.getMoves()){
                    System.out.println("#"+GameMoves.getMoves().size());
                    double currentTime=currentPlayer.getTimeforTimer();// TODO: get it From The Move
                    System.out.println(_move.getEndTimeMove());
                    System.out.println(currentPlayer.getName());
                    while (currentTime > _move.getEndTimeMove()) {
                        currentTime -= 0.1;
                        UIElements.currentPanel.setTime(currentTime);
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO: Some Handling way
                            //System.err.println("Interrupted Timer");
                            return;
                        }
                    }
                    if(currentTime>0) {
                        ApplyPlayerMove(_move);
                    }
                    else{
                        moves=new ArrayList<>();
                        currentRules.DecideNextPlayer(moves);
                    }
                    UpdateVeiw(moves);
                }
                UIElements.BackButton.setDisable(false);
                UIElements.SaveButton.setDisable(false);
                UIElements.ReplayButton.setDisable(false);

                if(status==GameStatus.Finish) {
                    EndGame();
                }
                else{
                    currentTimer = new GUITimer(currentTimer.getCurrentTime());
                    GUIGameThreadStart(currentTimer);
                    if (!(currentPlayer instanceof GUIPlayer))
                        GetMove();
                }

//                Replay=GameReplay.off;

            }
        });
        GUIGameThreadStart(showGameThread);
    }

    public void ContinueGame(){
        Thread ContinueGameThread=new Thread(new Runnable() {
            @Override
            public void run() {
                moves.clear();
                for(int i=1;i<grid.getWidth();i++){
                    for(int j=1;j<grid.getHeight();j++){
                        Square currentSquare=grid.getField()[i][j];
                        if(currentSquare.getStatus()!= SquareStatus.Closed){
                            PlayerMove _move=new PlayerMove(currentPlayer,currentSquare);
                            moves.add(_move);
                        }
                    }
                }
                UpdateVeiw(moves);
                if (status != GameStatus.Finish) {
                    currentTimer = new GUITimer(currentTimer.getCurrentTime());
                    GUIGameThreadStart(currentTimer);
                    if (!(currentPlayer instanceof GUIPlayer))
                        GetMove();
                }
                else{
                    EndGame();
                }
//                updateTimer();
            }
        });
        GUIGameThreadStart(ContinueGameThread);
    }

    void AddToScoreBoard(Player winner) {
        UIElements.Begin.scoreboard.AddBoard(this,winner);
    }

}
