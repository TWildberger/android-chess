package com.example.androidchess43;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class DrawDialog extends DialogFragment{
    public interface DrawDialogListener {

        public void exitGame(DialogFragment dialog);
        public void offerDraw(DialogFragment dialog);

    }

    DrawDialogListener listener;


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DrawDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }
    public void drawOfferedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View customLayout = inflater.inflate(R.layout.activity_draw_offered_dialog, null);
        Button draw = customLayout.findViewById(R.id.draw_button);
        Button cancel = customLayout.findViewById(R.id.no_draw);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customLayout);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View customLayout = inflater.inflate(R.layout.activity_draw_dialog, null);
        Button cancel = customLayout.findViewById(R.id.no_draw);
        Button draw = customLayout.findViewById(R.id.draw_button);
        builder.setView(customLayout);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.exitGame(DrawDialog.this);
                dismiss();
            }
        });
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.offerDraw(DrawDialog.this);
                dismiss();

            }
        });
        return builder.create();
    }
}
