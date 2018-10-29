package minesweeper;

import BaseAlphabit.Converter;

import java.util.Scanner;

public class ConsolePlayer extends Player {
    private static Scanner in = new Scanner(System.in);
    public ConsolePlayer(String _name, int _currentScore) {
        super(_name, _currentScore);
    }

    @Override
    PlayerMove GetPlayerMove() {
        PlayerMove ret=null;
        MoveType t=MoveType.Reveal;
        boolean valid=true;
        String row,col;
        do {
            row = "";
            col = "";
            System.out.println("Enter your move:\n");
            // read the details of the move
            String row_col = in.nextLine();
            valid=true;
            for (int i = 0; i < row_col.length(); i++) {
                char c = row_col.charAt(i);
                if (c == '-') {
                    // get the mark if it found
                    if (i != row_col.length()-1) {//if not last char then its not valid move
                        valid = false;
                        break;
                    }
                    t = MoveType.Mark;
                }
                else if (i == 0) {//first char dont have last :)
                    if (Character.isDigit(c)) row += c;
                    else col += c;
                }
                else {// compare case of cur char and last one to decide
                    char last = row_col.charAt(i - 1);
                    if (Character.isDigit(c) == Character.isDigit(last)) {//same case
                        if (Character.isDigit(c)) row += c;
                        else col += c;
                    } else {
                        if (Character.isDigit(c)) {//----------row-------------
                            if (row.length() != 0) {//if row found before then its not valid move
                                valid = false;
                                break;
                            } else row += c;
                        }
                        else { //----------col---------------
                            if (col.length() != 0) {//if row found before then its not valid move
                                valid = false;
                                break;
                            } else col += c;
                        }

                    }
                }
            }
            if (row.length() == 0 || col.length() == 0) valid = false;
            if(!valid) System.out.println("not valid input!! try again...\n");
        } while (!valid);
        System.out.println(row+"\n"+col);
        int x=Integer.valueOf(row);
        int y= Converter.valueOf(col.toUpperCase());
//        System.out.println(x+".."+y+".."+t);
        return new PlayerMove(this, new Square(x,y,Boolean.FALSE,0),t,new MoveResult());
    }
}
