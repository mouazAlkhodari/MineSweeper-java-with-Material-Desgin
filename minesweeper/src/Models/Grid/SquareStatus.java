package Models.Grid;

import java.io.Serializable;

public enum SquareStatus implements Serializable {
    Closed,OpenedEmpty,OpenedNumber,OpenedMine,Marked;
}

