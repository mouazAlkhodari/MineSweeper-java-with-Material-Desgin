package GUIGame;

import Models.Game.GameStatus;
import Models.Game.NormalGame;
import Models.Grid.Square;
import Models.Move.MoveType;
import Models.Player.Player;
import Models.Move.PlayerMove;
import Models.Player.PlayerStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.util.List;

import static java.lang.Math.max;

public class GUIGame extends NormalGame {
    // <__ DATA MEMBERS __> \\
    private MoveType TypeOfMove;
    private Button ClickedButton;
    private static Double ConstBorder=400.0;

    private GridPane FXgrid;
    private VBox ScoreBoard;
    private HBox footer;

    private Scene scene;
    private BorderPane layout;

    // <__ CONSTRUCTOR __> \\
    public GUIGame(List _players){
        super(_players);
        initScene();
    }
    public GUIGame(int Width, int Height, int NumMines, List ListOfPlayers) {// Constructor
        super(Width,Height,NumMines,ListOfPlayers);
        initScene();
    }

    // <__ GETTERS-SETTERS __> \\
    //Getters;

    public GridPane getFXgrid() { return FXgrid; }
    public VBox getScoreBoard() { return ScoreBoard; }
    public Scene getScene() { return scene; }
    public HBox getFooter() {return footer; }

    private void initScene() {
        initFXComponoents();
        layout=new BorderPane();
        layout.setCenter(FXgrid);
        layout.setRight(ScoreBoard);
        layout.setBottom(footer);
        scene = new Scene(layout);
        scene.getStylesheets().add("Styles/style.css");
    }



    private void initFXComponoents() {
        initGrid();
        initScoreBoard();
        initfooter();
    }

    private void initGrid() {
        ConstBorder= Double.valueOf(Math.min(max(grid.getWidth(),grid.getHeight()) * 50,600));
        // initialize Grid
        FXgrid=new GridPane();
        FXgrid.getStyleClass().add("grid");
        FXgrid.getStylesheets().add("Styles/style.css");
        for(int i=1;i<this.grid.getHeight();i++){
            for(int j=1;j<this.grid.getWidth();j++){
                Button currentbutton=new Button();
                currentbutton.getStylesheets().add("Styles/style.css");
                //SettingSize
                double buttonborder = ConstBorder / max(this.grid.getHeight()-1, this.grid.getWidth()-1);
                //System.out.println(buttonborder + " " + grid.getHeight() + " " +grid.getWidth());
                currentbutton.setMaxSize(buttonborder, buttonborder);
                currentbutton.setMinSize(buttonborder, buttonborder);
                //Set Action
                currentbutton.setOnMouseClicked(e->{
                    ClickedButton = currentbutton;
                    if (e.getButton() == MouseButton.PRIMARY) TypeOfMove=MoveType.Reveal;
                    else TypeOfMove=MoveType.Mark;
                    GetMove();
                });
                FXgrid.add(currentbutton, j, i);
            }
        }
    }

    private void initScoreBoard() {
        // Initialize ScoreBoard
        ScoreBoard = new VBox();
        ScoreBoard.setMinWidth(200);
        ScoreBoard.setStyle("-fx-alignment: CENTER;");
        String[] colors = {"#8E44AD","#1F4788","#03A678"};
        for(Player _player:super.players){
            HBox _playerPanel=new HBox();
            _playerPanel.getStyleClass().add("playerboard");
            Label playerNameLabel=new Label(_player.getName() + " : ");
            Label playerScoreLabel=new Label(String.valueOf(_player.getCurrentScore().getScore()));
            playerNameLabel.getStyleClass().add("h2");
            playerScoreLabel.getStyleClass().add("h2");
            _playerPanel.setStyle("-fx-background-color: "+(_player.getColor())+";");
            _playerPanel.getChildren().addAll(playerNameLabel,playerScoreLabel);
            ScoreBoard.getChildren().add(_playerPanel);
        }
    }
    private void initfooter() {
        // init Last Move Label
        footer=new HBox();
        footer.setPadding(new Insets(20));
        footer.setSpacing(200);
        footer.setAlignment(Pos.CENTER);
        Label LastMoveLabel;
        LastMoveLabel =new Label();
        // init Last Move Label
        Label FlagsNumberLabel;
        FlagsNumberLabel =new Label(""+ FlagsNumber +"");
        footer.getChildren().addAll(FlagsNumberLabel,LastMoveLabel);
    }

