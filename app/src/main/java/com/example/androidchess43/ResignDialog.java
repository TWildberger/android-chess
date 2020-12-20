package com.example.androidchess43;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ResignDialog extends DialogFragment{
    public interface ResignDialogListener  {
        public void acceptResign(DialogFragment dialog);
        public void cancelDialog(DialogFragment dialog);
    }
    ResignDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ResignDialogListener) context;
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
        View customLayout = inflater.inflate(R.layout.activity_resign_dialog, null);
        Button cancel = customLayout.findViewById(R.id.noResign);
        Button resign = customLayout.findViewById(R.id.Resign);
        builder.setView(customLayout);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancelDialog(ResignDialog.this);
                dismiss();
            }
        });

        resign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.acceptResign(ResignDialog.this);
                dismiss();
            }
        });
        return builder.create();
    }


}
