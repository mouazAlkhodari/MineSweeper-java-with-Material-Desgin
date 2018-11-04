package minesweeper;

import java.util.List;

public abstract class NormalGame extends Game {


    // Constructors
    public NormalGame(List ListOfPlayers){
        super(ListOfPlayers);
        currentRules=new NormalGame.DefaultRules();
    }
    public NormalGame(int Width,int Height,int NumMines,List ListOfPlayers){
        super(Width,Height,NumMines,ListOfPlayers);
        currentRules=new NormalGame.DefaultRules();
    }


    // InnerClass
    // #see GameRules In Game Class
    class DefaultRules extends GameRules {
        WhenHitMine PressMineBehavior;

        public DefaultRules() {
            PressMineBehavior = WhenHitMine.Lose;
        }

        void ChangePlayerStatus(List<PlayerMove> moves) {
            if (moves.get(0).getSquare().getStatus() == SquareStatus.OpenedMine) {
                if (PressMineBehavior == WhenHitMine.Lose || (PressMineBehavior == WhenHitMine.Continue && currentPlayer.getCurrentScore().getScore() < 0))
                    currentPlayer.setCurrentStatus(PlayerStatus.Lose);
                else {
                    currentPlayer.setCurrentStatus(PlayerStatus.waiting);
                }
            }
            else{
                currentPlayer.setCurrentStatus(PlayerStatus.waiting);
            }
        }

        void GetScoreChange(List<PlayerMove> moves) {
            if (moves.size() == 1) {
                PlayerMove move = moves.get(0);
                switch (move.getSquare().getStatus()) {
                    case OpenedEmpty:
                        currentPlayer.getCurrentScore().addRevealEmptyPoints();
                        break;
                    case OpenedNumber:
                        currentPlayer.getCurrentScore().addPoints(move.getSquare().getNumberOfSurroundedMines());
                        break;
                    case OpenedMine:
                        currentPlayer.getCurrentScore().addRevealMinePoints();
                        break;
                    case Marked:
                        if (move.getSquare().isMine()) {
                            currentPlayer.getCurrentScore().addMarkMinePoints();
                        } else {
                            currentPlayer.getCurrentScore().addMarkNotMinePoints();
                        }
                        break;
                    case Closed: // This case is when user unmark marked sqaure so it will be closed;
                        currentPlayer.getCurrentScore().addUnmarkPoints();
                        break;
                }
                return;
            }
            //In this case .. More than one sqaure revealed so >>
            currentPlayer.getCurrentScore().addRevealEmptyPontis();
            currentPlayer.getCurrentScore().addRevealFloodFill(moves.size() -1);
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


    @Override
    public abstract void GetMove();
    protected abstract void UpdateVeiw();
    protected abstract void EndGame();
}
