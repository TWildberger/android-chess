package com.example.androidchess43.Pieces;

import com.example.androidchess43.R;

public class Rook extends Piece{

    /**
     * Constructor for Rook class that sets the color field inherited from square.
     * @param color Color of the Piece
     */
    public Rook(boolean color) {

        this.setColor(color);
        this.hasMoved = false;
    }
    /**
     * Constructs the name of the piece using the piece type and color
     * @return String the name of the piece.
     */
    public String getName() {
        Boolean color = this.getColor();
        if (!color) {
            return "bR";
        }
        return "wR";
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
        int rowDelta = rowEnd - rowStart;
        int colDelta = colEnd - colStart;
        Square endPiece = squares[rowEnd][colEnd];

        //System.out.println(colEnd);

        if (endPiece instanceof Piece) {
            //There is a piece at dest, so check if it is same color
            Piece ep = (Piece) endPiece;
            if (ep.getColor() == turn) {
                //System.out.println("Illegal to move where your own color piece is.");
                return -1;
            }

        }
        if(colDelta!=0 && rowDelta!=0) {
            //System.out.println("Illegal move, can't move diagionally");
            return -1;
        }
        if((colDelta==0)&&(rowDelta==0)) {
            //System.out.println("Illegal move, piece must move");
            return -1;
        }
        if((colDelta == 0) && (rowDelta != 0)) {

            //moving along Y axis
            if(rowDelta<0) {
                //System.out.println("up");
                //moving up
                for(int i = rowStart-1; i > rowEnd; i--) {
                    //System.out.println(i);
                    Square piecePresent = squares[i][colStart];
                    if(piecePresent instanceof Piece) {
                        //System.out.println("illegal move, piece in the way moving up");
                        return -1;
                    }

                }

            }else if(rowDelta>0) {
                //System.out.println("down");
                //moving down
                for(int i = rowStart+1; i < rowEnd; i++) {
                    //System.out.println(i);
                    Square piecePresent = squares[i][colStart];
                    if(piecePresent instanceof Piece) {
                        //System.out.println("illegal move, piece in the way moving down");
                        return -1;
                    }

                }
            }
        }else if((colDelta != 0) && (rowDelta == 0)) {
            if(colDelta<0) {

                //moving left
                for(int i = colStart-1; i>colEnd; i--) {
                    //System.out.println(i);
                    Square piecePresent = squares[rowStart][i];
                    if(piecePresent instanceof Piece) {
                        //System.out.println("illegal move, piece in the way moving left");
                        return -1;
                    }
                }
            }else if(colDelta>0) {

                //moving right
                for(int i = colStart+1; i<colEnd; i++) {
                    //System.out.println(i);
                    Square piecePresent = squares[rowStart][i];
                    if(piecePresent instanceof Piece) {
                        //System.out.println("illegal move, piece in the way moving right");
                        return -1;
                    }
                }
            }
        }
        this.hasMoved = true;
//		squares[move[2]][move[3]] = squares[move[0]][move[1]];
//		squares[move[2]][move[3]].setRow(move[2]);
//		squares[move[2]][move[3]].setCol(move[3]);
//		squares[move[0]][move[1]] = this.underlyingColor(move[0], move[1]);
        return 0;
    }
    @Override
    public int getResourceImage() {
        if (getColor()){
            return R.drawable.wrook;
        }
        return R.drawable.brook;
    }
}
