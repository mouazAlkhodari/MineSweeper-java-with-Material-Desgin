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
    public void initGame(){};
    public abstract boolean AcceptMove(PlayerMove move);
    public abstract void ApplyPlayerMove(PlayerMove move);
}
