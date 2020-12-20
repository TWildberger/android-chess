package com.example.androidchess43.Pieces;

import com.example.androidchess43.R;

/**
 * This class implements Queen Piece functionality.
 * @author Tanvi Wagle, Tyler Wildberger
 */
public class Queen extends Piece{

    /**
     * Constructor for Queen class that sets the color field inherited from square.
     * @param color Color of the Piece
     */
    public Queen(boolean color) {

        this.setColor(color);
    }
    /**
     * Constructs the name of the piece using the piece type and color
     * @return String the name of the piece.
     */
    public String getName() {
        Boolean color = this.getColor();
        if (!color) {
            return "bQ";
        }
        return "wQ";
    }
    /**
     * Validates moving the queen diagonally
     * @param squares Array of the board
     * @param move Array containing initial and final states [rowStart, colStart, rowEnd, colEnd]
     * @param turn Black/White turn (black = false, white = true)
     * @return int If invalid returns -1. If valid returns 0.
     */
    public int move_diagonally(Square[][] squares, int [] move, Boolean turn) {

        /* going down and right = f4 h2 [4,5,6,7] */
        if (move[2] > move[0] && move[3] > move[1]) {
            ////System.out.println("Down right");
            int x = move[0];
            int y = move[1];
            x++;
            y++;
            while (x < move[2] && y < move[3]) {
                if (squares[x][y] instanceof Piece) {
                    ////System.out.println("Piece in between "+x + " " + y);
                    return -1;
                }
                x++;
                y++;
            }
        }
        /* going down and left */
        else if (move[2] > move[0] && move[3] < move[1]){
            ////System.out.println("Down Left");
            int x = move[0];
            int y = move[1];
            x++;
            y--;
            while (x < move[2] && y > move[3]) {
                if (squares[x][y] instanceof Piece) {
                    ////System.out.println("Piece in between "+x + " " + y);
                    return -1;
                }
                x++;
                y--;
            }
        }
        /* going up and left */
        else if (move[2] < move[0] && move[3] < move[1]){
            ////System.out.println("Up Left");
            int x = move[0];
            int y = move[1];
            x--;
            y--;
            while (x > move[2] && y > move[3]) {
                if (squares[x][y] instanceof Piece) {
                    ////System.out.println("Piece in between "+x + " " + y);
                    return -1;
                }
                x--;
                y--;
            }
        }
        /* going up and right c1 f4 [7,2,4,5] */
        else if (move[2] < move[0] && move[3] > move[1]){
            ////System.out.println("Up right");
            int x = move[0];
            int y = move[1];
            x--;
            y++;
            while (x > move[2] && y < move[3]) {
                if (squares[x][y] instanceof Piece) {
                    ////System.out.println("Piece in between "+x + " " + y);
                    return -1;
                }
                x--;
                y++;
            }
        }
        return 0;
    }

    /**
     * Validates moving the queen horizontally
     * @param squares Array of the board
     * @param move Array containing initial and final states [rowStart, colStart, rowEnd, colEnd]
     * @param turn Black/White turn (black = false, white = true)
     * @param horz true if the move is horizontal, false otherwise.
     * @return int If invalid returns -1. If valid returns 0.
     */
    public int move_vert_horz(Square[][] squares, int [] move, Boolean turn, Boolean horz) {
        int row = move[2] - move[0];
        int col = move[3] - move[1];
        if (horz) {
            if (row > 0) { // moving down
                //System.out.println("Down");
                int x = move[0];
                x++;
                while (x < move[2]) {
                    if (squares[x][move[1]] instanceof Piece) {
                        //System.out.println("Piece in between "+ x + " " + move[1]);
                        return -1;
                    }
                    x++;
                }
            }
            else if (row < 0) { // moving up
                //System.out.println("Up");
                int x = move[0];
                x--;
                while (x > move[2]) {
                    if (squares[x][move[1]] instanceof Piece) {
                        //System.out.println("Piece in between "+ x + " " + move[1]);
                        return -1;
                    }
                    x--;
                }
            }
        }
        else {
            if (col > 0) { // moving right
                //System.out.println("Right");
                int y = move[1];
                y++;
                while (y < move[3]) {
                    if (squares[move[0]][y] instanceof Piece) {
                        //System.out.println("Piece in between "+ move[0] + " " + y);
                        return -1;
                    }
                    y++;
                }
            }
            else if (col < 0) { // moving left
                //System.out.println("Left");
                int y = move[1];
                y--;
                while (y > move[3]) {
                    if (squares[move[0]][y] instanceof Piece) {
                        //System.out.println("Piece in between "+ move[0] + " " + y);
                        return -1;
                    }
                    y--;
                }
            }
        }
        return 0;
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
        // check which direction it is moving
        int row = move[2] - move[0];
        int col = move[3] - move[1];
        //////System.out.println("row: " + row + "col: " + col);
        if (row == 0) { // moving horizontally
            if (move_vert_horz(squares,move,turn, false) == -1) {
                ////System.out.println("Queen Illegal move");
                return -1;
            }
        }
        else if (col == 0) { // moving vertically
            if (move_vert_horz(squares,move,turn, true) == -1) {
                ////System.out.println("Queen Illegal move");
                return -1;
            }
        }
        else if (Math.abs(row) == Math.abs(col)) { // moving diagonally
            if (move_diagonally(squares,move,turn) == -1) {
                ////System.out.println("Queen Illegal move");
                return -1;
            }
        }
        else { // moving randomly
            //System.out.println("Queen Illegal move");
            return -1;
        }
        Square s = squares[move[2]][move[3]];
        if (s instanceof Piece) {
            if (s.getColor() == turn) {
                //System.out.println("Same color piece at end");
                return -1;
            }
        }
        return 0;
    }
    @Override
    public int getResourceImage() {
        if (getColor()){
            return R.drawable.wqueen;
        }
        return R.drawable.bqueen;
    }


}