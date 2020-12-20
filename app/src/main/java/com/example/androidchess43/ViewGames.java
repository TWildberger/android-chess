package com.example.androidchess43;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.androidchess43.data.model.Game;
import com.example.androidchess43.util.AppData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ViewGames extends AppCompatActivity implements ViewGamesErrDialog.ErrDialogListener{

    private ListView listView;

    //List<Game> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);
        AppData appData = AppData.getInstance(this);
        List<Game> gameList = appData.getGame();
        if(gameList==null){
            Log.d("debug", "gets here");
            DialogFragment newFragment = new ViewGamesErrDialog();
            newFragment.show(getSupportFragmentManager(), "vg error");
            return;
        }
        Spinner spinner = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapter);

        listView = findViewById(R.id.mainListView);
        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this, android.R.layout.simple_list_item_1, gameList);
        listView.setAdapter(adapter);
        Log.d("debug", gameList.toString());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game selectedItem = (Game) parent.getItemAtPosition(position);
                Log.d("debug", selectedItem.getName());
                goToRecordGame(selectedItem);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Sort by Title")){
                    Collections.sort(gameList,new MyComparator(MyComparator.NAME));
                }else if(selectedItem.equals("Sort by Date")){
                    Collections.sort(gameList,new MyComparator(MyComparator.DATE));
                }

                adapter.notifyDataSetChanged();
                spinAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void goToRecordGame(Game game){
        Intent intent = new Intent(this, RecordGame.class);
        intent.putExtra("selectedGame", game);
        startActivity(intent);
    }

    @Override
    public void exitGame(DialogFragment dialog) { goToHome(); }

    public void goToHome(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    static class MyComparator implements Comparator<Game>{
        static final int NAME = 0, DATE = 1;
        int type;

        public MyComparator(int type){
            this.type = type;
        }
        @Override
        public int compare(Game o1, Game o2){
            if(type==NAME){
                return o1.getName().compareTo(o2.getName());
            }else if(type == DATE){
                if(o1.getDate().getTime()>o2.getDate().getTime()){
                    return 1;
                }else if(o1.getDate().getTime()==o2.getDate().getTime()){
                    return 0;
                }else{
                    return -1;
                }

            }
            return 0;
        }
    }
}