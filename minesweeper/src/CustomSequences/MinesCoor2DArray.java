/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomSequences;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Da
 */
public class MinesCoor2DArray {
    public boolean[][] arr;
    public int width,height;
    
    
    public MinesCoor2DArray(int width,int height,Boolean value) {
        this.width = width;
        this.height = height;
        arr = new boolean[height][width];
        for (int i = 0 ;i < height; i++) { 
             Arrays.fill(arr[i], value);
        }
    }
    
    public MinesCoor2DArray(int width,int height) { 
        this.width = width;
        this.height = height;
        arr = new boolean[height][width];
        for (int i = 0 ;i < height; i++) { 
             Arrays.fill(arr[i], false);
        }
    }   
    
    public void GenerateRandomMines(int NumberOfMines) { 
         for (int i = 0 ;i < NumberOfMines; i++) {
             
             Random rand = new Random();
             int randomWidth,randomHeight;
             //checking that there is no duplicated mines in one square
             while (true) { 
                 randomWidth = rand.nextInt(width);
                 randomHeight = rand.nextInt(height);
                 if (!arr[randomHeight][randomWidth]) { 
                     arr[randomHeight][randomWidth] = true;
                     break;
                 }
             }
         }
    }
}
