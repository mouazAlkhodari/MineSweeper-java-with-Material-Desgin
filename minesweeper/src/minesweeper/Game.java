/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.List;

import CustomSequences.SurroundingMines2DArray;

/**
 *
 * @author Omar
 */
enum GameStatus{
    Running,Finish
}
enum WhenHitMine {
    Lose,Continue;
}

public abstract class Game {
    class Points {
        int RevealFloodFill;
        int RevealEmpty;
        int RevealMine;
        int MarkMine;
        int MarkNotMine;
        int Unmark;
        int LastNumber;

        public Points() {
            RevealFloodFill = 1;
            RevealEmpty = 10;
            RevealMine = -250;
            MarkMine = 5;
            MarkNotMine = -1;
            Unmark = -1;
        }
    }
    // <__ INNER CLASS __> \\
    abstract class GameRules{
        abstract int GetScoreChange(List<PlayerMove> moves);
        abstract Player DecideNextPlayer(List<PlayerMove> moves);
    }

    // <__ DATA MEMBERS __> \\
    Player currentPlayer;
    Grid grid;
    GameStatus status;
    GameRules currentRules;
    List<Player> players=new ArrayList<Player>();
    List<PlayerMove> moves=new ArrayList<PlayerMove>();


    // <__ METHODS __> \\
    public void initGame(int width,int height,int minesCount){
        currentPlayer = (Player)players.get(0);
        this.status=GameStatus.Running;// need to change to New Start game
        grid = new Grid(width,height,minesCount);
    }
    public void ApplyPlayerMove(PlayerMove move) {
        // here We ApPly The move And then Check The Status Of The Game
        moves =this.grid.AcceptMove(move);
        int ScoreChange=currentRules.GetScoreChange(moves);
        currentPlayer.addScore(ScoreChange);
        // need To be Func

        ChangeStatus();
        currentPlayer=currentRules.DecideNextPlayer(moves);

    }
    boolean AcceptMove(PlayerMove move){// x Rows Y columns
        Square s = move.getSquare();
        if(SurroundingMines2DArray.CheckIndex(s.getX(),s.getY(),grid.getWidth(),grid.getHeight()))
        {
            if(s.status == SquareStatus.Closed)
            {
                return true;
            }
            if(move.getType() == MoveType.Mark && s.status == SquareStatus.Marked)
            {
                return true;
            }
        }
        return false;
    }
    public void ChangeStatus(){
        Square[][] feild =this.grid.getField();
        int num=0;
        for(int i=1;i<this.grid.getWidth();i++){
            for(int j=1;j<this.grid.getHeight();j++){
                switch (feild[i][j].getStatus()){
                    case Marked:
                    case Closed:
                        num++;
                        break;
                }
            }
        }
        boolean CanContinue=false;
        for(int i=0;i<players.size();i++){
            if(players.get(i).getCurrentStatus()!=PlayerStatus.Lose){
                CanContinue=true;
            }
        }
        if(num==this.grid.getMinesCount() || !CanContinue){
            status=GameStatus.Finish;
        }
        else{
            status=GameStatus.Running;
        }
    }
    void AddPlayer(Player player)
    {
        players.add(player);
    }

    // <__ SETTERS-GETTERS __> \\
    //Setters
    void setStatus(GameStatus status) {
        this.status = status;
    }
    //Getters
    public GameStatus getStatus() {
        return status;
    }


    //This func Implement in each kind of game Like Console Or GUI...
    public abstract void StartGame();
    abstract void GetMove();
    abstract void EndGame();
    abstract void UpdateVeiw();

}
