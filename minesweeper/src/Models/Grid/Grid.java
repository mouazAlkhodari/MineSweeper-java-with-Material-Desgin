package Models.Grid;

import CustomSequences.MinesCoor2DArray;
import CustomSequences.SurroundingMines2DArray;
import Models.Game.Game;
import Models.Move.MoveResult;
import Models.Move.MoveType;
import Models.Move.PlayerMove;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Grid {
    // <__ DATA MEMBERS __> \\
    private int width;
    private int height;
    private int minesCount;
    private Square[][] field;
    private ArrayList<Square> mines;
    private Game CurrentGame;

    // <__ CONSTRUCTERS __> \\
    public Grid(int width,int height,int minesNumber) {
        // +1 because Number Start From 1
        this.width=width+1;
        this.height=height+1;
        this.minesCount = minesNumber;
        InitGrid();
    }

    // <__ METHODS __> \\
    public void InitGrid() {
        field = new Square[height][width];
        mines=new ArrayList<Square>();
        //to generate random coordinates for mines
        MinesCoor2DArray minesCoordinates = new MinesCoor2DArray(width, height, Boolean.FALSE);
        minesCoordinates.GenerateRandomMines(minesCount);
        SurroundingMines2DArray numberOfSurroundedmines = new SurroundingMines2DArray(width, height, minesCoordinates);

        //init sqaures inside the field
        for (int i = 1 ;i < height; i++) {
            for (int j = 1;j < width; j++) {
                field[i][j] = new Square(i, j,minesCoordinates.arr[i][j],numberOfSurroundedmines.arr[i][j]);
                if(minesCoordinates.arr[i][j]){ mines.add(field[i][j]); }
            }
        }
    }
    public void initGrid(PlayerMove move) {
        //to generate random coordinates for mines
        MinesCoor2DArray minesCoordinates = new MinesCoor2DArray(width, height,Boolean.FALSE);
        minesCoordinates.GenerateRandomMines(minesCount,move);
        SurroundingMines2DArray numberOfSurroundedmines = new SurroundingMines2DArray(width, height, minesCoordinates);
        //init sqaures inside the field
        for (int i = 1 ;i < height; i++) {
            for (int j = 1;j < width; j++) {
                if(minesCoordinates.arr[i][j]){ mines.add(field[i][j]); }
                field[i][j] = new Square(i, j,minesCoordinates.arr[i][j],numberOfSurroundedmines.arr[i][j]);
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
                    if (!SurroundingMines2DArray.CheckIndex(i, j, width, height)) continue;

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

    //Getters

    public ArrayList<Square> getMines() { return mines; }

    public int getMinesCount() { return minesCount; }
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public Square[][] getField() { return this.field;}

}


