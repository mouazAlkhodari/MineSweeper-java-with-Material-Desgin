package GUIGame;

import Models.Game.Points;
import Models.Game.WhenHitMine;
import Models.Game.WhenScoreNegative;
import Models.Grid.Square;
import Models.Grid.SquareStatus;
import Models.Move.PlayerMove;
import Models.Player.PlayerStatus;

import java.util.List;

public class GUIGameCustom extends GUIGame {
    class CustomRules extends DefaultRules{
        public CustomRules() {
            super();
        }
        public CustomRules(Points _points, WhenHitMine pressMineBehavior, WhenScoreNegative scoreNegativeBehavior) {
            super(_points, pressMineBehavior, scoreNegativeBehavior);
        }
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
            super.GetScoreChange(moves);
            for(PlayerMove currentMove:moves){
                Square currentSquare=currentMove.getSquare();
                if(currentSquare.isMine()){
                    currentPlayer.addNormalshild(-1);

                }
            }
        }
    };

    public GUIGameCustom(List _players) {
        super(_players);
        currentRules=new CustomRules();
    }

    public GUIGameCustom(int Width, int Height, int NumMines, List ListOfPlayers) {
        super(Width, Height, NumMines, ListOfPlayers);
        currentRules=new CustomRules();
    }

    public GUIGameCustom(int Width, int Height, int NumMines, List _players, Points points, WhenHitMine pressMineBehavior, WhenScoreNegative scoreNegativeBehavior) {
        super(Width, Height, NumMines, _players, points, pressMineBehavior, scoreNegativeBehavior);
        currentRules=new CustomRules(points,pressMineBehavior,scoreNegativeBehavior);

    }

}
