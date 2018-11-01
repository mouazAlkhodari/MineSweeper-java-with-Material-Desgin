package minesweeper;

import java.util.ArrayList;
import java.util.List;

public abstract class NormalGame extends Game{


    class DefaultRules extends GameRules{
        int GetScoreChange(ArrayList moves){

            return 0;
        }
        Player DecideNextPlayer(ArrayList moves){
            int indOfcurrentPlayer=players.lastIndexOf(currentPlayer);
            for(int idx=indOfcurrentPlayer,i=0;i<players.size();i++){
                idx+=(idx+1)%players.size();
                if(players.get(idx).getCurrentStatus()!=PlayerStatus.Lose){
                    return players.get(i);
                }
            }
            return currentPlayer;
        }
    }


    public void ApplyPlayerMove(PlayerMove move) {
        // here We ApPly The move And then Check The Status Of The Game
        moves =this.grid.AcceptMove(move);

        int ScoreChange=currentRules.GetScoreChange(moves);
        currentPlayer.addScore(ScoreChange);
        // need To be Func
            if(moves.get(0).getSquare().getStatus()==SquareStatus.OpenedMine) {
                if (LoseWhenHitMine) {
                    currentPlayer.setCurrentStatus(PlayerStatus.Lose);
                } else if (currentPlayer.getCurrentScore() < 0) {
                    currentPlayer.setCurrentStatus(PlayerStatus.Lose);
                }
            }
        ChangeStatus();
        currentPlayer=currentRules.DecideNextPlayer(moves);

    }
    public void ChangeStatus(){
        Square[][] feild =this.grid.getField();
        int num=0;
        for(int i=1;i<this.grid.getWidth();i++){
            for(int j=1;j<this.grid.getHeight();j++){
                switch (feild[i][j].getStatus()){
                    case Marked:
                    case Closed:
                        num++;
                        break;
                }
            }
        }
        boolean CanContinue=false;
        for(int i=0;i<players.size();i++){
            if(players.get(i).getCurrentStatus()!=PlayerStatus.Lose){
                CanContinue=true;
            }
        }
        if(num==this.grid.getMinesCount() || !CanContinue){
            status=GameStatus.Finish;
        }
        else{
            status=GameStatus.Running;
        }
    }
}
