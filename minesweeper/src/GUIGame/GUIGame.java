package GUIGame;

import Models.Game.GameStatus;
import Models.Game.NormalGame;
import Models.Grid.Square;
import Models.Move.MoveType;
import Models.Player.DumbPlayer;
import Models.Player.Player;
import Models.Move.PlayerMove;
import Models.Player.PlayerStatus;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

import static java.lang.Math.max;

public class GUIGame extends NormalGame {
    private MoveType TypeOfMove;
    // <__ DATA MEMBERS __> \\
    private static Double ConstBorder=500.0;
    private GridPane FXgrid;
    private VBox ScoreBoard;
    private Button ClickedButton;
    private Label LastMoveLabel;

    // <__ CONSTRUCTOR __> \\
    public GUIGame(List _players){
        super(_players);
        initFXComponoents();
    }
    public GUIGame(int Width, int Height, int NumMines, List ListOfPlayers) {// Constructor
        super(Width,Height,NumMines,ListOfPlayers);
        initFXComponoents();
    }

    // <__ GETTERS-SETTERS __> \\
    //Getters;

    public GridPane getFXgrid() {
        return FXgrid;
    }
    public VBox getScoreBoard() { return ScoreBoard; }

    private void initFXComponoents() {
        // initialize Grid
        FXgrid=new GridPane();
        FXgrid.getStyleClass().add("grid");
        FXgrid.getStylesheets().add("Styles/style.css");
        for(int i=1;i<this.grid.getHeight();i++) {
            for (int j = 1; j < this.grid.getWidth(); j++) {
                Button button = new Button();
                button.getStylesheets().add("Styles/style.css");
                //SettingSize
                button.setMaxSize(50, 50);
                double buttonborder = ConstBorder / max(this.grid.getHeight(), this.grid.getWidth());
                button.setMinSize(buttonborder, buttonborder);
                //Set Action
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ClickedButton = button;
                        if (event.getButton() == MouseButton.PRIMARY) {System.out.println("pr");TypeOfMove=MoveType.Reveal;}
                        else { System.out.println("sec");TypeOfMove=MoveType.Mark;}
                        GetMove();
                    }
                });
                FXgrid.add(button, j, i);
            }
        }
        // Initialize ScoreBoard
        ScoreBoard = new VBox();
        ScoreBoard.setMinWidth(200);
        for(Player _player:super.players){
            VBox _playerPanel=new VBox();
            Label playerNameLabel=new Label(_player.getName());
            Label playerScoreLabel=new Label(String.valueOf(_player.getCurrentScore().getScore()));
           // playerScoreLabel.textProperty().bind(new SimpleIntegerProperty(_player.getCurrentScore().getScore()).asString());
            _playerPanel.getChildren().addAll(playerNameLabel,playerScoreLabel);
            ScoreBoard.getChildren().add(_playerPanel);
        }
        LastMoveLabel =new Label();
        LastMoveLabel.setStyle("-fx-padding: 10");
        ScoreBoard.getChildren().add(LastMoveLabel);
    }
    @Override
    public void StartGame() {
        UpdateVeiw();
    }

    @Override
    public void GetMove(){
        PlayerMove move = this.currentPlayer.GetPlayerMove();
        if(currentPlayer instanceof GUIPlayer){
            move=new PlayerMove(move.getPlayer(),new Square(GridPane.getRowIndex(ClickedButton),GridPane.getColumnIndex(ClickedButton)),TypeOfMove);
            move.getSquare().setX(GridPane.getRowIndex(ClickedButton));
            move.getSquare().setY(GridPane.getColumnIndex(ClickedButton));
        }
        //PrintGrid();
        if(AcceptMove(move))
            ApplyPlayerMove(move);
        // need else some thing wrong input Or Some Thing Like that :3
        if(this.status== GameStatus.Finish){
            EndGame();
        }
        else{
            UpdateVeiw();
            if (!(currentPlayer instanceof GUIPlayer))
                GetMove();
        }
    }
    @Override
    protected void UpdateVeiw(){
        // Update Last Move Label
        String LastMove="pick any Square";
        if(ClickedButton!=null)
            LastMove=String.valueOf(GridPane.getRowIndex(ClickedButton)) + " --- " + String.valueOf(GridPane.getColumnIndex(ClickedButton));
//        System.out.println(LastMove);
        LastMoveLabel.setText(LastMove);

        // Update Grid View
        Square[][] feild=this.grid.getField();
        for (int i=1;i<this.grid.getHeight();i++){
            for(int j=1;j<this.grid.getWidth();j++){
                int H=(i-1)*(this.grid.getWidth()-1)+(j-1);
                Button currentButton=(Button)FXgrid.getChildren().get(H);
                switch (feild[i][j].getStatus()){
                    case Closed:
                        currentButton.setStyle("-fx-background-color: #22a6b3");
                        break;
                    case OpenedEmpty:
                        currentButton.setStyle("-fx-background-color: #875F9A;-fx-border-width: 0;");
                        break;
                    case OpenedNumber:
                        currentButton.setText(""+feild[i][j].getNumberOfSurroundedMines());
                        currentButton.setStyle("-fx-background-color: #F5AB35   ;");
                        break;
                    case OpenedMine:
                        currentButton.setStyle("-fx-background-color: #8F1D21");
                        break;
                    case Marked:
                        currentButton.setStyle("-fx-background-color: #00f");
                        break;
                }
            }
        }
        // Update ScoreBoard View
        for(int i=0;i<players.size();i++){
            Player _currentplayer=players.get(i);
            VBox currentpanel=(VBox)ScoreBoard.getChildren().get(i);
            Label currentNameLabel=(Label)currentpanel.getChildren().get(0);
            Label currentScoreLabel=(Label)currentpanel.getChildren().get(1);
            currentScoreLabel.setText(String.valueOf((_currentplayer.getCurrentScore().getScore())));
            if(_currentplayer.getCurrentStatus()== PlayerStatus.Playing){
                currentNameLabel.setStyle("-fx-font-weight: Bold");
            }
            else{
                currentNameLabel.setStyle("-fx-font-weight: normal");
            }
        }
    }

    @Override
    protected void EndGame() {
        for(Square mineSqauer:this.grid.getMines()){
            mineSqauer.ChangeStatus(currentPlayer,MoveType.Reveal);
        }
        UpdateVeiw();
        Player winner=players.get(0);
        for(int i=0;i<players.size();i++) {
            if(players.get(i).getCurrentScore().getScore()>winner.getCurrentScore().getScore()){
                winner=players.get(i);
            }
        }
        winner.setCurrentStatus(PlayerStatus.win);
       LastMoveLabel.setText(winner.getName() + " Win the Game yyyyhaaaa!!!!");
       for(int i=1;i<this.grid.getWidth();i++){
           for(int j=1;j<this.grid.getHeight();j++){
               int H=(i-1)*(this.grid.getWidth()-1)+(j-1);
               Button currentButton=(Button)FXgrid.getChildren().get(H);
               currentButton.setDisable(true);
           }
       }
    }
}
