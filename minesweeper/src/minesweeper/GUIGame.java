package minesweeper;

import java.util.List;

public class GUIGame extends NormalGame {


    public GUIGame(int Width, int Height, int NumMines, List ListOfPlayers) {// Constructor
        for(Object curPlayer:ListOfPlayers) {// add Players To the Game
            this.AddPlayer((Player) curPlayer);
        }
        initGame(Width,Height,NumMines);
    }
    @Override
    public void StartGame() {
        UpdateVeiw();
    }

    @Override
    void GetMove() {

    }

    @Override
    void EndGame() {

    }

    @Override
    void UpdateVeiw() {

    }
}
