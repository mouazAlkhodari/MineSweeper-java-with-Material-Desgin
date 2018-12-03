package GUIGame;

import Models.Game.*;
import Models.Grid.*;
import Models.Move.*;
import Models.Player.*;

import java.util.List;

public class GUIGameCustom extends GUIGame {

    class CustomRules extends DefaultRules {
        public CustomRules() {
            super();
        }
        public CustomRules(WhenHitMine pressMineBehavior, WhenScoreNegative scoreNegativeBehavior) {
            super(pressMineBehavior,scoreNegativeBehavior);
        }
        public CustomRules(Points _points, WhenHitMine pressMineBehavior, WhenScoreNegative scoreNegativeBehavior) {
            super(_points, pressMineBehavior, scoreNegativeBehavior);
        }
        protected void ChangePlayerStatus(List<PlayerMove> moves) {
            if(moves.size()==0){
                if(status != GameStatus.Finish)
                    currentPlayer.setCurrentStatus(PlayerStatus.waiting);
                return;
            }
            if(moves.get(0).getType()== MoveType.Reveal) {
                if (PressMineBehavior == WhenHitMine.Lose && currentPlayer.getNumberOfShield() < 0) {
                    currentPlayer.setCurrentStatus(PlayerStatus.Lose);
                    return;
                }
                if (currentPlayer.getCurrentScore().getScore() < 0
                        && PressMineBehavior == WhenHitMine.Continue
                        && ScoreNegativeBehavior == WhenScoreNegative.End) {
                    currentPlayer.setCurrentStatus(PlayerStatus.Lose);
                    return;
                }
            }
            currentPlayer.setCurrentStatus(PlayerStatus.waiting);
        }
        protected void GetScoreChange(List<PlayerMove> moves) {
            if(moves.size()==0){
                if(status==GameStatus.Finish)
                    points.addHasNormalShieldPointPoints(currentPlayer);
                return;
            }
            super.GetScoreChange(moves);
            if(moves.get(0).getType()==MoveType.Reveal) {
                for (PlayerMove currentMove : moves) {
                    Square currentSquare = currentMove.getSquare();
                    if (currentSquare.isMine() && currentPlayer.getNumberOfShield() >= 0) {
                        currentPlayer.addNormalshild(-1);
                        if (currentPlayer.getNumberOfShield() >= 0 && PressMineBehavior!= WhenHitMine.Lose)
                            points.addLostNormalShieldPoints(currentPlayer);
                    } else if (currentSquare.hasNormalSield()) {
                        currentPlayer.addNormalshild((1));
                    } else if (currentSquare.hasHeroSield()) {
                        // Todo: add number Of Normal Shield To Points and Player
                    }
                }
            }
        }


    }

    public GUIGameCustom(int NumOfShield,List _players) {
        super(_players);
        currentRules=new CustomRules();
        ShildNumber=NumOfShield;
    }

    public GUIGameCustom(int Width, int Height, int NumMines,int NumOfShield, List ListOfPlayers) {
        super(Width, Height, NumMines,ListOfPlayers);
        ShildNumber=NumOfShield;
        currentRules=new CustomRules();
    }
    public GUIGameCustom(int Width, int Height, int NumMines,int NumOfShield, List ListOfPlayers, WhenHitMine pressMineBehavior, WhenScoreNegative scoreNegativeBehavior) {
        super(Width, Height, NumMines,ListOfPlayers);
        ShildNumber=NumOfShield;
        currentRules=new CustomRules(pressMineBehavior,scoreNegativeBehavior);
    }

    public GUIGameCustom(int Width, int Height, int NumMines,int NumOfShield, List _players, Points points, WhenHitMine pressMineBehavior, WhenScoreNegative scoreNegativeBehavior) {
        super(Width, Height, NumMines, _players, points, pressMineBehavior, scoreNegativeBehavior);
        currentRules=new CustomRules(points,pressMineBehavior,scoreNegativeBehavior);
        ShildNumber=NumOfShield;
    }

    /*@Override
    protected void initScene() {
        super.initScene();
    }

    @Override
    protected void initScoreBoard() {
        super.initScoreBoard();
    }*/
}
