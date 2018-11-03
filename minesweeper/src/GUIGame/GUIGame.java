package GUIGame;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import minesweeper.*;

import java.util.List;

import static java.lang.Math.max;

public class GUIGame extends NormalGame {
    public GridPane getFXgrid() {
        return FXgrid;
    }
    private static Double ConstBorder=500.0;
    private GridPane FXgrid;
    private Button ClicedButton;
    public GUIGame(int Width, int Height, int NumMines, List ListOfPlayers) {
        super();
        for(Object curPlayer:ListOfPlayers) {// add Players To the Game
            this.AddPlayer((Player) curPlayer);
        }
        initGame(Width,Height,NumMines);
        initFXGrid();
    }
    private void initFXGrid() {
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
                        if(event.isPrimaryButtonDown()) System.out.println("pr");
                        else System.out.println("sec");
                        GetMove();
                    }
                });
                FXgrid.add(button,j,i);
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
        Square[][] feild=this.grid.getField();
        for (int i=1;i<this.grid.getHeight();i++){
            for(int j=1;j<this.grid.getWidth();j++){
                int H=(i-1)*(this.grid.getWidth()-1)+(j-1);
                Button currentButton=(Button)FXgrid.getChildren().get(H);
                switch (feild[i][j].getStatus()){
                    case Closed:
                        break;
                    case OpenedEmpty:
                        currentButton.setStyle("-fx-background-color: #875F9A");
                        break;
                    case OpenedNumber:
                        currentButton.setText(""+feild[i][j].getNumberOfSurroundedMines());
                        break;
                    case OpenedMine:
                        currentButton.setStyle("-fx-background-color: #C3272B");
                        break;
                    case Marked:
                        currentButton.setStyle("-fx-background-color: #0ff");
                        break;
                }
            }
        }
    }

    @Override
    protected void EndGame() {

    }
}
