package com.example.androidchess43.util;

import android.content.Context;

import com.example.androidchess43.data.model.Game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;


public class AppData implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<Game> game;

    public AppData (){

    }

    public List<Game> getGame() {
        return game;
    }


    public void setGame(List<Game> game){
        this.game = game;
    }
    private static AppData instance;

    public static AppData getInstance(Context context){
        if(instance == null) instance = AppData.Fetch(context);
        return instance;
    }

    public void save(Context context)
    {
        save(context, this);
    }

    private void save(Context context, AppData appData)
    {
        try{
            FileOutputStream fos = context.openFileOutput("appsettings", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(appData);
            oos.close();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static AppData Fetch(Context context)
    {
        AppData appData = null;
        try
        {
            FileInputStream fis = context.openFileInput("appsettings");
            ObjectInputStream ois = new ObjectInputStream(fis);
            appData = (AppData) ois.readObject();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if (appData == null){
            //Initialize
            appData =  new AppData();
        }

        return appData;
    }
}
