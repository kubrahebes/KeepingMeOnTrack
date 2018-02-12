package com.example.user.keepingmeontrack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.keepingmeontrack.fragments.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View addView = inflater.inflate(R.layout.forgotpass_layout, null);

        final Dialog addDialog = builder.setView(addView)
                .create();
        addDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button positiveButton = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);
                Button forgotBtn = addDialog.findViewById(R.id.forgot_button);
                forgotBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        final EditText forgotUname = addView.findViewById(R.id.forgot_username);
                        String emailAddress = forgotUname.getText().toString();
                        if (emailAddress.isEmpty()) {
                            Toast.makeText(getActivity(), "Enter the your email adress", Toast.LENGTH_SHORT).show();
                        } else {


                            auth.sendPasswordResetEmail(emailAddress)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "sucsess", Toast.LENGTH_SHORT).show();
                                                forgotUname.setText("");
                                            } else {
                                                Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText forgotUname = addView.findViewById(R.id.forgot_username);

                        String forgotName = forgotUname.getText().toString().trim();

                        if (TextUtils.isEmpty(forgotName)) {
                            Toast.makeText(getActivity(), "Failed to send", Toast.LENGTH_SHORT).show();
                        } else if (!forgotName.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                            Toast.makeText(getActivity(), "Invalid email address!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        return addDialog;
    }
}
