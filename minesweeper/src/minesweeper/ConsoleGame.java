/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;


import BaseAlphabit.Converter;

/**
 *
 * @author Omar
 */
public class ConsoleGame extends NormalGame {
    @Override
    private abstract void printGrid() {
        for(int i=0;i<this.grid.getWidth();i++){
            System.out.println(" "+ Converter.valueOf(i));
        }

        for(int i=0;i<this.grid.getHeight();i++){
            System.out.println("/n");
            System.out.println(i+" ");
            for (int j=0;j<this.grid.getWidth();i++){

            }
        }
    }

    @Override
    private abstract void Lose() {
        // must Do some things in Grid make user feel unhappy because he Lose the game 🌚_🌚
        System.out.println("You catch all Mines and win the game!!\n 💃💃💃^___^💃💃💃");
    }

    @Override
    private abstract void Win() {
        // must Do some things in Grid make user feel happy because he win the game 💙_💙
        System.out.println("You catch all Mines and win the game!!\n 💃💃💃^___^💃💃💃");
    }

    @Override
    public void ApplyPlayerMove(PlayerMove move) {

    }

    @Override
    public boolean AcceptMove(PlayerMove move){return true;}
}
