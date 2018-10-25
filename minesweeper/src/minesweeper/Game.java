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
            
        }
        public Player DecideNextPlayer(ArrayList moves)
        {}
    }
    ArrayList players;
    Player currentPlayer;
    ArrayList moves;
    GameRules currentRules;
    public void initGame(){};
    public boolean AcceptMove(PlayerMove move){};
    public void ApplyPlayerMove(PlayerMove move){};
}
