package com.example.androidchess43.Pieces;

import com.example.androidchess43.R;

public class King extends Piece {

    /**
     * Constructor for King class that sets the color field inherited from square.
     *
     * @param color Color of the Piece
     * @author Tanvi Wagle, Tyler Wildberger
     */
    public King(boolean color) {

        this.setColor(color);
        this.hasMoved = false;
    }

    /**
     * Constructs the name of the piece using the piece type and color
     *
     * @return String the name of the piece.
     */
    public String getName() {
        Boolean color = this.getColor();
        if (!color) {
            return "bK";
        }
        return "wK";
    }

    /**
     * Validates the move
     *
     * @param squares Array of the board
     * @param move    Array containing initial and final states [rowStart, colStart, rowEnd, colEnd]
     * @param turn    Black/White turn (black = false, white = true)
     * @return int If invalid returns -1. If valid returns 0. If castling, return 4.
     */

    @Override
    public int move(Square[][] squares, int[] move, Boolean turn) {
        // TODO Auto-generated method stub
        int rowStart = move[0];
        int colStart = move[1];
        int rowEnd = move[2];
        int colEnd = move[3];
//
////System.out.println(rowStart);
////System.out.println(colStart);
////System.out.println(rowEnd);
////System.out.println(colEnd);

        if ((rowStart == 7 && colStart == 4 && rowEnd == 7 && colEnd == 6) || (rowStart == 0 && colStart == 4 && rowEnd == 0 && colEnd == 6)) {
            //king-side castling
            Square king = squares[rowStart][colStart];
            Square rook = squares[rowStart][colEnd + 1];
            //System.out.println("Correct position");
            if ((king instanceof King) && (rook instanceof Rook)) {
                Piece kingPiece = (King) king;
                Piece rookPiece = (Rook) rook;
                //System.out.println("Correct pieces");
                if ((kingPiece.hasMoved == false && rookPiece.hasMoved == false)) {
                    //System.out.println("pieces haven't moved");
                    if (!((squares[rowStart][colStart + 1] instanceof Piece) && (squares[rowStart][colStart + 2] instanceof Piece))) {
                        //System.out.println("no pieces in way");
                        return 4;
                    }
                }
            }
        } else if ((rowStart == 7 && colStart == 4 && rowEnd == 7 && colEnd == 2) || (rowStart == 0 && colStart == 4 && rowEnd == 0 && colEnd == 2)) {
            //queen-side castling
            Square king = squares[rowStart][colStart];
            Square rook = squares[rowStart][colEnd - 2];
            //System.out.println("Correct position");
            if ((king instanceof King) && (rook instanceof Rook)) {
                Piece kingPiece = (King) king;
                Piece rookPiece = (Rook) rook;
                //System.out.println("Correct pieces");
                if ((kingPiece.hasMoved == false && rookPiece.hasMoved == false)) {
                    //System.out.println("pieces haven't moved");
                    if (!((squares[rowStart][colStart - 1] instanceof Piece) && (squares[rowStart][colStart - 2] instanceof Piece))) {
                        //System.out.println("no pieces in way");
                        return 5;
                    }
                }
            }
        } else {
            if (!((rowStart + 1 == rowEnd) && (colStart == colEnd)
                    || (rowStart == rowEnd) && (colStart + 1 == colEnd) ||
                    (rowStart - 1 == rowEnd) && (colStart == colEnd) ||
                    (rowStart == rowEnd) && (colStart - 1 == colEnd) ||
                    (rowStart + 1 == rowEnd) && (colStart + 1 == colEnd) ||
                    (rowStart - 1 == rowEnd) && (colStart - 1 == colEnd) ||
                    (rowStart + 1 == rowEnd) && (colStart - 1 == colEnd) ||
                    (rowStart - 1 == rowEnd) && (colStart + 1 == colEnd))) {
                //System.out.print(rowStart + " " + colStart + " " + rowEnd + " " + colEnd);
                //System.out.println("Illegal move for king");
                return -1;
            } else {
                Square endPiece = squares[rowEnd][colEnd];
                if (endPiece instanceof Piece && endPiece.getColor() == turn) {
                    //System.out.println("Illegal to move where your own color piece is.");
                    return -1;

                } else {
                    this.hasMoved = true;
                    //	squares[move[2]][move[3]] = squares[move[0]][move[1]];
                    //	squares[move[2]][move[3]].setRow(move[2]);
                    //	squares[move[2]][move[3]].setCol(move[3]);
                    //	squares[move[0]][move[1]] = this.underlyingColor(move[0], move[1]);
                    return 0;
                }
            }

        }


        return -1;
    }
    @Override
    public int getResourceImage() {
        if (getColor()){
            return R.drawable.wking;
        }
        return R.drawable.bking;
    }
}

