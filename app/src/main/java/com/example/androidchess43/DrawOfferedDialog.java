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

public class DrawOfferedDialog extends DialogFragment{
    public interface DrawOfferedListener {
        public void cancelDialog(DialogFragment dialog);
        public void acceptDraw(DialogFragment dialog);
    }

    public static final String WINNER_KEY = "winner";

    DrawOfferedDialog.DrawOfferedListener listener;
    TextView error;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (DrawOfferedDialog.DrawOfferedListener) context;
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
        View customLayout = inflater.inflate(R.layout.activity_draw_offered_dialog, null);
        builder.setTitle("You have been offered a draw");
        Button draw = customLayout.findViewById(R.id.draw_button);
        Button cancel = customLayout.findViewById(R.id.no_draw);
        TextView winner = customLayout.findViewById(R.id.heading);
        winner.setText(bundle.getString(WINNER_KEY));

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customLayout);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancelDialog(DrawOfferedDialog.this);
                dismiss();
            }
        });
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.acceptDraw(DrawOfferedDialog.this);
                dismiss();
            }
        });
        return builder.create();
    }


}
