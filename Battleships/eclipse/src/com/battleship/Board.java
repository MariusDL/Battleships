package com.battleship;

import java.util.ArrayList;

public class Board {

    private static final int BOARD_HEIGHT = 11;
    private static final char BOARD_WIDTH = 'K';

    public ArrayList<Square> squares;

    public Board(){
        squares = new ArrayList<>();

        for(int i=1; i<BOARD_HEIGHT;i++){
            for(char c='A'; c<BOARD_WIDTH; c++){
                squares.add(new Square(i,String.valueOf(c)));
            }
        }

    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    // displays the board on screen
    public void displayBoard(){
        printTop();
        int i=1;
        //iterate through the board squares
        for(Square square:squares){
            //print the rows number
            if(square.getCoordinates().getRow()==i){
                print(String.format("%3s",square.getCoordinates().getRow()+"|") );
                i++;
            }
            // print the square
            print(" " + square.getTypeOf() + " ");

            // if it gets to the 10th square on the row, start a new row
            if(square.getCoordinates().getColumn().equals("J")){
                print("\n");
            }

        }
    }

    // this will print the top row with letters
    private static void printTop(){
        for(char c='A';c<'K';c++){
            if(c=='A'){
                print("   ");
            }
            print(" " + c + " ");
            if(c=='J'){
                print("\n");
            }
        }
        print("   ");
        for(int i=0; i<30;i++){
            print("_");
        }
        print("\n");
    }


    private static void print(String word){
        System.out.print(word);
    }

}
