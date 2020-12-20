package com.example.androidchess43;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class PromotionDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] pieces = getResources().getStringArray(R.array.pieces);
        piece = pieces[position];
        Log.d("debug", piece);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface PromotionListener {
        public void select(DialogFragment dialog);
    }

    String piece;
    PromotionListener listener;
    TextView error;

    public String getPiece() {
        return piece;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (PromotionListener) context;
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
        View customLayout = inflater.inflate(R.layout.promotion_dialog, null);
        Button save = customLayout.findViewById(R.id.select);
        error = customLayout.findViewById(R.id.error);
        Spinner spinner = customLayout.findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customLayout);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.select(PromotionDialog.this);
                dismiss();
            }
        });
        return builder.create();
    }

}