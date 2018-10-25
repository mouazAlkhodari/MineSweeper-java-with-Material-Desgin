package minesweeper;

enum MoveType{Reveal,mark}
public class PlayerMove {
    Player player;
    Square square;
    MoveType type;
    MoveResult result;

    public PlayerMove(Player _player,Square _square,MoveType _type,MoveResult _result) {
        player=_player;square=_square;type=_type;result=_result;
    }
}
