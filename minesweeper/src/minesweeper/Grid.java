package minesweeper;
import CustomSequences.MinesCoor2DArray;
import CustomSequences.SurroundingMines2DArray;


public class Grid {
    private int width,height,minesCount;
    private Square[][] field;
    private Mine[] mines;
    private Game CurrentGame;

    public Grid(int width,int height,int minesNumber) {
        this.width=width;
        this.height=height;
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
        
    }
    
    
}
