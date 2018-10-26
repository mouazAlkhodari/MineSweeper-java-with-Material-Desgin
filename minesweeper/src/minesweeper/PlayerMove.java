package minesweeper;

enum MoveType{Reveal,Mark}
public class PlayerMove {
    Player player;
    Square square;
    MoveType type;
    MoveResult result;

    public PlayerMove(Player _player,Square _square,MoveType _type,MoveResult _result) {
        this.player= _player;
        this.square=_square;
        this.type=_type;
        this.result=_result;
    }
}
