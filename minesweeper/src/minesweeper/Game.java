/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Omar
 */
enum GameStatus{
    Win,Lose,Running
}
public abstract class Game {

    class GameRules{
        public int GetScoreChange(ArrayList moves){
            return 0;
        }

        public Player DecideNextPlayer(ArrayList moves)
        {
            return null;
        }
    }
    private List<Player> players=new ArrayList<Player>();
    private Player currentPlayer;
    private ArrayList moves;
    private GameRules currentRules;
    protected Grid grid;
    private GameStatus status;
    public void initGame(int width,int height,int minesCount){
        currentPlayer = (Player)players.get(0);
        this.status=GameStatus.Running;// need to change to begin game
        grid = new Grid(width,height,minesCount);
    }
    public void StartGame(){
        UpdateVeiw();
        GetMove();
    }

    protected abstract void UpdateVeiw();

    ;
    public void GetMove(){
        PlayerMove move = currentPlayer.GetPlayerMove();
        if(AcceptMove(move)){
            ApplyPlayerMove(move);
            if(this.status==GameStatus.Win){
                Win();
            }
            else if(this.status==GameStatus.Lose){
                Lose();
            }
            else{
                UpdateVeiw();
                GetMove();
            }
        }
    }

    protected abstract void Lose();

    protected abstract void Win();

    public boolean AcceptMove(PlayerMove move){// x Rows Y columns
        Square s = move.getSquare();
        if(s.getX() <= grid.getHeight() && s.getY() <= grid.getWidth())
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
    };
    public void ApplyPlayerMove(PlayerMove move){};
    public void AddPlayer(Player player)
    {
        players.add(player);
    }
}
