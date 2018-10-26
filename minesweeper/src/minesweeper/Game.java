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
public class Game {
    class GameRules{
        public int GetScoreChange(ArrayList moves)
        {

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
    public void initGame(int width,int height,int minesCount){
        currentPlayer = (Player)players.get(0);
        grid = new Grid(width,height,minesCount);
    };
    public void GetMove(){
        PlayerMove move = currentPlayer.GetPlayerMove();
        if(AcceptMove(move))
            ApplyPlayerMove(move);
        
    }
    public boolean AcceptMove(PlayerMove move){
        return false;   
    };
    public void ApplyPlayerMove(PlayerMove move){};
    public void AddPlayer(Player player)
    {
        players.add(player);
    }
}
