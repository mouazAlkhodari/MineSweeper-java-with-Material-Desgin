package minesweeper;

enum MoveType{Reveal,mark}
public class PlayerMove {
    Player player;
    Square square;
    MoveType type;
    MoveResult result;
}
