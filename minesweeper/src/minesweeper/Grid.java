package minesweeper;
import CustomSequences.MinesCoor2DArray;
import CustomSequences.SurroundingMines2DArray;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Grid {
    private int width,height,minesCount;
    private Square[][] field;
    private Mine[] mines;
    private Game CurrentGame;

    public Grid(int width,int height,int minesNumber) {
        this.width=width+1;
        this.height=height+1;
        this.minesCount = minesNumber;
        InitGrid();
    }
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}

    public Square[][] getField() { return this.field;}

    public void InitGrid() {
        field = new Square[height][width];
        //to generate random coordinates for mines
        MinesCoor2DArray minesCoordinates = new MinesCoor2DArray(width, height, Boolean.FALSE);
        minesCoordinates.GenerateRandomMines(minesCount);
        SurroundingMines2DArray numberOfSurroundedmines = new SurroundingMines2DArray(width, height, minesCoordinates);
     
        //init sqaures inside the field
        for (int i = 0 ;i < height; i++) { 
            for (int j = 0;j < width; j++) { 
                field[i][j] = new Square(i, j,minesCoordinates.arr[i][j],numberOfSurroundedmines.arr[i][j]);
            }
        }
    }
        
    public void AcceptMove(PlayerMove move) {
        move.setSquare(field[move.getSquare().getX()][move.getSquare().getY()]);
        if(move.getType()==MoveType.Mark){
            move.getSquare().ChangeStatus(move.getPlayer(), MoveType.Mark);
        }
        else{
            if(move.getSquare().getNumberOfSurroundedMines()==0){
                move.getSquare().ChangeStatus(move.getPlayer(), MoveType.Reveal);
            }
            else{
                this.floodFill(move);
            }
        }
    }
    private void floodFill(PlayerMove move) {
        Queue<PlayerMove> Q=new LinkedList<PlayerMove>();
        Q.add(move);
        while(!Q.isEmpty()){
            PlayerMove curMove=Q.poll();
            Square curScuare=curMove.getSquare();
            curScuare.ChangeStatus(curMove.getPlayer(), MoveType.Reveal);
            for(int i=curScuare.getX()-1;i<curScuare.getX()+1;i++){
                for(int j=curScuare.getY()-1;i<curScuare.getY()+1;i++){
                    Square toScuare=field[i][j];
                    if(toScuare.getStatus()==SquareStatus.Closed){
                        ((LinkedList<PlayerMove>) Q).add(new PlayerMove(move.getPlayer(),toScuare,MoveType.Reveal,new MoveResult()));
                    }
                }
            }
        }
    }


}
