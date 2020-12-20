package com.example.androidchess43;


import android.app.Dialog;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOver extends DialogFragment {
    public interface GameOverListener {
        public void saveGame(DialogFragment dialog, String gameName);
        public void cancelGame(DialogFragment dialog);
    }

    public static final String WINNER_KEY = "winner";

    String gameName;
    GameOverListener listener;
    TextView error;
    public String getGameName() {
        return gameName;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (GameOverListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        Bundle bundle = getArguments();
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View customLayout = inflater.inflate(R.layout.activity_gameover_dialog, null);
        EditText name = customLayout.findViewById(R.id.gameName);
        Button save = customLayout.findViewById(R.id.save);
        Button nosave = customLayout.findViewById(R.id.noSave);
        error = customLayout.findViewById(R.id.errMessage);
        TextView winner = customLayout.findViewById(R.id.winner);
        winner.setText(bundle.getString(WINNER_KEY));
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                gameName = s.toString();
                Log.d("debug", gameName);
            }
        });
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customLayout);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if game name is entered
                if (gameName == null || gameName == ""){
                    error.setText("Error! You need to enter a game name if you want to save it.");
                }
                // check if game name exists
                listener.saveGame(GameOver.this, gameName);
                dismiss();
            }
        });
        nosave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancelGame(GameOver.this);
               dismiss();
            }
        });
        return builder.create();
    }


}
