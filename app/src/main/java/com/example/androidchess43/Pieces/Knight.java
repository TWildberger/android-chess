package com.example.androidchess43.Pieces;

import com.example.androidchess43.R;

/**
 * This class implements Knight Piece functionality.
 * @author Tanvi Wagle, Tyler Wildberger
 */

public class Knight extends Piece{


    /**
     * Constructor for Knight class that sets the color field inherited from square.
     * @param color Color of the Piece
     */
    public Knight(boolean color) {

        this.setColor(color);
    }
    /**
     * Constructs the name of the piece using the piece type and color
     * @return String the name of the piece.
     */
    public String getName() {
        Boolean color = this.getColor();
        if (!color) {
            return "bN";
        }
        return "wN";
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
        // TODO Auto-generated method stub
        int rowStart = move[0];
        int colStart = move[1];
        int rowEnd = move[2];
        int colEnd = move[3];

        //At most 8 possible legal moves, check for these
        if(!((rowStart-2 == rowEnd)&&(colStart-1 == colEnd)||(rowStart-2 == rowEnd)&&(colStart+1 == colEnd)||(rowStart+2 == rowEnd)&&(colStart-1 == colEnd)||(rowStart+2 == rowEnd)&&(colStart+1 == colEnd)||(rowStart-1 == rowEnd)&&(colStart-2 == colEnd)||(rowStart-1 == rowEnd)&&(colStart+2 == colEnd)||(rowStart+1 == rowEnd)&&(colStart-2 == colEnd)||(rowStart+1 == rowEnd)&&(colStart+2 == colEnd))){
            //System.out.println("Illegal move for knight");
            return -1;
        }
        else{
            //positionally valid move, so check if there is a piece at dest
            Square endPiece = squares[rowEnd][colEnd];
            if (endPiece instanceof Piece) {
                //There is a piece at dest, so check if it is same color
                Piece ep = (Piece) endPiece;
                if (ep.getColor() == turn) {
                    //System.out.println("Illegal to move where your own color piece is.");
                    return -1;
                }

            }
            //Otherwise, move the knight
//				squares[move[2]][move[3]] = squares[move[0]][move[1]];
//				squares[move[2]][move[3]].setRow(move[2]);
//				squares[move[2]][move[3]].setCol(move[3]);
//				squares[move[0]][move[1]] = this.underlyingColor(move[0], move[1]);
            return 0;

        }

    }
    @Override
    public int getResourceImage() {
        if (getColor()){
            return R.drawable.wknight;
        }
        return R.drawable.bknight;
    }
}
