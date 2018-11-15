package CustomSequences;

import Models.Grid.SquareType;
import Models.Move.PlayerMove;

import java.util.Random;

public class MineSweeperGrid extends SquareType2DArray {
    private int MinesCount;
    private int ShieldsCount;
    private int HeroShieldsCount;
    private PlayerMove FirstMove;

    public MineSweeperGrid(int _width, int _height, int _MinesCount, PlayerMove move)  {
        super(_width,_height, SquareType.Empty);
        MinesCount = _MinesCount;
        FirstMove = move;
        GenerateGrid(1);
    }

    public MineSweeperGrid(int _width, int _height, int _MinesCount,int _ShieldsCount, PlayerMove move) {
        super(_width,_height,SquareType.Empty);
        MinesCount = _MinesCount;
        ShieldsCount = _ShieldsCount;
        FirstMove = move;
        GenerateGrid(2);
    }

    public MineSweeperGrid(int _width, int _height, int _MinesCount,int _ShieldsCount,int  _HeroShieldsCount, PlayerMove move) {
        super(_width,_height,SquareType.Empty);
        MinesCount = _MinesCount;
        ShieldsCount = _ShieldsCount;
        HeroShieldsCount = _HeroShieldsCount;
        FirstMove = move;
        GenerateGrid(3);
    }

    //  <<!------IMPORTANT------>>\\

    //Generating Grid Taking This Cases:
        //Case Type = 1: Means that game had only mines
        //Case Type = 2: Means that game had Mines & Shields
        //Case Type = 3: Means that game had Mines , Shields & HeroShields

    void GenerateGrid( int Type) {
       GenerateRandomMines();
       if (Type > 1) { GenerateRandomShields();}
       if (Type > 2) { GenerateRandomHeroShields(); }
    }


    public void GenerateRandomMines() {
        for (int i = 0 ;i < MinesCount; i++) {
            Random rand = new Random();
            int randomWidth,randomHeight;
            //checking that there is no duplicated mines in one square
            while (true) {
                randomWidth = rand.nextInt(width-2)+1;
                randomHeight = rand.nextInt(height-2)+1;
                if(randomHeight==FirstMove.getSquare().getX() && randomWidth==FirstMove.getSquare().getY())continue;
                if (type[randomHeight][randomWidth] == SquareType.Empty) {
                    type[randomHeight][randomWidth] = SquareType.Mine;
                    break;
                }
            }
        }
    }

    public void GenerateRandomShields() {
        for (int i = 0 ;i < ShieldsCount; i++) {
            Random rand = new Random();
            int randomWidth,randomHeight;
            //checking that there is no duplicated mines in one square
            while (true) {
                randomWidth = rand.nextInt(width-2)+1;
                randomHeight = rand.nextInt(height-2)+1;
                if (type[randomHeight][randomWidth] == SquareType.Empty) {
                    type[randomHeight][randomWidth] = SquareType.Shield;
                    break;
                }
            }
        }
    }

    public void GenerateRandomHeroShields() {
        for (int i = 0 ;i < HeroShieldsCount; i++) {
            Random rand = new Random();
            int randomWidth,randomHeight;
            //checking that there is no duplicated mines in one square
            while (true) {
                randomWidth = rand.nextInt(width-2)+1;
                randomHeight = rand.nextInt(height-2)+1;
                if (type[randomHeight][randomWidth] == SquareType.Empty) {
                    type[randomHeight][randomWidth] = SquareType.HeroShield;
                    break;
                }
            }
        }
    }
}
