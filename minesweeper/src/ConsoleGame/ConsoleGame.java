package ConsoleGame;
import extensions.BaseAlphabit.Converter;
import Models.Game.GameStatus;
import Models.Game.NormalGame;
import Models.Grid.Square;
import Models.Player.Player;
import Models.Move.PlayerMove;
import Models.Player.PlayerStatus;
import MineSweeperGameDefineException.IllegalGameMove;

import java.util.ArrayList;
import java.util.List;

public class ConsoleGame extends NormalGame {
    protected class ConsoleTimer extends Timer{

        public ConsoleTimer(int time){
            super(time);
        }
        @Override
        public void Show(double Time) {
            System.out.println(Time);
        }

        @Override
        public void EndTimer() {
            currentPlayer.stop();
            moves=new ArrayList<>();
            currentRules.DecideNextPlayer(moves);
            UpdateVeiw(moves);
            GetMove();
        }
    }
    public ConsoleGame(List _players){
        super(_players);
    }
    public ConsoleGame(int Width, int Height, int NumMines, List ListOfPlayers) {// Constructor
        super(Width,Height,NumMines,ListOfPlayers);
    }

    @Override
    public void StartGame() {
        UpdateVeiw(moves);
        currentTimer=new ConsoleTimer(currentPlayer.getTimeforTimer());
        currentTimer.start();
        GetMove();
    }

    @Override
    public void GetMove(){// get The Move From The Console Player and then Apply it
        PlayerMove move = this.currentPlayer.GetPlayerMove();
        try {
            AcceptMove(move);
        } catch (IllegalGameMove illegalGameMove) {
            illegalGameMove.handle();
            if(this.status== GameStatus.Finish){
                EndGame();
            }
            else{
                GetMove();
            }
            return;
        }
        if(this.status== GameStatus.Finish){
            EndGame();
        }
        else{
            UpdateVeiw(moves);
            currentTimer=new ConsoleTimer(currentPlayer.getTimeforTimer());
            currentTimer.start();
            GetMove();
        }
    }
    @Override
    protected void UpdateVeiw(List<PlayerMove> Moves) {
        //PrintGrid();
        //print in One row number Of each Column In Grid
        System.out.print("   ");
        for(int i=0;i+1<this.grid.getWidth();i++){
            System.out.print(" "+Converter.valueOf(i));
        }
       // System.out.println();
        Square[][] feild=this.grid.getField();
        for(int i=1;i<this.grid.getHeight();i++){
            System.out.println();
           String number = ConsoleGame.fixedLengthString(String.valueOf(i), 2);
           System.out.print(number);
                     for (int j=1;j<this.grid.getWidth();j++){
                         if(status==GameStatus.FirstMove){
                             System.out.print("O ");
                            continue;
                         }
                switch (feild[i][j].getStatus()){
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
        System.out.println(currentPlayer.getName());
    }
    @Override
    protected void EndGame() {
        UpdateVeiw(moves);
        System.out.println("scores:");
        Player winner=players.get(0);
        for(int i=0;i<players.size();i++) {
            System.out.println(players.get(i).getName() + ": "+players.get(i).getCurrentScore());
            if(players.get(i).getCurrentScore().getScore()>winner.getCurrentScore().getScore()){
                winner=players.get(i);
            }
        }
        winner.setCurrentStatus(PlayerStatus.win);
        String WinnerStr=winner.getName() + " Win The Game yyyhaaa";
        if(players.size()==1){
            WinnerStr = winner.getCurrentStatus()==PlayerStatus.Lose ?"You Lose" : "You Win";
        }
        winner.setCurrentStatus(PlayerStatus.win);
        System.out.println(WinnerStr);
    }

}
