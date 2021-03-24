package com.battleship;

public class Square {

    // the type of square will contain a value for each type of action such as:
    //  O - normal hidden square
    //  X - player shoots but does not hit anything
    //  H - player hits a ship
    //  D - this will be placed on the sunk ship
    //  S - This will represent the ship

    private String typeOf;
    private Coordinates coordinates;

    public Square(int row, String column){
        coordinates = new Coordinates(row, column);
        typeOf = "0";
    }


    public String getTypeOf() {
        return typeOf;
    }

    public void setShipCoordinate() {
        this.typeOf = "S";
    }

    public void setMissShot() {
        this.typeOf = "X";
    }

    public void setHit() {
        this.typeOf = "H";
    }

    public void setNormalSquare() {
        this.typeOf = "0";
    }

    public void destroyShip() {
        this.typeOf = "D";
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    // this will check if the coordinates entered by the player match with those of the ship
    public boolean checkIfShipIsOnShotCoordinates(Coordinates playerShot){
        if(playerShot.getRow() == this.coordinates.getRow() &&
                    playerShot.getColumn().toUpperCase().equals(this.coordinates.getColumn())){
                return true;
        }
        return false;
    }
}
