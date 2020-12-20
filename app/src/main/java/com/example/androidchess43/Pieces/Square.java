package com.example.androidchess43.Pieces;

public class Square {

    /**
     * Color of Square
     */
    protected boolean color;

    /**
     * Constructor for Square class.
     */
    public Square() {

    }
    /**
     * Constructor for Square class that sets the color field.
     * @param color Color of the Square
     */
    public Square(boolean color) {

        this.color = color;
    }
    /**
     * Constructs the printed form of the square using the color
     * @return String the name of the piece.
     */
    public String getName() {
        if (!color) {
            return "##";
        }
        return "  ";
    }
    //use bool to flag color, true = white, false = black
    /**
     * Getter method for the color field
     * @return boolean Color of square (black=false, white=true)
     */
    public boolean getColor() {

        return this.color;
    }
    /**
     * Setter method for the color field
     * @param color Color of the square (black=false, white=true)
     */
    public void setColor(boolean color) {

        this.color = color;
    }


}
