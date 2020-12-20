package com.example.androidchess43.Pieces;

public abstract class Piece extends Square{
    protected boolean hasMoved;

    /**
     * Constructor for Piece Class.
     */
    public Piece() {

    }
    /**
     * Getter to determine whether a piece has made a move this game, used for castling.
     * @return true if the piece has moved yet, false otherwise.
     */
    public boolean hasMoved() {

        return this.hasMoved;
    }
    /**
     * Setter to determine whether a piece has made a move this game, used for castling.
     * @param hasMoved whether the piece has moved.
     */
    public void setHasMoved(boolean hasMoved) {

        this.hasMoved = hasMoved;
    }

    public abstract int getResourceImage();
    /**
     * Abstract method Validates the move
     * @param squares Array of the board
     * @param move Array containing initial and final states [rowStart, colStart, rowEnd, colEnd]
     * @param turn Black/White turn (black = false, white = true)
     * @return int If invalid returns -1. If valid returns 0.
     */
    public abstract int move(Square[][] squares, int[] move, Boolean turn); // abstract method


}

