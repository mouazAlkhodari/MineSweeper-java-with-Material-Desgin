/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.ArrayList;

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
    ArrayList players;
    Player currentPlayer;
    ArrayList moves;
    GameRules currentRules;
    Grid grid;
    GameStatus status;
    public void initGame(int width,int height,int minesCount){
        currentPlayer = (Player)players.get(0);
        this.status=GameStatus.Running;// need to change to begin game
        grid = new Grid(width,height,minesCount);
        printGrid();
        GetMove();
    }

    protected abstract void printGrid();

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
                printGrid();
                GetMove();
            }
        }
    }

    protected abstract void Lose();

    protected abstract void Win();

    public boolean AcceptMove(PlayerMove move){
    };
    public void ApplyPlayerMove(PlayerMove move){};
    public void AddPlayer(Player player)
    {
        players.add(player);
    }
}
