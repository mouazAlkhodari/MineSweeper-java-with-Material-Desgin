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
    public NormalGame(int Width,int Height,int NumMines,List ListOfPlayers,
                      int RevealFloodFill,
                              int RevealEmpty,
                              int RevealMine,
                              int MarkMine,
                              int MarkNotMine,
                              int Unmarkmine,
                              int UnmarkNotMine,
                              int LastNumber){
        super(Width,Height,NumMines,ListOfPlayers);
        currentRules=new NormalGame.DefaultRules(RevealFloodFill,RevealEmpty,RevealMine,MarkMine,MarkNotMine,Unmarkmine,UnmarkNotMine,LastNumber);
    }


    // InnerClass
    // #see GameRules In Game Class
    protected class DefaultRules extends GameRules {
        public Points points;
        class Points {
            int RevealFloodFill;
            int RevealEmpty;
            int RevealMine;
            int MarkMine;
            int MarkNotMine;
            int Unmarkmine;
            int UnmarkNotMine;
            int LastNumber;

            public Points() { this(1,10,-250,5,-1,-5,1,0); }

            public Points(int revealFloodFill, int revealEmpty, int revealMine, int markMine, int markNotMine, int unmarkmine, int unmarkNotMine, int lastNumber) {
                RevealFloodFill = revealFloodFill;
                RevealEmpty = revealEmpty;
                RevealMine = revealMine;
                MarkMine = markMine;
                MarkNotMine = markNotMine;
                Unmarkmine = unmarkmine;
                UnmarkNotMine = unmarkNotMine;
                LastNumber = lastNumber;
            }

            public void addRevealEmptyPoints() { currentPlayer.getCurrentScore().addPoints(RevealEmpty); }
            public void addRevealMinePoints() { currentPlayer.getCurrentScore().addPoints(RevealMine); }
            public void addMarkMinePoints() { currentPlayer.getCurrentScore().addPoints(MarkMine); }
            public void addMarkNotMinePoints() { currentPlayer.getCurrentScore().addPoints(MarkNotMine); }
            public void addUnmarkMinePoints() { currentPlayer.getCurrentScore().addPoints(Unmarkmine); }
            public void addUnmarkNotMinePoints() { currentPlayer.getCurrentScore().addPoints(UnmarkNotMine); }
            public void addRevealEmptyPontis() { currentPlayer.getCurrentScore().addPoints(RevealEmpty); }
            public void addRevealFloodFill(int SquaresNumber) { currentPlayer.getCurrentScore().addPoints(RevealFloodFill*(SquaresNumber - 1) + RevealEmpty); }
        }
        WhenHitMine PressMineBehavior;

        public DefaultRules() {
            PressMineBehavior = WhenHitMine.Lose;
            points = new Points();
        }
        public DefaultRules(int RevealFloodFill,
                            int RevealEmpty,
                            int RevealMine,
                            int MarkMine,
                            int MarkNotMine,
                            int Unmarkmine,
                            int UnmarkNotMine,
                            int LastNumber){
            PressMineBehavior=WhenHitMine.Lose;
            points=new Points(RevealFloodFill,RevealEmpty,RevealMine,MarkMine,MarkNotMine,Unmarkmine,UnmarkNotMine,LastNumber);
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
                        if(PressMineBehavior == WhenHitMine.Continue)
                            points.addRevealMinePoints();
                        break;
                    case Marked:
//                        move.getSquare().isMine() ? points.addMarkMinePoints() : points.addMarkNotMinePoints();
                        if (move.getSquare().isMine()) { points.addMarkMinePoints(); }
                        else { points.addMarkNotMinePoints(); }
                        break;
                    case Closed: // This case is when user unmark marked sqaure so it will be closed;
                        if(move.getSquare().isMine()){ points.addUnmarkMinePoints(); }
                        else{ points.addUnmarkNotMinePoints(); }
                        break;
                }
                return;
            }
            else {//In this case .. More than one sqaure revealed so >>
                points.addRevealFloodFill(moves.size());
            }
        }

        @Override
        Player DecideNextPlayer(List<PlayerMove> moves) {
            int indOfcurrentPlayer = players.lastIndexOf(currentPlayer);
            for (int i = 0; i < players.size(); i++) {
                indOfcurrentPlayer = (indOfcurrentPlayer + 1) % players.size();
                if (players.get(indOfcurrentPlayer).getCurrentStatus() == PlayerStatus.waiting) {
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
