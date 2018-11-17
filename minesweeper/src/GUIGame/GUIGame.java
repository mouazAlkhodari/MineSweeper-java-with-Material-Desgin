package GUIGame;

import Models.Game.*;
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
import MineSweeperGameDefineException.IllegalGameMove;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class GUIGame extends NormalGame {
    private static final List<PlayerPanel> PlayersPanel =new ArrayList<>() ;
    // <__ DATA MEMBERS __> \\
    protected MoveType TypeOfMove;
    protected Button ClickedButton;
    protected static Double ConstBorder=400.0;

    protected GridPane FXgrid;
    protected VBox ScoreBoard;
    protected HBox footer;
    protected HBox top;

    protected Scene scene;
    protected BorderPane layout;

    protected Button BackButton;
    protected GUIGameMainMenu Begin;
    // <__ CONSTRUCTOR __> \\
    public GUIGame(List _players){
        super(_players);
        initScene();
    }
    public GUIGame(int Width, int Height, int NumMines, List ListOfPlayers) {// Constructor
        super(Width,Height,NumMines,ListOfPlayers);
        initScene();
    }
    public GUIGame(int Width, int Height, int NumMines, List _players, Points points, WhenHitMine pressMineBehavior,WhenScoreNegative scoreNegativeBehavior){
        super(Width,Height,NumMines,_players,points,pressMineBehavior,scoreNegativeBehavior);
        initScene();
    }

    // <__ GETTERS-SETTERS __> \\
    //Getters;

    public GridPane getFXgrid() { return FXgrid; }
    public VBox getScoreBoard() { return ScoreBoard; }
    public Scene getScene() { return scene; }
    public HBox getFooter() {return footer; }

    public GUIGameMainMenu getBegin() { return Begin; }

    public void setBegin(GUIGameMainMenu begin) { Begin = begin; }

    protected void initScene() {
        initFXComponoents();
        layout=new BorderPane();
        layout.setCenter(FXgrid);
        layout.setRight(ScoreBoard);
        layout.setBottom(footer);
        layout.setTop(top);
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

    protected void initScoreBoard() {
        // Initialize ScoreBoard
        ScoreBoard = new VBox();
        ScoreBoard.setMinWidth(200);
        ScoreBoard.setStyle("-fx-alignment: CENTER;");
        String[] colors = {"#8E44AD","#1F4788","#03A678"};
        for(Player _player:super.players){
            PlayerPanel _playerPanel=new PlayerPanel(_player);
            PlayersPanel.add(_playerPanel);
            ScoreBoard.getChildren().add(_playerPanel.getLeftPanel());
            if(_player==currentPlayer){
                top=_playerPanel.getTopPanel();
            }
        }
    }
    private void initfooter() {
        // init Last Move Label
        footer=new HBox();
        footer.setPadding(new Insets(20));
        footer.setSpacing(100);
        footer.setAlignment(Pos.CENTER);
        Label LastMoveLabel;

        LastMoveLabel =new Label();
        // init Last Move Label
        Label FlagsNumberLabel;
        FlagsNumberLabel =new Label("Flags left: "+ FlagsNumber +"");
        FlagsNumberLabel.getStyleClass().addAll("buttonlabel","h3","padding-sm");
        LastMoveLabel.getStyleClass().addAll("buttonlabel","h3","padding-sm");

        BackButton =new Button("Back");
        BackButton.getStyleClass().addAll("menubutton","h3");
        BackButton.setPrefSize(100,40);
        BackButton.setOnAction(e->{
            Begin.Window.setScene(Begin.getWelcomescene());
            Begin.Window.centerOnScreen();
        });
        footer.getChildren().addAll(FlagsNumberLabel,LastMoveLabel, BackButton);
    }


    @Override
    public void StartGame() {
        UpdateVeiw(moves);
        if(!(currentPlayer instanceof GUIPlayer))
            GetMove();
    }

    @Override
    public void GetMove(){
        PlayerMove move = this.currentPlayer.GetPlayerMove();

        // For view And Git Clicked Button
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

        try {
            AcceptMove(move);
        } catch (IllegalGameMove illegalGameMove) {
            illegalGameMove.handle();
        }

        // need else some thing wrong input Or Some Thing Like that :3
        if(this.status== GameStatus.Finish){
            EndGame();
        }
        else{
            UpdateVeiw(moves);
            if (!(currentPlayer instanceof GUIPlayer))
                GetMove();
        }
    }
    @Override
    protected void UpdateVeiw(List<PlayerMove> Moves){
        // Update Grid View
        for(PlayerMove currentmove:Moves) {
            int i = currentmove.getSquare().getX();
            int j = currentmove.getSquare().getY();
            int Position = (i - 1) * (this.grid.getWidth() - 1) + (j - 1);
            Button currentButton = (Button) FXgrid.getChildren().get(Position);
            Square currentSquare = currentmove.getSquare();
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
        for(int i=0;i<players.size();i++){
            Player _player=players.get(i);
            PlayerPanel _currentpanel=PlayersPanel.get(i);
            _currentpanel.Update();
            if(_player==currentPlayer){
                top=_currentpanel.getTopPanel();
            }
            layout.setTop(top);
        }
        // Update footer Move Label
        String LastMove="--";
        Label LastMoveLabel=(Label)footer.getChildren().get(1);
        if(ClickedButton!=null)
            LastMove=String.valueOf(GridPane.getRowIndex(ClickedButton)) + " --- " + String.valueOf(GridPane.getColumnIndex(ClickedButton));
        LastMoveLabel.setText(LastMove);

        Label FlagsNumberLabel=(Label)footer.getChildren().get(0);
        FlagsNumberLabel.setText("Flags left: "+ FlagsNumber +"");
    }

    @Override
    protected void EndGame() {
        // show All The mines in The Game
        // and Update View For Shows
        List<PlayerMove> curr = new ArrayList<>();
        for(Square mineSqauer:this.grid.getMines()){
            mineSqauer.ChangeStatus(currentPlayer,MoveType.Reveal);
            curr.add(new PlayerMove(currentPlayer,mineSqauer));
        }
        UpdateVeiw(curr);

        // Get The Winner
        Player winner=players.get(0);
        for(int i=0;i<players.size();i++) {
            if(players.get(i).getCurrentScore().getScore()>winner.getCurrentScore().getScore()){
                winner=players.get(i);
            }
        }
        // Update footer Move Label

        Label LastMoveLabel=(Label)footer.getChildren().get(0);
        String WinnerStr=winner.getName() + " Win The Game";
        if(players.size()==1){
            WinnerStr = winner.getCurrentStatus()==PlayerStatus.Lose ?"You Lose" : "You Win";
        }
        winner.setCurrentStatus(PlayerStatus.win);
        LastMoveLabel.setText(WinnerStr);

        for(int i=1;i<this.grid.getHeight();i++){
           for(int j=1;j<this.grid.getWidth();j++){
               int H=(i-1)*(this.grid.getWidth()-1)+(j-1);
               Button currentButton=(Button)FXgrid.getChildren().get(H);
               currentButton.setDisable(true);
           }
        }
    }
}
