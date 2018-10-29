package minesweeper;

enum MoveType{Reveal,Mark}
public class PlayerMove {

    private Player player;
    private Square square;
    private MoveType type;
    private MoveResult result;

    public PlayerMove(Player _player,Square _square,MoveType _type,MoveResult _result) {
        this.player= _player;
        this.square=_square;
        this.type=_type;
        this.result=_result;
    }
    //setter
    public void setSquare(Square square) {
        this.square = square;
    }

    //getter
    public Square getSquare() {
        return square;
    }
    public MoveType getType() {
        return type;
    }
    public Player getPlayer() {
        return player;
    }
    public MoveResult getResult() {
        return result;
    }
}
