package Models.Grid;

import java.io.Serializable;

public class Mine implements Serializable {
   int ID;
   //to define unique id for each mine
   private static int IDNumber = 0;

   public Mine() {
       ID = IDNumber++;
    }
}
