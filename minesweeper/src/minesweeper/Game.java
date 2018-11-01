/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.List;

import CustomSequences.SurroundingMines2DArray;

/**
 *
 * @author Omar
 */
enum GameStatus{
    Running,Finish
}

public abstract class Game {

    // <__ INNER CLASS __> \\
    abstract class GameRules{
        abstract int GetScoreChange(ArrayList moves);
        abstract Player DecideNextPlayer(ArrayList moves);
    }

    // <__ DATA MEMBERS __> \\
    Player currentPlayer;
    Grid grid;
    GameStatus status;
    private List<Player> players=new ArrayList<Player>();
    private ArrayList moves;
    private GameRules currentRules;

    // <__ METHODS __> \\
    public void initGame(int width,int height,int minesCount){
        currentPlayer = (Player)players.get(0);
        this.status=GameStatus.Running;// need to change to New Start game
        grid = new Grid(width,height,minesCount);
    }
    boolean AcceptMove(PlayerMove move){// x Rows Y columns
        Square s = move.getSquare();
        if(SurroundingMines2DArray.CheckIndex(s.getX(),s.getY(),grid.getWidth(),grid.getHeight()))
        {
            if(s.status == SquareStatus.Closed)
            {
                return true;
            }
            if(move.getType() == MoveType.Mark && s.status == SquareStatus.Marked)
            {
              return true;
            }
        }
        return false;
    }
    void AddPlayer(Player player)
    {
        players.add(player);
    }

    // <__ SETTERS-GETTERS __> \\
    //Setters
    void setStatus(GameStatus status) {
        this.status = status;
    }
    //Getters
    public GameStatus getStatus() {
        return status;
    }


    //This func Implement in each kind of game Like Console Or GUI...
    public abstract void StartGame();
    abstract void GetMove();
    abstract void EndGame();
    abstract void UpdateVeiw();

    // This Func Is implement in Normal Game Class Or any class That Extend This Class Immedialtly
    abstract void ApplyPlayerMove(PlayerMove move);


}