    @Override
    public void StartGame() {
        UpdateVeiw();
        if(!(currentPlayer instanceof GUIPlayer))
            GetMove();
    }

    @Override
    public void GetMove(){
        PlayerMove move = this.currentPlayer.GetPlayerMove();
        if(currentPlayer instanceof GUIPlayer){
            move=new PlayerMove(move.getPlayer(),
                                new Square(GridPane.getRowIndex(ClickedButton),GridPane.getColumnIndex(ClickedButton)),
                                TypeOfMove);
        }
        else{
            int Position=(move.getSquare().getX()-1)*(this.grid.getWidth()-1)+(move.getSquare().getY()-1);
            ClickedButton=(Button)FXgrid.getChildren().get(Position);
            TypeOfMove=move.getType();
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
        // Update Grid View
        Square[][] feild=this.grid.getField();
        for (int i=1;i<this.grid.getHeight();i++){
            for(int j=1;j<this.grid.getWidth();j++){
                int Position=(i-1)*(this.grid.getWidth()-1)+(j-1);
                Button currentButton=(Button)FXgrid.getChildren().get(Position);
                switch (feild[i][j].getStatus()){
                    case Closed:
                        currentButton.getStyleClass().removeAll();
                        currentButton.getStyleClass().add("Closed");
                        break;
                    case OpenedEmpty:
                        currentButton.setStyle("-fx-background-color: "+feild[i][j].getColor()+"");
//                        currentButton.setStyle("-fx-background-color: #875F9A;-fx-border-width: 0;");
                        currentButton.getStyleClass().add("pressed");
                        break;
                    case OpenedNumber:


                        currentButton.getStyleClass().add("ff");
                        currentButton.getStyleClass().add("f"+(String.valueOf(feild[i][j].getNumberOfSurroundedMines()))+"");
                        currentButton.setText(""+feild[i][j].getNumberOfSurroundedMines());
                        currentButton.setStyle("-fx-background-color: "+feild[i][j].getColor()+"");
                        currentButton.getStyleClass().add("pressed");

                        break;
                    case OpenedMine:
                        currentButton.setStyle("-fx-background-color: #ff1a28");
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
            HBox currentpanel=(HBox)ScoreBoard.getChildren().get(i);
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
        // Update footer Move Label
        String LastMove="--";
        Label LastMoveLabel=(Label)footer.getChildren().get(0);
        if(ClickedButton!=null)
            LastMove=String.valueOf(GridPane.getRowIndex(ClickedButton)) + " --- " + String.valueOf(GridPane.getColumnIndex(ClickedButton));
        LastMoveLabel.setText(LastMove);

        Label FlagsNumberLabel=(Label)footer.getChildren().get(1);
        FlagsNumberLabel.setText(""+ FlagsNumber +"");
    }

    @Override
    protected void EndGame() {
        // show All The mines in The Game
        // and Update View For Shows
        for(Square mineSqauer:this.grid.getMines()){
            mineSqauer.ChangeStatus(currentPlayer,MoveType.Reveal);
        }
        UpdateVeiw();

        // Get The Winner
        Player winner=players.get(0);
        for(int i=0;i<players.size();i++) {
            if(players.get(i).getCurrentStatus()!=PlayerStatus.Lose && players.get(i).getCurrentScore().getScore()>winner.getCurrentScore().getScore()){
                winner=players.get(i);
            }
        }
        winner.setCurrentStatus(PlayerStatus.win);
        // Update footer Move Label

        Label LastMoveLabel=(Label)footer.getChildren().get(0);
        LastMoveLabel.setText(winner.getName() + " Win the Game yyyyhaaaa!!!!");

        for(int i=1;i<this.grid.getHeight();i++){
           for(int j=1;j<this.grid.getWidth();j++){
               int H=(i-1)*(this.grid.getWidth()-1)+(j-1);
               Button currentButton=(Button)FXgrid.getChildren().get(H);
               currentButton.setDisable(true);
           }
        }
    }
}
