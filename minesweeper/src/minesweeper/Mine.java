package minesweeper;

public class Mine {
   int ID;
   //to define unique id for each mine
   private static int IDNumber = 0;

   public Mine() {
       ID = IDNumber++;
    }
}
