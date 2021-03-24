package com.battleship;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Scanner console = new Scanner(System.in);
    private Random random = new Random();

    private Board playerBoard;
    private int totalShipHits, score;

    private ArrayList<Ship> ships;

    public Game(){
        this.totalShipHits = 0;
        this.score = 0;

        this.ships = new ArrayList<>();
        this.ships.add(new Ship(5));
        this.ships.add(new Ship(4));
        this.ships.add(new Ship(4));
    }

    public void startGame(){
        this.playerBoard = new Board();

        // generate the ships coordinates and update the list
        generateShips(this.ships);

        boolean runGame=true;

        while(runGame){

            // place the ships on the board
            placeShipsOnDisplayBoard();

            // display the board
            playerBoard.displayBoard();

            print("Total shots fired: " + this.score);
            print("\nEnter the coordinate where you want to shot(ex: A1): ");
            String shot = console.next();

            // validate the user input
            if(validateShot(shot)){
                score++;
                char column = shot.charAt(0);
                int row;
                if(shot.length()==2){
                    row = Character.getNumericValue(shot.charAt(1));
                }else{
                    row = Integer.parseInt(shot.substring(1,3));
                }

                // create a coordinate object with the user input
                Coordinates playerShotCoordinates = new Coordinates(row,String.valueOf(column));

                /**
                 * Call the function that checks if any of the ships has been hit
                 * The function takes input a coordinate object
                 * The function returns a boolean if a ship has been hit or not
                 * If no ship has been hit, place a X marker on the board
                 */
                if(!playerShot(playerShotCoordinates)){

                    for(int i=0; i<playerBoard.getSquares().size(); i++){
                        Square boardSquare = playerBoard.getSquares().get(i);
                        if(boardSquare.getCoordinates().compareCoordinates(playerShotCoordinates)){
                            boardSquare.setMissShot();
                        }
                    }
                } else{
                    totalShipHits++;
                }

            } else {
                print("\nINVALID SHOT. TRY AGAIN.\n");
            }

            // if all the the ships have been hit, end the game
            if(totalShipHits == 13){
                placeShipsOnDisplayBoard();
                playerBoard.displayBoard();

                runGame=false;
                print("\nGAME OVER. Your final score is: " + this.score+"\n\n");
            }
        }
    }

    public void gameMenu(){
        String choice="0";

        while(choice.equals("0")){
            print("########  Welcome to  ########\n");
            print("######### Battleship #########\n");

            print("             /\"\"|\n");
            print("         (\"\"\"\"\"\"|\n");
            print("   _______|_____|_______\n");
            print("   \\   o o o o o o o   /\n");
            print("    \\_________________/\n");
            print("##############################\n");
            print("Choose one of the following options \n");
            print("1. Play game \n");
            print("2. Exit game \n");
            print("Enter your choice: ");
            choice = console.next();

            switch(choice){
                case "1":
                    startGame();
                    break;
                case "2":
                    System.exit(0);
                default:
                    print("Incorrect choice. Try Again.\n");
                    choice="0";
            }
        }
    }

    /**
     * This method iterates through the ships list and checks if any of the ships has been hit
     *   by passing the coordinates where the player has shot
     *
     *  It calls the method inside each ship object that checks all the ship coordinates
     *    and returns if the ship has been hit or not
     *
     */
    private boolean playerShot(Coordinates coordinates){
        for(Ship ship: ships){
            if(ship.checkIfShipIsDestroyed()){
                continue;
            }
            if(ship.hitShip(coordinates)){
                return true;
            }
        }
        return false;
    }


    private void placeShipsOnDisplayBoard(){

        // THIS WILL DISPLAY THE COORDINATES OF THE SHIPS
//        for(int i=0; i<ships.size();i++){
//            Ship ship = this.ships.get(i);
//
//            for(int j =0; j<ship.getShipLength();j++){
//
//                System.out.println("Ship" + i +" - Coordinate " + j +
//                        ": row " + ship.getShipSquares().get(j).getCoordinates().getRow() +
//                        "; column: " +ship.getShipSquares().get(j).getCoordinates().getColumn());
//            }
//
//        }


        //iterate through the board squares
        for(int i=0; i<playerBoard.getSquares().size(); i++){
            Square boardSquare = playerBoard.getSquares().get(i);

            // iterate through each ship
            int j=0;
            while(j<ships.size()){

                // iterate through each square of the ship
                for(Square shipSquare:ships.get(j).getShipSquares()){

                    // if the ship square coordinate matches with the current board square coordinates, place it on the board
                    if(boardSquare.getCoordinates().compareCoordinates(shipSquare.getCoordinates())){

                        // check not to display a ship square that has not been hit
                        if(!shipSquare.getTypeOf().equals("S")){
                            playerBoard.getSquares().set(i,shipSquare);

                        }
                    }
                }
                j++;
            }
        }
    }

    /**
     *
     * Generate the ships coordinates and check if they overlap
     * If any of the ships overlap, generate all of them them again
     *
     * The function is a bit complicated because this way the program is scalable and more ships can be added
     * just by putting them in the list of ships
     *
     * It would have been simpler to just compare only three ships with if conditions
     *
     */
    private ArrayList<Ship> generateShips(ArrayList<Ship> ships){
        // this will be false if in the end none of the ships overlap
        boolean shipsOverlap=true;

        // the main loop of the function
        while (shipsOverlap){

            // generate coordinates for all the ships
            for(Ship ship:ships) {
                generateShipCoordinates(ship);
            }

            // this will iterate through the list and get the first ship to compare
            for (int i=0; i<ships.size(); i++){
                Ship ship1 = ships.get(i);

                // iterate through the list again and get the second ship to compare
                for(int j=i+1; j<ships.size();j++){
                    Ship ship2 = ships.get(j);

                    // if the ships do not overlap, change and maintain the flag variable to false
                    //
                    // if any of the ships overlap change the variable to true, break both loops and generate the ships again
                    if(!compareShipsNotToOverlap(ship1,ship2)){
                        shipsOverlap =false;
                    } else {
                        shipsOverlap=true;
                        break;
                    }
                }
                if(shipsOverlap){
                    break;
                }
            }
        }
        return ships;
    }

    // compare 2 different given ships if any of their coordinates overlap
    private boolean compareShipsNotToOverlap(Ship ship1, Ship ship2){

        // loop through the coordinates of the first ship
        for(int i = 0; i<ship1.getShipSquares().size(); i++){
            Square Ship1Square = ship1.getShipSquares().get(i);

            // loop through the coordinates of the second ship
            for(int j = 0; j<ship2.getShipSquares().size(); j++){
                Square Ship2Square = ship2.getShipSquares().get(j);

                //compare the coordinates of the first ship with those of the second ship
                if(Ship1Square.getCoordinates().getRow() == Ship2Square.getCoordinates().getRow() &&
                Ship1Square.getCoordinates().getColumn().equals(Ship2Square.getCoordinates().getColumn())){
                    return true;
                }
            }
        }
        return false;
    }

    // this method generates coordinates in a given ship and returns that ship
    private Ship generateShipCoordinates(Ship ship){

        // generate a number for the row
        int initialShipCoordinateRow = random.nextInt(10) + 1;

        // generate a char for the column
        char initialShipCoordinateColumn = (char)('A'+random.nextInt(10));

        // generate the orientation of the ship: 0 - vertical , 1 - horizontal
        int orientation = random.nextInt(2);

        // add the initial coordinate to the ship object
        ship.setShipSquares(new Coordinates(initialShipCoordinateRow, String.valueOf(initialShipCoordinateColumn)),0);

        // generate the body of the ship
        for(int i=1; i<ship.getShipLength(); i++){
            if(orientation==0){
                // check if the initial point is close to the top edge and prevent the ship to be generated out of board bounds
                if(initialShipCoordinateRow<=5){
                    ship.setShipSquares(new Coordinates(initialShipCoordinateRow+i, String.valueOf(initialShipCoordinateColumn)), i);
                } else {
                    ship.setShipSquares(new Coordinates(initialShipCoordinateRow-i, String.valueOf(initialShipCoordinateColumn)), i);
                }
            } else {
                // check if the initial point is close to the side edge and prevent the ship to be generated out of board bounds
                if(initialShipCoordinateColumn<='E'){
                    ship.setShipSquares(new Coordinates(initialShipCoordinateRow, String.valueOf((char)(initialShipCoordinateColumn+i))), i);
                } else {
                    ship.setShipSquares(new Coordinates(initialShipCoordinateRow, String.valueOf((char)(initialShipCoordinateColumn-i))), i);
                }
            }
        }
        return ship;
    }

    // validate the shot entered by the player
    private boolean validateShot(String shot){
        // check that the length of the shot to be less than 3 characters
        if(shot.length()<=3){
            //check that the first character is a letter in the board bounds
            if(((shot.charAt(0)>='A' && shot.charAt(0)<='J') ||
               (shot.charAt(0)>='a' && shot.charAt(0)<='j'))){
                // check if the second character in not in bounds
                if(!(shot.charAt(1)>='1' && shot.charAt(1)<='9')){
                    return false;
                }

                // check if the maximum number entered by the user is 10
                if(shot.length()==3){
                   int row = Integer.parseInt(String.valueOf(shot.charAt(1))+String.valueOf(shot.charAt(2)));
                   if(row !=10){
                       return false;
                   }
                }
                return true;
            }
        }
        return false;
    }

    private static void print(String word){
        System.out.print(word);
    }
}
