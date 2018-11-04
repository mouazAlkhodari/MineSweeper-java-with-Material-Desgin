package Models.Game;

import Models.Grid.SquareStatus;
import Models.Player.Player;
import Models.Move.PlayerMove;
import Models.Player.PlayerStatus;

import java.util.List;

public abstract class NormalGame extends Game {


    // Constructors
    public NormalGame(List ListOfPlayers){
        super(ListOfPlayers);
        currentRules=new DefaultRules();
    }
    public NormalGame(int Width,int Height,int NumMines,List ListOfPlayers){
        super(Width,Height,NumMines,ListOfPlayers);
        currentRules=new NormalGame.DefaultRules();
    }


    // InnerClass
    // #see GameRules In Game Class
    class DefaultRules extends GameRules {
        public Points points;
        class Points {
            int RevealFloodFill;
            int RevealEmpty;
            int RevealMine;
            int MarkMine;
            int MarkNotMine;
            int Unmark;
            int LastNumber;

            public Points(){
                this(1,10,-250,5,-1,-1,0);
            }

            public Points(int revealFloodFill, int revealEmpty, int revealMine, int markMine, int markNotMine, int unmark, int lastNumber) {
                RevealFloodFill = revealFloodFill;
                RevealEmpty = revealEmpty;
                RevealMine = revealMine;
                MarkMine = markMine;
                MarkNotMine = markNotMine;
                Unmark = unmark;
                LastNumber = lastNumber;
            }

            public void addRevealEmptyPoints() { currentPlayer.getCurrentScore().addPoints(RevealEmpty); }
            public void addRevealMinePoints() { currentPlayer.getCurrentScore().addPoints(RevealMine); }
            public void addMarkMinePoints() { currentPlayer.getCurrentScore().addPoints(MarkMine); }
            public void addMarkNotMinePoints() { currentPlayer.getCurrentScore().addPoints(MarkNotMine); }
            public void addUnmarkPoints() { currentPlayer.getCurrentScore().addPoints(Unmark); }
            public void addRevealEmptyPontis() { currentPlayer.getCurrentScore().addPoints(RevealEmpty); }
            public void addRevealFloodFill(int SquaresNumber) { currentPlayer.getCurrentScore().addPoints(RevealFloodFill*(SquaresNumber - 1) + RevealEmpty); }
        }

        WhenHitMine PressMineBehavior;
        public DefaultRules() {
            PressMineBehavior = WhenHitMine.Lose;
            points = new Points();
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
                        points.addRevealEmptyPoints();
                        break;
                    case OpenedNumber:
                        currentPlayer.getCurrentScore().addPoints(move.getSquare().getNumberOfSurroundedMines());
                        break;
                    case OpenedMine:
                        points.addRevealMinePoints();
                        break;
                    case Marked:
//                        move.getSquare().isMine() ? points.addMarkMinePoints() : points.addMarkNotMinePoints();
                        if (move.getSquare().isMine()) { points.addMarkMinePoints(); }
                        else { points.addMarkNotMinePoints(); }
                        break;
                    case Closed: // This case is when user unmark marked sqaure so it will be closed;
                        points.addUnmarkPoints();
                        break;
                }
                return;
            }
            //In this case .. More than one sqaure revealed so >>
            points.addRevealFloodFill(moves.size());
        }

        @Override
        Player DecideNextPlayer(List<PlayerMove> moves) {
            int indOfcurrentPlayer = players.lastIndexOf(currentPlayer);
            for (int i = 0; i < players.size(); i++) {
                indOfcurrentPlayer = (indOfcurrentPlayer + 1) % players.size();
                if (players.get(indOfcurrentPlayer).getCurrentStatus() != PlayerStatus.Lose) {
                    return players.get(indOfcurrentPlayer);
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
