package com.example.androidchess43;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ViewGamesErrDialog extends DialogFragment{
    public interface ErrDialogListener {

        public void exitGame(DialogFragment dialog);
    }




   ErrDialogListener listener;



    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ErrDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View customLayout = inflater.inflate(R.layout.activity_viewgames_err_dialog, null);
        Button exitButton = customLayout.findViewById(R.id.vg_cancel);

        builder.setView(customLayout);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.exitGame(ViewGamesErrDialog.this);
                dismiss();
            }
        });
        return builder.create();
    }


}

