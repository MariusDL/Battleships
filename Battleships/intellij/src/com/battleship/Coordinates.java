package com.battleship;

public class Coordinates {
    private int row;
    private String column;

    public Coordinates(int row, String column){
        this.row = row;
        this.column = column;
    }

    public boolean compareCoordinates(Coordinates coordinate){
        if(coordinate.getRow() == this.row && coordinate.getColumn().toUpperCase().equals(this.column)){
            return true;
        }
        return false;
    }

    public int getRow() {
        return row;
    }


    public String getColumn() {
        return column;
    }

}
