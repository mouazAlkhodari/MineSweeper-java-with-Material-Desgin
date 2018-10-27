/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;


import BaseAlphabit.Converter;

import java.util.List;

/**
 *
 * @author Omar
 */
public class ConsoleGame extends NormalGame {
    public ConsoleGame(int Width, int Height, int NumMines, List player) {
        for(Object curPlayer:player){
            this.AddPlayer((Player) curPlayer);
        }
        initGame(Width,Height,NumMines);
    }

    @Override
    protected void UpdateVeiw() {
        for(int i=1;i<this.grid.getWidth();i++){
            System.out.println(" "+ Converter.valueOf(i));
        }
        Square[][] feild=this.grid.getField();
        for(int i=1;i<this.grid.getHeight();i++){
            System.out.println("/n");
            System.out.println(i+" ");
            for (int j=1;j<this.grid.getWidth();i++){
                switch (feild[i][j].status){
                    case Closed:
                        System.out.println("O ");
                        break;
                    case Marked:
                        System.out.println("p ");
                        break;
                    case OpenedMine:
                        System.out.println("B ");
                        break;
                    case OpenedNumber:
                        System.out.println(feild[i][j].getNumberOfSurroundedMines()+" ");
                        break;
                    case OpenedEmpty:
                        System.out.println(". ");
                        break;
                }
            }
        }
    }

    @Override
    protected void Lose() {
        // must Do some things in Grid make user feel unhappy because he Lose the game 🌚_🌚
        UpdateVeiw();
        System.out.println("Same On You!! \n Game Orver\n");
    }

    @Override
    protected void Win() {
        // must Do some things in Grid make user feel happy because he win the game 💙_💙
        UpdateVeiw();
        System.out.println("You catch all Mines and win the game!!\n");
    }

    @Override
    public void ApplyPlayerMove(PlayerMove move) {
        this.grid.AcceptMove(move);
    }
}
