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
        currentRules=new DefaultRules();
    }
    public NormalGame(int width, int height, int numMines, List players,
                      Points points, WhenHitMine pressMineBehavior, WhenScoreNegative scoreNegativeBehavior) {
        super(width, height, numMines, players);
        currentRules=new DefaultRules(points,pressMineBehavior,scoreNegativeBehavior);
    }



    // InnerClass
    // #see GameRules In Game Class
    protected class DefaultRules extends GameRules {
        public Points points;
        protected WhenHitMine PressMineBehavior;
        protected WhenScoreNegative ScoreNegativeBehavior;
        public DefaultRules() {
            PressMineBehavior = WhenHitMine.Lose;
            ScoreNegativeBehavior=WhenScoreNegative.End;
            points = new Points();
        }
        public DefaultRules(WhenHitMine pressMineBehavior, WhenScoreNegative scoreNegativeBehavior) {
            PressMineBehavior = pressMineBehavior;
            ScoreNegativeBehavior=scoreNegativeBehavior;
            points = new Points();
        }
        public DefaultRules(Points _points, WhenHitMine pressMineBehavior, WhenScoreNegative scoreNegativeBehavior) {
            points=_points;
            PressMineBehavior=pressMineBehavior;
            ScoreNegativeBehavior=scoreNegativeBehavior;

        }//EndOfClass

        protected void ChangePlayerStatus(List<PlayerMove> moves) {
            if (moves.get(0).getSquare().getStatus() == SquareStatus.OpenedMine && PressMineBehavior == WhenHitMine.Lose){
                currentPlayer.setCurrentStatus(PlayerStatus.Lose);
                return;
            }
            if( currentPlayer.getCurrentScore().getScore() < 0
                    && PressMineBehavior == WhenHitMine.Continue
                    && ScoreNegativeBehavior ==WhenScoreNegative.End){
                currentPlayer.setCurrentStatus(PlayerStatus.Lose);
                return;
            }
            currentPlayer.setCurrentStatus(PlayerStatus.waiting);
        }

        protected void GetScoreChange(List<PlayerMove> moves) {
            if (moves.size() == 1) {
                PlayerMove move = moves.get(0);
                switch (move.getSquare().getStatus()) {
                    case OpenedEmpty:
                        points.addRevealEmptyPoints(currentPlayer);
                        break;
                    case OpenedNumber:
                        currentPlayer.getCurrentScore().addPoints(move.getSquare().getNumberOfSurroundedMines());
                        break;
                    case OpenedMine:
                            points.addRevealMinePoints(currentPlayer);
                        break;
                    case Marked:
//                        move.getSquare().isMine() ? points.addMarkMinePoints() : points.addMarkNotMinePoints();
                        if (move.getSquare().isMine()) { points.addMarkMinePoints(currentPlayer); }
                        else { points.addMarkNotMinePoints(currentPlayer); }
                        break;
                    case Closed: // This case is when user unmark marked sqaure so it will be closed;
                        if(move.getSquare().isMine()){ points.addUnmarkMinePoints(currentPlayer); }
                        else{ points.addUnmarkNotMinePoints(currentPlayer); }
                        break;
                }
                return;
            }
            else {//In this case .. More than one sqaure revealed so >>
                points.addRevealFloodFill(currentPlayer,moves.size());
            }
        }

        @Override
        protected Player DecideNextPlayer(List<PlayerMove> moves) {
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
    protected abstract void UpdateVeiw(List<PlayerMove> Moves);
    protected abstract void EndGame();
}
