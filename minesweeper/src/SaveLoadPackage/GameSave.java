package SaveLoadPackage;

import GUIGame.GUIGame;
import Models.Game.*;
import Models.Grid.Grid;
import Models.Move.PlayerMove;
import Models.Player.Player;

import java.awt.*;
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
    public int gameTime;
    Points points;
    WhenHitMine whenHitMineBehavior;
    WhenScoreNegative whenScoreNegativeBehavior;

    // Constructor //
    public GameSave(GUIGame game) {
        this.grid = game.getGrid();
        this.currentPlayer = game.getCurrentPlayer();
        this.moves = game.getGameMoves();
        this.status = game.getGameStatus();
        this.flagsNumber = game.getFlagsNumber();
        this.shieldsNumber = game.getShieldsNumber();
        this.players = game.getPlayers();
        this.gameTime = game.getGameTime();
        this.points = game.getPoints();
        this.whenHitMineBehavior = game.getWhenHitMine();
        this.whenScoreNegativeBehavior = game.getWhenScoreNegative();

    }
}
