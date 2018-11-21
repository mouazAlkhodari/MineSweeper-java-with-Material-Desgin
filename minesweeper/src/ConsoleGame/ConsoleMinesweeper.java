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
        Thread T=new Thread(new Runnable() {
            @Override
            public void run() {
                int i=10;
                while(i>=0){
                    System.out.println(i);
                    i--;
                    try {
                        currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        T.start();
        //currentGame.start(window);
        for(int i=0;i<5;i++) {
            long x=0;
            for (long j = 0; j < 1e9; j++){
                x++;
            }
            System.out.println(i);
        }
        System.out.println("End Wait");
    }


    
}
