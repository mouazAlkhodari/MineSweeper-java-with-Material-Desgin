package Models.Move;

import ConsoleGame.ConsolePlayer;
import Models.Grid.Square;
import Models.Player.DumbPlayer;
import Models.Player.Player;

import java.io.Serializable;

public class PlayerMove implements Serializable {
    // <__ DATA MEMBERS__> \\
    private Player player;
    private Square square;
    private MoveType type;


    private MoveResult result;

    public double getEndTimeMove() {
        return endTimeMove;
    }

    public void setEndTimeMove(double endTimeMove) {
        this.endTimeMove = endTimeMove;
    }

    double endTimeMove=0;
    // <__ CONSTRUCTER __> \\
    public PlayerMove(){this(new ConsolePlayer(),new Square(),MoveType.Reveal,new MoveResult());}
    public PlayerMove(Player _player){ this(_player,new Square(),MoveType.Reveal,new MoveResult()); }
    public PlayerMove(Player _player,Square _sqaure){ this(_player,_sqaure,MoveType.Reveal,new MoveResult()); }
    public PlayerMove(Player _player,Square _sqaure,MoveType _type){
        this(_player,_sqaure,_type,new MoveResult());
    }
    public PlayerMove(Player _player,Square _square,MoveType _type,MoveResult _result) {
        this.player= _player;
        this.square=_square;
        this.type=_type;
        this.result=_result;
    }

    // <__ SETTERS-GETTERS __> \\
    //setter
    public void setSquare(Square square) {
        this.square = square;
    }
    // Getters
    public Square getSquare() { return square; }
    public MoveType getType() {
        return type;
    }
    public Player getPlayer() { return player; }
    public MoveResult getResult() {
        return result;
    }
    public void setType(MoveType type) {
        this.type = type;
    }
}
