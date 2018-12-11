/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extensions.CustomSequences;

import java.util.Arrays;

/**
 *
 * @author Da
 */
public class Int2DArray {
    public int[][] arr;
    public int width,height;
    
    
    public Int2DArray(int width, int height, int value) {
        this.width = width;
        this.height = height;

    }
    
    public Int2DArray(int width, int height) {
        this.width = width;
        this.height = height;
        arr = new int[height][width];
        for (int i = 0 ;i < height; i++) { 
             Arrays.fill(arr[i], 0);
        }
    }
}
