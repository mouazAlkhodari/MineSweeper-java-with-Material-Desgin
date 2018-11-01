package minesweeper;
import BaseAlphabit.Converter;

import java.util.List;

public class ConsoleGame extends NormalGame {

    public ConsoleGame(int Width, int Height, int NumMines, List ListOfPlayers) {// Constructor
        for(Object curPlayer:ListOfPlayers) {// add Players To the Game
            this.AddPlayer((Player) curPlayer);
        }
        initGame(Width,Height,NumMines);
    }

    @Override
    public void StartGame() {
        UpdateVeiw();
        GetMove();
    }

    @Override
    public void GetMove(){// get The Move From The Console Player and then Apply it
        PlayerMove move = this.currentPlayer.GetPlayerMove();
        if(AcceptMove(move))
            ApplyPlayerMove(move);
        // need else some thing wrong input Or Some Thing Like that :3
        if(this.status==GameStatus.Finish){
            EndGame();
        }
        else{
            UpdateVeiw();
            GetMove();
        }
    }
    @Override
    protected void UpdateVeiw() {
        //PrintGrid();
        //print in One row number Of each Column In Grid
        System.out.print("    ");
        for(int i=0;i+1<this.grid.getWidth();i++){
            System.out.print(" "+Converter.valueOf(i));
        }
        System.out.println();
        Square[][] feild=this.grid.getField();
        for(int i=1;i<this.grid.getHeight();i++){
            System.out.println();
           String number = ConsoleGame.fixedLengthString(String.valueOf(i), 2);
           System.out.print(number);
                     for (int j=1;j<this.grid.getWidth();j++){
                switch (feild[i][j].status){
                    case Closed:
                        System.out.print("O ");
                        break;
                    case Marked:
                        System.out.print("p ");
                        break;
                    case OpenedMine:
                        System.out.print("B ");
                        break;
                    case OpenedNumber:
                        System.out.print(feild[i][j].getNumberOfSurroundedMines()+" ");
                        break;
                    case OpenedEmpty:
                        System.out.print(". ");
                        break;
                }
            }
        }
        System.out.println();

    }

    // In Win and Lose Func
    // Some Thing Will Change In case Of Multi Player
    @Override
    protected void EndGame() {
        UpdateVeiw();
        System.out.println("You catch all Mines and win the game!!\n");
    }
    public static String fixedLengthString(String string, int length) {
        return String.format("%1$"+length+ "s  ", string);
    }

    // That Function for Debug
    private void PrintGrid() {
        System.out.print("   ");
        for(int i=0;i+1<this.grid.getWidth();i++){
            System.out.print(" "+Converter.valueOf(i));
        }
        System.out.println();
        Square[][] feild=this.grid.getField();
        for(int i=1;i<this.grid.getHeight();i++){
            System.out.print("\n");
            System.out.print(" "+i+"  ");
            for (int j=1;j<this.grid.getWidth();j++){
                if(!feild[i][j].isMine())
                    System.out.print(feild[i][j].getNumberOfSurroundedMines()+" ");
                else System.out.print("B ");
            }
        }
        System.out.println();
    }

}
