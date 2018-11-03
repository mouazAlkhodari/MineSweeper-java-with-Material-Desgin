package minesweeper;

public class PlayerMove {
    // <__ DATA MEMBERS__> \\
    private Player player;
    private Square square;
    private MoveType type;
    private MoveResult result;

    // <__ CONSTRUCTER __> \\
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
}
