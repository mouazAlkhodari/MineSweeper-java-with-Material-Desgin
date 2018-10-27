package minesweeper;
import CustomSequences.Boolean2DArray;
import java.util.Arrays;
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

    public Square[][] getField() {
        return field;
    }

    public void InitGrid() {
        field = new Square[height][width];
        //to generate random coordinates for mines
        Boolean2DArray minesCoordinates = new Boolean2DArray(width, height, Boolean.FALSE);
        minesCoordinates.GenerateRandomMines(minesCount);
        int[][] numberOfSurroundedmines = new int[height][width];
        
        
        
        //init sqaures inside the field
        for (int i = 0 ;i < height; i++) { 
            for (int j = 0;j < width; j++) { 
               // field[i][j] = new Square(i, j,minesCoordinates[i][j]);
            }
        }
    }
        
    public void AcceptMove(PlayerMove move) {

    }
    
    
}
