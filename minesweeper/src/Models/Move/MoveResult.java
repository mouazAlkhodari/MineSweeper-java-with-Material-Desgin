package Models.Move;

import Models.Grid.SquareStatus;

import java.io.Serializable;

public class MoveResult implements Serializable {
    private int ScoreChange;
    private SquareStatus newStatus;
}
