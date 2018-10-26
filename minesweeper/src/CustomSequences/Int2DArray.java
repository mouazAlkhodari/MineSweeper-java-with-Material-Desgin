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
public class Int2DArray {
    public int[][] arr;
    Boolean2DArray mines;
    public int width,height;
    public Int2DArray(int width,int height,int value,Boolean2DArray mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        for (int i = 0 ;i < height; i++) { 
             Arrays.fill(arr[i],value);
        }
       
    }
    
    public Int2DArray(int width,int height,Boolean2DArray mines) { 
        this.width = width;
        this.height = height;
        this.mines = mines;
        for (int i = 0 ;i < height; i++) { 
             Arrays.fill(arr[i],0);
        }
    }   
}
