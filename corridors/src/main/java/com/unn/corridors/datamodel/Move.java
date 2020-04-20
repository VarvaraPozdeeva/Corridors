package com.unn.corridors.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Move implements Serializable {
    private int row;
    private int col;
    private int direction;
    private int clientNum;
    private int indexCellFill;
    private Boolean isMyMove;
    public int getCellIndex(int count){
        return row *2 + (col * count*2) + direction;
    }
}
