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
public class Boolean2DArray {
    public boolean[][] arr;
    public Boolean2DArray(int width,int height,Boolean value) {
        for (int i = 0 ;i < height; i++) { 
             Arrays.fill(arr[i], value);
        }
    }
    
    public Boolean2DArray(int width,int height) { 
        for (int i = 0 ;i < height; i++) { 
             Arrays.fill(arr[i], false);
        }
    }
    Boolean
    
    
}
