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
public abstract class NormalGame extends Game{
    class DefaultRules extends GameRules{
        
        public int GetScoreChange(ArrayList moves){
            return 0;
        }
        public Player DecideNextPlayer(ArrayList moves){
            return null;
        }
    }

    @Override
    protected abstract void printGrid();
    @Override
    protected abstract void Lose();
    @Override
    protected abstract void Win();
    @Override
    public abstract void ApplyPlayerMove(PlayerMove move);
    @Override
    public boolean AcceptMove(PlayerMove move) {

    }
}
