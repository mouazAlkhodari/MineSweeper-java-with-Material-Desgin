package minesweeper;

import java.util.ArrayList;

public abstract class NormalGame extends Game{

    class DefaultRules extends GameRules{ // inner Class
        public int GetScoreChange(ArrayList moves){
            return 0;
        }
        public Player DecideNextPlayer(ArrayList moves){
            return null;
        }
    }
    public void ApplyPlayerMove(PlayerMove move) {
        // here We ApPly The move And then Check The Status Of The Game
        this.grid.AcceptMove(move);
        Square[][] feild=this.grid.getField();
        int Closed=0,Marked=0;
        // here We Check The Number Of Closed And Marked Squares
        // and in case If They equal To The number Of Mines The The Game Is Over
        for(int i=1;i<this.grid.getHeight();i++){
            for (int j=1;j<this.grid.getWidth();j++){
                switch (feild[i][j].status){
                    case Closed:
                        Closed++;
                        break;
                    case Marked:
                        Marked++;
                        break;
                    case OpenedMine:
                        this.setStatus(GameStatus.Lose);
                        break;
                }
            }
        }
        if(this.getStatus()!=GameStatus.Lose && Closed+Marked==this.grid.getMinesCount()){
            this.setStatus(GameStatus.Win);
        }
    }
}
