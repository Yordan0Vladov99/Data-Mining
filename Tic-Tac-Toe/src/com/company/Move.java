package com.company;

public class Move {
    private int type;
    private int row;
    private int col;

    public Move(int type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
    }
    public Move(){
        this(-1,-1,-1);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
