package minesweeper;

import java.util.ArrayList;
import java.util.List;

public abstract class NormalGame extends Game {
    class DefaultRules extends GameRules {
        WhenHitMine PressMineBehavior;
        Points GamePoints;

        public DefaultRules() {
            PressMineBehavior = WhenHitMine.Lose;
            GamePoints = new Points();
        }

        void ChangePlayerStatus(List<PlayerMove> moves) {
            if (moves.get(0).getSquare().getStatus() == SquareStatus.OpenedMine)
                if (PressMineBehavior == WhenHitMine.Lose || (PressMineBehavior == WhenHitMine.Continue && currentPlayer.getCurrentScore() < 0))
                    currentPlayer.setCurrentStatus(PlayerStatus.Lose);
        }

        int GetScoreChange(List<PlayerMove> moves) {
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

        @Override
        Player DecideNextPlayer(List<PlayerMove> moves) {
            int indOfcurrentPlayer = players.lastIndexOf(currentPlayer);
            for (int i = 0; i < players.size(); i++) {
                indOfcurrentPlayer = (indOfcurrentPlayer + 1) % players.size();
                if (players.get(indOfcurrentPlayer).getCurrentStatus() != PlayerStatus.Lose) {
                    return players.get(i);
                }
            }
            return currentPlayer;
        }
    }
    public NormalGame(){
        currentRules=new DefaultRules();
    }
    public abstract void GetMove();
    protected abstract void UpdateVeiw();
    protected abstract void EndGame();
}
