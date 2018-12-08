package Models.Grid;

import extensions.CustomSequences.MineSweeperGrid;
import extensions.CustomSequences.SquareType2DArray;
import extensions.CustomSequences.SurroundingMinesMatrix;
import Models.Game.Game;
import Models.Move.MoveResult;
import Models.Move.MoveType;
import Models.Move.PlayerMove;
import MineSweeperGameDefineException.IllegalBoundsOfGrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Grid implements Serializable {
    // <__ DATA MEMBERS __> \\
    private int width;
    private int height;
    private int minesCount;
    private int shieldsCount;
    private int heroShieldsCount;
    private Square[][] field;
    private ArrayList<Square> mines;
    private Game CurrentGame;

    // <__ CONSTRUCTERS __> \\
    public Grid(int _width,int _height,int _minesCount, int _shieldsCount, int _heroShieldsCount) throws IllegalBoundsOfGrid{
        if (_width<=0 || _height<=0 || _minesCount<0)throw new IllegalBoundsOfGrid("Illegal Bound Of Grid");
        if(_minesCount >_height*_width)throw new IllegalBoundsOfGrid("Mines more than All squares");
        this.width=_width+1;
        this.height=_height+1;
        this.minesCount = _minesCount;
        this.shieldsCount = _shieldsCount;
        this.heroShieldsCount = _heroShieldsCount;
    }
    public Grid(int _width,int _height,int _minesCount, int _shieldsCount, int _heroShieldsCount,PlayerMove _move) throws IllegalBoundsOfGrid{
        if (_width<=0 || _height<=0 || _minesCount<0)throw new IllegalBoundsOfGrid("Illegal Bound Of Grid");
        if(_minesCount >_height*_width)throw new IllegalBoundsOfGrid("Mines more than All squares");
        this.width=_width+1;
        this.height=_height+1;
        this.minesCount = _minesCount;
        this.shieldsCount = _shieldsCount;
        this.heroShieldsCount = _heroShieldsCount;
        InitGrid(_move);
    }



    // <__ METHODS __> \\
    protected void InitGrid(PlayerMove move) {
        field = new Square[height][width];
        mines=new ArrayList<Square>();
        //to generate random coordinates for mines
        MineSweeperGrid generatedGrid = new MineSweeperGrid(width, height,minesCount,shieldsCount,heroShieldsCount,move);
        SurroundingMinesMatrix numberOfSurroundedmines = new SurroundingMinesMatrix(width, height, generatedGrid);

        //init sqaures inside the field
        for (int i = 1 ;i < height; i++) {
            for (int j = 1;j < width; j++) {

                Square currentSquare=new Square(i, j,generatedGrid.type[i][j],numberOfSurroundedmines.arr[i][j]);
                if(generatedGrid.type[i][j] == SquareType.Mine){ mines.add(currentSquare); }
                field[i][j] = currentSquare;

            }
        }
    }
    public ArrayList<PlayerMove> AcceptMove(PlayerMove move){
        ArrayList<PlayerMove> PlayerMoves=new ArrayList<PlayerMove>();
        move.setSquare(field[move.getSquare().getX()][move.getSquare().getY()]);
        if(move.getType()== MoveType.Mark){
            move.getSquare().ChangeStatus(move.getPlayer(), MoveType.Mark);
            PlayerMoves.add(move);
        }
        else{
            PlayerMoves=this.floodFill(move);
        }
        return PlayerMoves;
    }
    private ArrayList<PlayerMove> floodFill(PlayerMove move) {
        //Adding Square
        ArrayList<PlayerMove> PlayerMoves=new ArrayList<PlayerMove>();
        Queue<PlayerMove> Q = new LinkedList<PlayerMove>();
        Q.add(move);

        while (!Q.isEmpty()) {
            //Pulling The last move added to queue
            PlayerMove CurrentMove = Q.poll();
            PlayerMoves.add(CurrentMove);
            Square CurrentSquare = CurrentMove.getSquare();
            //Open The Square
            CurrentSquare.ChangeStatus(CurrentMove.getPlayer(), MoveType.Reveal);
            if(CurrentSquare.getStatus() != SquareStatus.OpenedEmpty) { continue; }

            //if The Square is Not empty then There will not flood into the other squares and it swill quit here
            //but if its empty then we will start iterating over the sudrrounded squares and open them if the dont contain mines
            //note that each empty square that opened will be added to the queue so it will also open the surrounding squares of it
            //We didn't steal it from internet
            //We owned This Code
            //Please Donate Us
            //Bye

            for (int i = CurrentSquare.getX() - 1; i <= CurrentSquare.getX() + 1; i++) {
                for (int j = CurrentSquare.getY() - 1; j <= CurrentSquare.getY() + 1; j++) {
                    // in case Out Of Grid
                    if (!SquareType2DArray.CheckIndex(i, j, width, height)) continue;

                    Square SurroundedSquare = field[i][j];
                    //Checking if Square is closed and has no surrounded mines then we will open it
                    if (SurroundedSquare.getStatus() == SquareStatus.Closed && !SurroundedSquare.isMine()) {
                        SurroundedSquare.ChangeStatus(CurrentMove.getPlayer(), MoveType.Reveal);
                        ((LinkedList<PlayerMove>) Q).add(new PlayerMove(move.getPlayer(), SurroundedSquare, MoveType.Reveal, new MoveResult()));
                    }
                }
            }
        }
        return PlayerMoves;
    }

    public void reset(){
        for(int i=1;i<width;i++){
            for(int j=1;j<height;j++){
                if(field[i][j]!=null)
                   field[i][j].reset();
            }
        }
    }
    //Getters

    public ArrayList<Square> getMines() { return mines; }

    public int getMinesCount() { return minesCount; }
    public int getShieldsCount() { return shieldsCount; }
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public Square[][] getField() { return this.field;}

}


