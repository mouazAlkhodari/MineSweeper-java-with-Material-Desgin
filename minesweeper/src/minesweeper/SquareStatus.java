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
enum statusType { 
        Closed,OpenedEmpty,OpenedNumber,OpenedMine,Marked;
    }

public class SquareStatus {
    private statusType status;
    //init
    public SquareStatus(statusType type) {
        this.status = type;
    }
    
    statusType GetStatus() {return status;}   
}
