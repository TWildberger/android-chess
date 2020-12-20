package com.example.androidchess43.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    public Date date;
    private ArrayList<int[]> moveList;
    private ArrayList<Integer> successList;
    private ArrayList<String> promotionList;

    public Game(String name, ArrayList<int[]>moves, ArrayList<Integer>success,ArrayList<String>promo){

        this.name = name;
        this.moveList = moves;
        this.date = Calendar.getInstance().getTime();
        this.successList = success;
        this.promotionList = promo;
    }

    public String getName(){
        return this.name;
    }

    public Date getDate(){
        return this.date;
    }

    public ArrayList<int[]> getMoves(){
        return this.moveList;
    }

    public ArrayList<Integer> getSuccess(){return this.successList;}

    public ArrayList<String> getPromotions(){return this.promotionList;}

    public String toString(){
        return name + " - " + date.toString();
    }
}
