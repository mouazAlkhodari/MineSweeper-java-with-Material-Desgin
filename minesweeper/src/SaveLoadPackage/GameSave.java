package SaveLoadPackage;

import Models.Game.GameStatus;
import Models.Grid.Grid;
import Models.Move.PlayerMove;
import Models.Player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameSave implements Serializable {

    public Player currentPlayer;
    public Grid grid;
    public GameStatus status;
    public List<Player> players = new ArrayList<Player>();
    public List<PlayerMove> moves = new ArrayList<PlayerMove>();
    public int flagsNumber;
    public int shieldsNumber;
}
