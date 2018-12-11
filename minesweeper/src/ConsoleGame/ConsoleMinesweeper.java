/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConsoleGame;

import Models.Game.Game;
import Models.Player.DumbPlayer;
import Models.Player.Player;

import java.util.ArrayList;

import static java.lang.Thread.currentThread;


/**
 *
 * @author Omar
 */

public class ConsoleMinesweeper {


    private static final Player ConstPlayer=new ConsolePlayer("Flan");
    private static final Player ConstDumbPlayer=new DumbPlayer(10,10);
    /**
     */

    public static void main(String[] args) {
        // TODO code application logic here
       ArrayList<Player> Players=new ArrayList<Player>();
       Players.add(ConstPlayer);
       Players.add(ConstDumbPlayer);
       Game ConstGame=new ConsoleGame(Players);
     //  ConstGame.StartGame();

    }


    
}
