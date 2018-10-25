package minesweeper;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class ConsolePlayer extends Player {

    public ConsolePlayer(String _name, int _currentScore) {
        super(_name, _currentScore);
    }

    @Override
    PlayerMove GetPlayerMove() {
        Scanner in =new Scanner(System.in);

        System.out.println("Enter the row and col");
        // get move in form
        String row_col=in.next();
        char[] charRC=row_col.toCharArray();
        Arrays.sort(charRC);
        String x = "",y="";
        int i=0;
        while(true){
            if(i>charRC.length)break;
            char c=charRC[i];
            if(c<'0' || c>'9')break;
            x+=c;
            i++;
        }
        while(true){
            if(i>charRC.length)break;
            char c=charRC[i];
            if(c<'0' || c>'9')break;
            y+=c;
            i++;
        }
        System.out.println("Enter the type of move\n1- reveal\n2-mark");
        String type=in.next();

        return new PlayerMove(this, new Square(Integer.valueOf(x), Integer.valueOf(y),Boolean.FALSE),
                            ((type=="1")?MoveType.Reveal:MoveType.Reveal),new MoveResult());
    }


}
