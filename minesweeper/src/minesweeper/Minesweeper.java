/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;
import BaseAlphabit.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Omar
 */

public class Minesweeper {

    private static final int ConstMines = 10;
    private static final int ConstHeight = 10 ;
    private static final int ConstWidth = 10;
    private static final Player ConstPlayer=new ConsolePlayer("Flan",0);
    /**
     */

    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hello guys Engo using git with me ");
        System.out.println("am here guys (V)_(V)");
        System.out.println("Da");
        /*
        ---- for testing class player :3
        Player p1=new ConsolePlayer("flan",0);
        p1.GetPlayerMove();
        */

       List<Player> Players=new ArrayList<Player>();
       Players.add(ConstPlayer);
       Game ConstGame=new ConsoleGame(ConstWidth,ConstHeight,ConstMines,Players);
       ConstGame.StartGame();
    }

    
}
