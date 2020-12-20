package com.example.androidchess43.Pieces;

import com.example.androidchess43.R;

/**
 * This class implements Pawn Piece functionality.
 * @author Tanvi Wagle, Tyler Wildberger
 */

public class Pawn extends Piece{

    /**
     * If it is possible to do enpassant on this pawn.
     */
    Boolean enpassent = false;

    /**
     * Constructor for Pawn class that sets the color field inherited from square.
     * @param color Color of the Piece
     */
    public Pawn(boolean color) {

        this.setColor(color);
    }
    /**
     * Constructs the name of the piece using the piece type and color
     * @return String the name of the piece.
     */
    public String getName() {
        Boolean color = this.getColor();
        if (!color) {
            return "bp";
        }
        return "wp";
    }

    /* invalid = -1
     * valid = return 0
     * enpassant = 3
     * promotrion = 2
     * */
    /**
     * Validates the move
     * @param squares Array of the board
     * @param move Array containing initial and final states [rowStart, colStart, rowEnd, colEnd]
     * @param turn Black/White turn (black = false, white = true)
     * @return int If invalid returns -1. If valid returns 0. If enpassant, returns 3. If promotion, returns 2.
     */
    @Override
    public int move(Square[][] squares, int [] move, Boolean turn) {
        // TODO Auto-generated method stub
        int rowStart = move[0];
        int colStart = move[1];
        int rowEnd = move[2];
        int colEnd = move[3];
        Boolean foundEnpass = false;
        // if it is at the initial position
        int len;
        if (turn) {len = rowStart - rowEnd;} // white
        else {len = rowEnd - rowStart;}

        if (len <= 0 || len > 2) { // cannot move backwards or in place or greater than 2
            //System.out.println("Illegal to Move more than 2, backwards or 0");
            return -1;
        }
        if ((len == 2 && rowStart != 6 && turn) ||  (len == 2 && rowStart != 1 && !turn)) {
            //System.out.println("Illegal to Move 2 as not the first move");
            return -1;
        }
        if (len == 2) {
            if (colStart != colEnd) { // make sure the first move is not TWO diagonal
                //System.out.println("Illegal to move 2 diagonally");
                return -1;
            }
            Square endPieceBefore;
            if (!turn) { endPieceBefore = squares[rowEnd-1][colEnd];}
            else {endPieceBefore = squares[rowEnd+1][colEnd];}
            Square endPiece = squares[rowEnd][colEnd];
            // both spots need to be empty to move. Cannot jump over a piece or move to a location with a piece
            if (endPiece instanceof Piece || endPieceBefore instanceof Piece) {
                //System.out.println("Illegal to move 2 when there is another piece or skipping a piece");
                return -1;
            }
            Pawn p = (Pawn) ((Piece)squares[move[0]][move[1]]);
            p.enpassent = true;
        }
        else { // moving only one forward
            Square endPieceBefore;
            if (!turn) { endPieceBefore = squares[rowEnd-1][colEnd];}
            else {endPieceBefore = squares[rowEnd+1][colEnd];}
            Square endPiece = squares[rowEnd][colEnd];
            int horzLen = move[3] - move[1];
            if (horzLen > 1 || horzLen < -1) { // move more than one horizontally
                //System.out.println("Illegal to move more than one horizontally");
                return -1;
            }
            if (horzLen == 1 || horzLen == -1) { // moving diagonally
                if (!(endPiece instanceof Piece)) { // if you are moving diagonally that means a piece has to be there
                    if ((endPieceBefore instanceof Pawn)){
                        Pawn p = (Pawn) ((Piece)endPieceBefore);
                        if (!p.enpassent){ // position of enpassant condition??
                            //System.out.println("Illegal Move Enpassant condition false");
                            return -1;
                        }
                        else {
                            //System.out.println("It is an enpassant!");
                            foundEnpass = true;
                        }
                    }
                    else {
                        //System.out.println("Illegal to move diagonally when there is no capture or enpassant");
                        return -1;
                    }
                }
                else {
                    Piece ep = (Piece) endPiece;
                    if (ep.getColor() == turn) { // the end piece is the same color as whoever's turn
                        //System.out.println("Illegal to move where your own piece is diagonally");
                        return -1;
                    }
                }
            }
            else if (horzLen == 0) { // moving forward
                if (endPiece instanceof Piece) { // if you are moving forward there cannot be a piece there
                    //System.out.println("Illegal to move because there is a piece");
                    return -1;
                }
            }
        }

        // remove any enpass
        if (turn) {
            // remove any black enpass
            for (int x=0; x<squares.length; x++) {
                if (squares[3][x] instanceof Pawn) {
                    Pawn p = (Pawn) ((Piece)squares[3][x]);
                    p.enpassent = false;
                }
            }
        }
        else {
            // remove any black enpass
            for (int x=0; x<squares.length; x++) {
                if (squares[4][x] instanceof Pawn) {
                    Pawn p = (Pawn) ((Piece)squares[4][x]);
                    p.enpassent = false;
                }
            }
        }
        if (foundEnpass) {
            return 3;
        }
        if ((move[2] == 0 && turn )|| (move[2] == 7 && !turn)){ // promotion
            return 2;
        }
        return 0;
    }
    @Override
    public int getResourceImage() {
        if (getColor()){
            return R.drawable.wpawn;
        }
        return R.drawable.bpawn;
    }

}

