package com.example.androidchess43;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Button play = findViewById(R.id.game);
        Button record = findViewById(R.id.record);
        play.setOnClickListener(v -> goToBoard());
        record.setOnClickListener(v -> goToGamesList());
    }
    public void goToBoard(){
        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
    }
    public void goToGamesList(){
        Intent intent = new Intent(this, ViewGames.class);
        startActivity(intent);
    }
}