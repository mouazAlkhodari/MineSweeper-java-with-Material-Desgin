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
    Win,Lose,Running
}
public abstract class Game {

    class GameRules{//inner Class
        public int GetScoreChange(ArrayList moves){
            return 0;
        }
        public Player DecideNextPlayer(ArrayList moves)
        {
            return null;
        }
    }
    Player currentPlayer;
    Grid grid;
    GameStatus status;

    private List<Player> players=new ArrayList<Player>();
    private ArrayList moves;
    private GameRules currentRules;

    public void initGame(int width,int height,int minesCount){
        currentPlayer = (Player)players.get(0);
        this.status=GameStatus.Running;// need to change to New Start game
        grid = new Grid(width,height,minesCount);
    }
    public void StartGame(){
        UpdateVeiw();
        GetMove();
    }

    boolean AcceptMove(PlayerMove move){// x Rows Y columns
        Square s = move.getSquare();
        if(SurroundingMines2DArray.CheckIndex(s.getX(),s.getY()))
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


    //This func Implement in each kind of game Like Console Or GUI...
    abstract void GetMove();
    abstract void Lose();
    abstract void Win();
    abstract void UpdateVeiw();

    // This Func Is implement in Normal Game Class Or any class That Extend This Class Immedialtly
    abstract void ApplyPlayerMove(PlayerMove move);

    void AddPlayer(Player player)
    {
        players.add(player);
    }
    void setStatus(GameStatus status) {
        this.status = status;
    }
    public GameStatus getStatus() {
        return status;
    }
}
