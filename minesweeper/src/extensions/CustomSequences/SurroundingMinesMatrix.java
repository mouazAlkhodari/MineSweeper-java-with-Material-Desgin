package extensions.CustomSequences;

import Models.Grid.SquareType;

import java.util.Arrays;

public class SurroundingMinesMatrix extends SquareType2DArray {
    private MineSweeperGrid CurrentGrid;
    public int[][] arr;



    public SurroundingMinesMatrix(int _width, int _height,MineSweeperGrid _CurrentGrid) {
        super(_width,_height);
        arr = new int[height][width];
        for (int i = 0 ;i < height; i++) {
            Arrays.fill(arr[i], 0);
        }
        CurrentGrid = _CurrentGrid;
        CalculateNOSM();
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
                        if (CheckIndex(x, y) && CurrentGrid.type[x][y] == SquareType.Mine) {
                            arr[i][j]++;
                        }
                    }
                }
            }
        }
    }

}
