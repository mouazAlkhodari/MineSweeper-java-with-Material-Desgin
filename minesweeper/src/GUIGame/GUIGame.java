package GUIGame;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import minesweeper.*;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Properties;

import static java.lang.Math.max;

public class GUIGame extends NormalGame {
    public GridPane getFXgrid() {
        return FXgrid;
    }
    public VBox getScoreBoard() { return ScoreBoard; }
    private static Double ConstBorder=500.0;
    private GridPane FXgrid;
    private VBox ScoreBoard;
    private Button ClicedButton;
    public GUIGame(List _players){
        super(_players);
        initFXComponoents();
    }
    public GUIGame(int Width, int Height, int NumMines, List ListOfPlayers) {// Constructor
        super(Width,Height,NumMines,ListOfPlayers);
        initFXComponoents();
    }
    private void initFXComponoents() {
        // initialize Grid
        FXgrid=new GridPane();
        FXgrid.getStyleClass().add("grid");
        FXgrid.getStylesheets().add("Styles/style.css");
        for(int i=1;i<this.grid.getHeight();i++){
            for(int j=1;j<this.grid.getWidth();j++){
                Button button=new Button();
                button.setMaxSize(50,50);
                button.getStylesheets().add("Styles/style.css");
                double buttonborder=ConstBorder/max(this.grid.getHeight(),this.grid.getWidth());
                button.setMinSize(buttonborder,buttonborder);
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ClicedButton=button;
                        if(event.getButton()== MouseButton.PRIMARY) System.out.println("pr");
                        else System.out.println("sec");
                        GetMove();
                    }
                });
                FXgrid.add(button,j,i);
            }
            // Initialize ScoreBoard
            ScoreBoard = new VBox();
            for(Player _player:super.players){
                VBox _playerPanel=new VBox();

                Label playerNameLabel=new Label(_player.getName());
                Label playerScoreLabel=new Label(String.valueOf(_player.getCurrentScore().getScore()));
               // playerScoreLabel.textProperty().bind(new SimpleIntegerProperty(_player.getCurrentScore().getScore()).asString());
                _playerPanel.getChildren().addAll(playerNameLabel,playerScoreLabel);
                ScoreBoard.getChildren().add(_playerPanel);
            }
        }
    }
    @Override
    public void StartGame() {
        UpdateVeiw();
    }

    @Override
    public void GetMove(){
        PlayerMove move = this.currentPlayer.GetPlayerMove();
        if(currentPlayer instanceof GUIPlayer){
            move.getSquare().setX(GridPane.getRowIndex(ClicedButton));
            move.getSquare().setY(GridPane.getColumnIndex(ClicedButton));
        }
        PrintGrid();
        if(AcceptMove(move))
            ApplyPlayerMove(move);
        // need else some thing wrong input Or Some Thing Like that :3
        if(this.status==GameStatus.Finish){
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
        // Update Grid View
        Square[][] feild=this.grid.getField();
        for (int i=1;i<this.grid.getHeight();i++){
            for(int j=1;j<this.grid.getWidth();j++){
                int H=(i-1)*(this.grid.getWidth()-1)+(j-1);
                Button currentButton=(Button)FXgrid.getChildren().get(H);
                switch (feild[i][j].getStatus()){
                    case Closed:
                        break;
                    case OpenedEmpty:
                        currentButton.setStyle("-fx-background-color: #875F9A;-fx-border-width: 0;");
                        break;
                    case OpenedNumber:
                        currentButton.setText(""+feild[i][j].getNumberOfSurroundedMines());
//                        currentButton.setStyle("-fx-background-color: #F5AB35   ;");
                        break;
                    case OpenedMine:
                        currentButton.setStyle("-fx-background-color: #8F1D21");
                        break;
                    case Marked:
                        currentButton.setStyle("-fx-background-color: #0ff");
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
            if(_currentplayer.getCurrentStatus()==PlayerStatus.Playing){
                currentNameLabel.setStyle("-fx-font-style: Bold");
            }
            else{
                currentNameLabel.setStyle("-fx-font-style: normal");
            }
        }
    }

    @Override
    protected void EndGame() {

    }
}
