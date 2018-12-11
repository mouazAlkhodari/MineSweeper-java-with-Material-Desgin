/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extensions.CustomSequences;

import Models.Grid.SquareType;

import java.util.Arrays;

/**
 *
 * @author Da
 */
public class SquareType2DArray {
    public SquareType[][] type;
    public int width,height;

    // <__ CONSTRUCTERS __> \\
    // Constructer initializr the field with 0
    public SquareType2DArray(int width, int height) {
        SetDataMembers(width,height);
        Fill2DArray(SquareType.Empty);
    }   
    // Constructer initializr the field with custom Value
    public SquareType2DArray(int width, int height, SquareType type) {
        SetDataMembers(width,height);
        Fill2DArray(type);
    }
    
    // <__ ASSISTANT FUNCTIONS __> \\
    
    //To set width,height & mines coordinates in the constructer
    private void SetDataMembers(int width, int height) {
        this.width = width;
        this.height = height;
        this.type = new SquareType[height][width];
    }
    // To fill 2D Array with some Value
    private void Fill2DArray(SquareType _type) {
        for (int i = 0 ;i < height; i++) { 
             Arrays.fill(type[i],_type);
        }
    }
    
    //to Check if Coordinates is exist
    public static Boolean CheckIndex(int x,int y,int width,int height) {
        return (x >= 1 && x < height && y >= 1 && y < width);
    }

    protected Boolean CheckIndex(int x,int y) { return (x >= 1 && x < height && y >= 1 && y < width); }
   
}
