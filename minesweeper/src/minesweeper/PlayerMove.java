package minesweeper;

enum MoveType{Reveal,Mark}
public class PlayerMove {
    Player player;
    Square square;
    MoveType type;
    MoveResult result;
<<<<<<< HEAD
}   
=======

    public PlayerMove(Player _player,Square _square,MoveType _type,MoveResult _result) {
        player=_player;square=_square;type=_type;result=_result;
    }
}
>>>>>>> ef7725448ae480293566145dce5f111d59cf1dbe
