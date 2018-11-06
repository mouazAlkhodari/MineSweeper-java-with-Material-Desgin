/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConsoleGame;

import ConsoleGame.ConsoleGame;
import Models.Game.Game;
import Models.Player.Player;

import java.util.ArrayList;


/**
 *
 * @author Omar
 */

public class ConsoleMinesweeper {


    private static final Player ConstPlayer=new ConsolePlayer("Flan");
    /**
     */

    public static void main(String[] args) {
        // TODO code application logic here
       ArrayList<Player> Players=new ArrayList<Player>();
       Players.add(ConstPlayer);
       Game ConstGame=new ConsoleGame(Players);
       ConstGame.StartGame();
    }


    
}
