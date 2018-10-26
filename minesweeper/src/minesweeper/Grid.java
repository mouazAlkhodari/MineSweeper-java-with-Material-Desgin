package minesweeper;
import java.util.Arrays;
import java.util.Random;


public class Grid {
    private Square[][] field;
    private Mine[] mines;
    private Game CurrentGame;

    public Grid(int width,int height,int minesNumber) {
        InitGrid(width, height, minesNumber);
    }
    
    
    public void InitGrid(int width,int height,int minesNumber) { 
        field = new Square[height][width];
        //to generate random coordinates for mines
        boolean[][] minesCoordinates = new boolean[height][width];
        //fill the matrix it with false 
         for (int i = 0 ;i < height; i++) { 
             Arrays.fill(minesCoordinates[i], false);
        }
         //fill random coordinates with true (number of coordinates according to nnumber of mines)
         for (int i = 0 ;i < minesNumber; i++) { 
             Random rand = new Random();
             int randomWidth,randomHeight;
             //checking that there is no duplicated mines in one square
             while (true) { 
                 randomWidth = rand.nextInt(width);
                 randomHeight = rand.nextInt(height);
                 if (!minesCoordinates[randomHeight][randomWidth]) { 
                     minesCoordinates[randomHeight][randomWidth] = true;
                     break;
                 }
             }
         }
        //init sqaures inside the field
        for (int i = 0 ;i < height; i++) { 
            for (int j = 0;j < width; j++) { 
                field[i][j] = new Square(i, j,minesCoordinates[i][j]);
            }
        }
    }
        
    public void AcceptMove(PlayerMove move) { 
        
    }
    
}
