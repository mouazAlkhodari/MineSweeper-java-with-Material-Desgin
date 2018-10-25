/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

/**
 *
 * @author Da
 */
public class Mine {
   int ID;
   //to define unique id for each mine
   private static int IDNumber = 0;

    public Mine() {
       ID = IDNumber++;
    }
}
