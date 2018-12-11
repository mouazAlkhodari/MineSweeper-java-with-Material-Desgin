package GUIGame;

import GUIGame.GUIElements.*;
import GUIGame.GUIElements.MenuButton;
import MineSweeperGameDefineException.IllegalGameMove;
import Models.Game.*;
import Models.Grid.Square;
import Models.Grid.SquareStatus;
import Models.Move.MoveType;
import Models.Move.PlayerMove;
import Models.Player.Player;
import Models.Player.PlayerStatus;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        protected Footer footer;
        protected Top top;
        protected RingTimer left;

        protected Scene scene;
        protected BorderPane layout;

        protected MenuButton BackButton = new MenuButton("Back");
        protected MenuButton SaveButton = new MenuButton("Save");
        protected MenuButton QuickSave = new MenuButton("Quick Save");
        protected MenuButton ReplayButton = new MenuButton("Replay");
        protected GUIGameMainMenu Begin;

        // in footer
        protected DarkLabel LastMoveLabel,FlagsNumberLabel,shieldNumberLabel;

        public UIGameElements(){
            initScene();
        }
        // <__ GETTERS-SETTERS __> \\
        //Getters;

        public GridPane getFXgrid() { return FXgrid; }
        public VBox getScoreBoard() { return ScoreBoard; }
        public Scene getScene() { return scene; }
        public Footer getFooter() {return footer; }
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
                    double buttonborder = ConstBorder / max(grid.getHeight()-1,grid.getWidth()-1);
                    GridButton currentbutton=new GridButton(buttonborder, buttonborder);

                    currentbutton.setOnMouseClicked(e->{
                        if(Replay !=GameReplay.on && currentPlayer instanceof GUIPlayer) {
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
                    left= _playerPanel.getRingTimer();
                }
            }
        }
        private void initfooter() {
            // init Last Move Label
            LastMoveLabel =new DarkLabel();
            FlagsNumberLabel =new DarkLabel("Flags: "+ FlagsNumber +"");
            shieldNumberLabel=new DarkLabel("Shields: " + ShieldNumber + "");

            SaveButton.setOnAction(event -> {
                SaveGame();
            });
            BackButton.setOnAction(e->{
                interruptThreads();
                if(status!=GameStatus.Finish && Replay!=GameReplay.on){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Look, you want to go back and we are ok with that\n"+
                                        "but you don't save the game, so it will be saved now. 😲 "
                    );
                    alert.setContentText("Are you ok with this?");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().addAll("Styles/style.css");
                    dialogPane.getStyleClass().add("myDialog");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        SaveGame();
                    }
                }
                Begin.Window.setScene(Begin.getWelcomescene());
                Begin.Window.centerOnScreen();
            });
            ReplayButton.setOnAction(e->{
                currentTimer.interrupt();
                showGame();
            });
            QuickSave.setOnAction(e->{
                QuicSaveGame();
            });
            footer = new Footer(FlagsNumberLabel,shieldNumberLabel,LastMoveLabel, BackButton,QuickSave, SaveButton,ReplayButton);
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
                            UIElements.QuickSave.setDisable(true);
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
                System.out.println("reset Scene interrupt");
                e.printStackTrace();
            }
        }
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


    transient Thread GetMoveThread=new Thread();
    @Override
    public void GetMove(){
        GetMoveThread=new Thread(new Runnable() {
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


    transient Thread UpdateViewThread=new Thread();
    @Override
    protected void UpdateVeiw(List<PlayerMove> Moves){
        UpdateViewThread=new Thread(new Runnable() {
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
                            GridButton currentButton = (GridButton) UIElements.FXgrid.getChildren().get(Position);
                            Square currentSquare = currentmove.getSquare();
                            switch (currentSquare.getStatus()) {
                                case Closed:
                                 currentButton.SetClosed();
                                    break;
                                case OpenedEmpty:
                                    currentButton.SetEmpty(currentSquare.getColor());
                                    break;
                                case OpenedNumber:
                                    currentButton.SetNumber(currentSquare.getNumberOfSurroundedMines(),currentSquare.getColor());
                                    break;
                                case OpenedMine:
                                    currentButton.SetMine();
                                    break;
                                case Marked:
                                    currentButton.SetMarked();
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
                                UIElements.left = _currentpanel.getRingTimer();
                            }
                            UIElements.setLayoutLeft();
                            UIElements.setLayoutTop();
                        }

                        // Update footer Move Label
                        String LastMove="--";
                        if(currentPlayerMove!=null)
                            LastMove=String.valueOf(currentPlayerMove.getSquare().getX()) + " --- " + String.valueOf((currentPlayerMove.getSquare().getY()));
                        if(Replay==GameReplay.on)//LastMove="Replay Mode";
                        UIElements.LastMoveLabel.setText(LastMove);

                        UIElements.FlagsNumberLabel.setText("Flags: "+ FlagsNumber + "");
                        UIElements.shieldNumberLabel.setText("Shields: "+ ShieldNumber + "");
                    }
                });
            }
        });
        GUIGameThreadStart(UpdateViewThread);
    }


    transient Thread EndGameThread=new Thread();
    @Override
    protected void EndGame() {
        // show All The mines in The Game
        // and Update View For Shows
        EndGameThread=new Thread(new Runnable() {
            @Override
            public void run() {
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
                        if (Replay != GameReplay.on) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setTitle("congratulations 💃💃💃");
                            alert.setHeaderText("eyyhaa "+winner.getName()+". 😍😍✨🎉🎊🎉✨✨🎉🎉🎉\n"+
                                    "you win the Game with " +winner.getCurrentScore().getScore() +"points.\n"+
                                    "it's new achievement and its gonna added to the Score Board so you can watch the game later"
                            );
                            alert.setContentText("Are you ok with this?");
                            DialogPane dialogPane = alert.getDialogPane();
                            dialogPane.getStylesheets().addAll("Styles/style.css");
                            dialogPane.getStyleClass().add("myDialog");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK){
                                // ... user chose OK
                                AddToScoreBoard(winner);
                                deleteSavedGame();
                            }
                        }
                        for (int i = 1; i < grid.getHeight(); i++) {
                            for (int j = 1; j < grid.getWidth(); j++) {
                                int H = (i - 1) * (grid.getWidth() - 1) + (j - 1);
                                Button currentButton = (Button) UIElements.FXgrid.getChildren().get(H);
                                currentButton.setDisable(true);
                            }
                        }
                        UIElements.SaveButton.setDisable(true);
                        UIElements.ReplayButton.setDisable(false);
                        UIElements.QuickSave.setDisable(true);

                    }

                });
            }
        });
        GUIGameThreadStart(EndGameThread);
        try {
            EndGameThread.join();
        } catch (InterruptedException e) {
            System.out.println("End Game interrupt");
        }
    }


    transient Thread showGameThread=new Thread();
    protected void showGame(){
        showGameThread= new Thread(new Runnable() {
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
                ShieldNumber =grid.getShieldsCount();
                status=GameStatus.Running;

                UIElements.reset();


                Replay=GameReplay.on;
                moves=new ArrayList<>();
                UpdateVeiw(moves);
                for(PlayerMove _move:GameMoves.getMoves()){
                    double currentTime=currentPlayer.getTimeforTimer();// TODO: get it From The Move
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
                UIElements.SaveButton.setDisable(false);
                UIElements.QuickSave.setDisable(false);
                UIElements.ReplayButton.setDisable(false);

                if(status==GameStatus.Finish) {
                    EndGame();
                }
                else{
                    Replay=GameReplay.off;
                    currentTimer = new GUITimer(currentTimer.getCurrentTime());
                    GUIGameThreadStart(currentTimer);
                    if (!(currentPlayer instanceof GUIPlayer))
                        GetMove();
                }


            }
        });
        GUIGameThreadStart(showGameThread);
    }


    transient Thread ContinueGameThread=new Thread();
    public void ContinueGame(){
        ContinueGameThread=new Thread(new Runnable() {
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

    public void interruptThreads(){
        try {
            showGameThread.interrupt();
            UpdateViewThread.interrupt();
            EndGameThread.interrupt();
            GetMoveThread.interrupt();
            ContinueGameThread.interrupt();
            currentTimer.interrupt();
        }
        catch (Exception e){
        }
    }

    protected void deleteSavedGame(){
        UIElements.getBegin().deleteSavedGame();
    }
    protected void SaveGame(){
        UIElements.getBegin().saveGame();
    }
    protected void QuicSaveGame(){
        if(status!=GameStatus.Finish && Replay!=GameReplay.on)
            UIElements.getBegin().QuickSave();
    }
    void AddToScoreBoard(Player winner) {
        UIElements.Begin.scoreboard.AddBoard(this,winner);
    }

}
