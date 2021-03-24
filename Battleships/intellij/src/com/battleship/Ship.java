package com.battleship;

import java.util.ArrayList;

public class Ship {

    private int shipLength, hitsCount=0;
    private ArrayList<Square> shipSquares;
    private boolean shipDestroyed;

    public Ship(int shipLength){
        this.shipLength = shipLength;
        this.shipSquares = new ArrayList<>(shipLength);
        this.shipDestroyed = false;

        // initialize the array with ship coordinates
        for(int i=0;i<shipLength;i++){
            this.shipSquares.add(new Square(1,"A"));
        }
    }

    public boolean checkIfShipIsDestroyed(){
        return shipDestroyed;
    }

    public void sunkShip(){
        for(Square square: shipSquares){
            square.destroyShip();
        }
        this.shipDestroyed = true;
    }

    public ArrayList<Square> getShipSquares() {
        return shipSquares;
    }

    public void setShipSquares(Coordinates coordinates, int squarePosition){
        Square square = new Square(coordinates.getRow(), coordinates.getColumn());
        square.setShipCoordinate();
        this.shipSquares.set(squarePosition, square);
    }

    // checks if coordinates entered by the player hit the ship
    public boolean hitShip(Coordinates playerShot){
            for (Square square : shipSquares) {
                if (square.checkIfShipIsOnShotCoordinates(playerShot)) {
                    square.setHit();
                    this.hitsCount++;

                    if(hitsCount==shipLength){
                        sunkShip();
                    }
                    return true;
                }
            }
            return false;
    }

    public int getShipLength() {
        return shipLength;
    }

}
