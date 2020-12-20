package com.example.androidchess43.Pieces;

import com.example.androidchess43.R;

/**
 * This class implements Bishop Piece functionality.
 * @author Tanvi Wagle, Tyler Wildberger
 */

public class Bishop extends Piece{

    /**
     * Constructor for Bishop class that sets the color field inherited from square.
     * @param color Color of the Piece
     */
    public Bishop(boolean color) {

        this.setColor(color);
    }
    /**
     * Constructs the name of the piece using the piece type and color
     * @return String the name of the piece.
     */
    public String getName() {
        Boolean color = this.getColor();
        if (!color) {
            return "bB";
        }
        return "wB";
    }

    @Override
    public int getResourceImage() {
        if (getColor()){
            return R.drawable.wbishop;
        }
        return R.drawable.bbishop;
    }

    /**
     * Validates the move
     * @param squares Array of the board
     * @param move Array containing initial and final states [rowStart, colStart, rowEnd, colEnd]
     * @param turn Black/White turn (black = false, white = true)
     * @return int If invalid returns -1. If valid returns 0.
     */
    @Override
    public int move(Square[][] squares, int [] move, Boolean turn)  {
        //c1 f4 [7,2,4,5]
        //       0 1 2 3
        // check which direction is it going
        //  4 - 7  -  going up row--
        // 5 - 2 + going right column++
        // check if position is on diagonal
        if (Math.abs(move[2] - move[0]) != Math.abs(move[3] - move[1])) {
            //System.out.println("Wrong dimensions");
            return -1 ;
        }
        // check if all position until that position are empty
        /* going down and right = f4 h2 [4,5,6,7] */
        if (move[2] > move[0] && move[3] > move[1]) {
            //System.out.println("Down right");
            int x = move[0];
            int y = move[1];
            x++;
            y++;
            while (x < move[2] && y < move[3]) {
                if (squares[x][y] instanceof Piece) {
                    //System.out.println("Piece in between "+x + " " + y);
                    return -1;
                }
                x++;
                y++;
            }
        }
        /* going down and left */
        else if (move[2] > move[0] && move[3] < move[1]){
            //System.out.println("Down Left");
            int x = move[0];
            int y = move[1];
            x++;
            y--;
            while (x < move[2] && y > move[3]) {
                if (squares[x][y] instanceof Piece) {
                    //System.out.println("Piece in between "+x + " " + y);
                    return -1;
                }
                x++;
                y--;
            }
        }
        /* going up and left */
        else if (move[2] < move[0] && move[3] < move[1]){
            //System.out.println("Up Left");
            int x = move[0];
            int y = move[1];
            x--;
            y--;
            while (x > move[2] && y > move[3]) {
                if (squares[x][y] instanceof Piece) {
                    //System.out.println("Piece in between "+x + " " + y);
                    return -1;
                }
                x--;
                y--;
            }
        }
        /* going up and right c1 f4 [7,2,4,5] */
        else if (move[2] < move[0] && move[3] > move[1]){
            //System.out.println("Up right");
            int x = move[0];
            int y = move[1];
            x--;
            y++;
            while (x > move[2] && y < move[3]) {
                if (squares[x][y] instanceof Piece) {
                    //System.out.println("Piece in between "+x + " " + y);
                    return -1;
                }
                x--;
                y++;
            }
        }
        // check if end position has a piece with same color - if so return false.
        Square s = squares[move[2]][move[3]];
        if (s instanceof Piece) {
            if (s.getColor() == turn) {
                //System.out.println("Same color piece at end");
                return -1;
            }
        }
        return 0;
    }
}

