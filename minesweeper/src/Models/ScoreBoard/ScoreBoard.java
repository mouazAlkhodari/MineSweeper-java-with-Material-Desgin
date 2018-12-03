package Models.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {
    protected List<PlayerBoard> scoreboard = new ArrayList<PlayerBoard>();

    public void AddPlayer(PlayerBoard _player) {
        scoreboard.add(_player);
    }

}
