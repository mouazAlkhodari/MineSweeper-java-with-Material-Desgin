package minesweeper;

import BaseAlphabit.Converter;

import java.util.Scanner;

public class ConsolePlayer extends Player {
    private static final Scanner in = new Scanner(System.in);
    public ConsolePlayer(String _name, int _currentScore) {
        super(_name, _currentScore);
    }

    @Override
    PlayerMove GetPlayerMove() {
        PlayerMove Move=null;
        MoveType TypeOfMove=MoveType.Reveal;
        boolean validMove=true;
        String NumberOfRow="",NumberOfCol="";
        do {
            try {
                NumberOfRow = "";
                NumberOfCol = "";
                System.out.println("Enter your move:\n");
                // read the details of the move
                String NumberOfRow_col = in.nextLine();
                validMove=true;
                for (int i = 0; i < NumberOfRow_col.length(); i++) {
                    char c = NumberOfRow_col.charAt(i);
                    if(c==' '){
                        validMove=false;
                        break;
                    }
                    if (c == '-') {
                        // get the mark if it found
                        //in Case not last char then it is not valid Move move
                        if (i != NumberOfRow_col.length()-1) {
                            validMove = false;
                            break;
                        }
                        TypeOfMove = MoveType.Mark;
                    }
                    else if (i == 0) {//first char dont have last :)
                        if (Character.isDigit(c)) NumberOfRow += c;
                        else NumberOfCol += c;
                    }
                    else {// compare case of cur char and last one to decide
                        char last = NumberOfRow_col.charAt(i - 1);
                        if (Character.isDigit(c) == Character.isDigit(last)) {//same case
                            if (Character.isDigit(c)) NumberOfRow += c;
                            else NumberOfCol += c;
                        } else {
                            if (Character.isDigit(c)) {//----------NumberOfRow-------------
                                if (NumberOfRow.length() != 0) {//if NumberOfRow found before then its not validMove move
                                    validMove = false;
                                    break;
                                } else NumberOfRow += c;
                            }
                            else { //----------col---------------
                                if (NumberOfCol.length() != 0) {//if NumberOfRow found before then its not validMove move
                                    validMove = false;
                                    break;
                                } else NumberOfCol += c;
                            }

                        }
                    }
                }
                if (NumberOfRow.length() == 0 || NumberOfCol.length() == 0) validMove = false;
                if(!validMove) System.out.println("not valid Move!! try again...\n");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (!validMove);
        int IntegerValueOfRow=Integer.valueOf(NumberOfRow);
        int IntegerValueOfCol= Converter.valueOf(NumberOfCol.toUpperCase());
//        System.out.println(x+".."+y+".."+t);
        return new PlayerMove(this,
                new Square(IntegerValueOfRow,IntegerValueOfCol,Boolean.FALSE,0),
                TypeOfMove,new MoveResult());
    }
}
