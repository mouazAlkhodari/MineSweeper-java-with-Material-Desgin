package minesweeper;

import java.util.ArrayList;
import java.util.List;

public abstract class NormalGame extends Game{


    class DefaultRules extends GameRules{
        int GetScoreChange(ArrayList moves){

            return 0;
        }
        Player DecideNextPlayer(ArrayList moves){
        }
    }


    public void ApplyPlayerMove(PlayerMove move) {
        // here We ApPly The move And then Check The Status Of The Game
        moves =this.grid.AcceptMove(move);
        Square[][] feild=this.grid.getField();
        int ScoreChange=currentRules.GetScoreChange(moves);
        currentPlayer.addScore(ScoreChange);
        currentPlayer=currentRules.DecideNextPlayer(moves);

    }
}
