/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomSequences;

import java.util.Arrays;

/**
 *
 * @author Da
 */
public class SurroundingMines2DArray {
    public int[][] arr;
    MinesCoor2DArray mines;
    public static int width,height;
    
    // <__ CONSTRUCTERS __> \\
    // Constructer initializr the field with 0
    public SurroundingMines2DArray(int width,int height,MinesCoor2DArray mines) { 
        SetDataMembers(width,height,mines);
        Fill2DArray(0);
        CalculateNOSM();
    }   
    // Constructer initializr the field with custom Value
    public SurroundingMines2DArray(int width,int height,int value,MinesCoor2DArray mines) {
        SetDataMembers(width,height,mines);
        Fill2DArray(value);
        CalculateNOSM();
       
    }
    
    // <__ ASSISTANT FUNCTIONS __> \\
    
    //To set width,height & mines coordinates in the constructer
    private void SetDataMembers(int width,int height,MinesCoor2DArray mines) { 
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.arr = new int[height][width];
    }
    // To fill 2D Array with some Value
    private void Fill2DArray(int value) { 
        for (int i = 0 ;i < height; i++) { 
             Arrays.fill(arr[i],value);
        }
    }
    
    //to Check if Coordinates is exist
    public static Boolean CheckIndex(int x,int y) {
        return (x >= 1 && x < height && y >= 1 && y < width);
    }
    
    //Calcuate Number of surrounding mines in each square in th field
    private void CalculateNOSM() {
          for (int i=1;i<height;i++) {
            for (int j = 1;j < width; j++) {
                //here we are in each square
                //for each square we will go to the surrounding squares and calculate number of mines in each one
                //Then we sum the result and put it in this square;

                for (int x = i - 1;x <= i + 1;x++) {
                    for (int y = j - 1;y <= j + 1;y++) {
                        if (CheckIndex(x, y) && mines.arr[x][y]) {
                            arr[i][j]++;
                        }
                    }
                }
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
   
}
