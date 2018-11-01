package minesweeper;

import java.util.ArrayList;

enum WhenHitMine {
    Lose,Continue;
}

public abstract class NormalGame extends Game{
    class Points {
        int RevealFloodFill;
        int RevealEmpty;
        int RevealMine;
        int MarkMine;
        int MarkNotMine;
        int Unmark;
        int LastNumber;

        public Points() {
            RevealFloodFill = 1;
            RevealEmpty = 10;
            RevealMine = -250;
            MarkMine = 5;
            MarkNotMine = -1;
            Unmark = -1;
        }
    }

    class DefaultRules extends GameRules{
        WhenHitMine PressMineBehavior;
        Points GamePoints;

        void DefaultRules() {
            PressMineBehavior = WhenHitMine.Lose;
            GamePoints = new Points();
        }



        void ChangePlayerStatus(ArrayList<PlayerMove> moves) {

        }

        int GetScoreChange(ArrayList<PlayerMove> moves){
            if (moves.size() == 1) {
                PlayerMove move = moves.get(0);
                switch (move.getSquare().getStatus()) {
                    case OpenedEmpty:
                        return GamePoints.RevealEmpty;
                    case OpenedNumber:
                        return move.getSquare().getNumberOfSurroundedMines();
                    case OpenedMine:
                        return GamePoints.RevealMine;
                    case Marked:
                        return move.getSquare().isMine() ? GamePoints.MarkMine : GamePoints.MarkNotMine;
                    case Closed: // This case is when user unmark marked sqaure so it will be closed;
                        return GamePoints.Unmark;
                }
            }
            //In this case .. More than one sqaure revealed so >>
            return GamePoints.RevealEmpty + GamePoints.RevealFloodFill * (moves.size() - 1);
        }
        Player DecideNextPlayer(ArrayList moves){
            return null;
        }
    }


    public void ApplyPlayerMove(PlayerMove move) {
        // here We ApPly The move And then Check The Status Of The Game
        moves = this.grid.AcceptMove(move);
        Square[][] feild=this.grid.getField();
        int ScoreChange=currentRules.GetScoreChange(moves);
        currentPlayer.addScore(ScoreChange);
        currentPlayer=currentRules.DecideNextPlayer(moves);

    }
}
