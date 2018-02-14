package com.example.user.keepingmeontrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.ForgotPassDialog;
import com.example.user.keepingmeontrack.MainTabActivity;
import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by User on 06.02.2018.
 */

public class LoginFragment extends Fragment {

    public LoginFragment() {
    }
    DatabaseReference myRef;
    FirebaseDatabase database;
    EditText sinInUserName;
    EditText sinUInPassword;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        sinInUserName = rootView.findViewById(R.id.user_name_edt);
        sinUInPassword = rootView.findViewById(R.id.password_edt);
        //final EditText singInEmail = rootView.findViewById(R.id.signup_email);
        Button sinInBtn = rootView.findViewById(R.id.sing_in_btn);
        TextView singUpBtn = rootView.findViewById(R.id.register_btn);
        TextView forgotB = rootView.findViewById(R.id.login_forget_click);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        sinInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser(sinInUserName.getText().toString(), sinUInPassword.getText().toString());
            }
        });

        forgotB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProductDialog();
            }
        });
        return rootView;
    }

    private void LoginUser(String mail, final String password) {

        if (!validateForm()) {
            return;
        }
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Login Succsess!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), MainTabActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean validateForm() {
        boolean valid = true;

        String email = sinInUserName.getText().toString();
        if (TextUtils.isEmpty(email)) {
            sinInUserName.setError("Required.");
            valid = false;
        } else {
            sinInUserName.setError(null);
        }
        String password = sinUInPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            sinUInPassword.setError("Required.");
            valid = false;
        } else {
            sinUInPassword.setError(null);
        }
        return valid;
    }
    private void showAddProductDialog() {
        ForgotPassDialog dialog = new ForgotPassDialog();
        dialog.show(getActivity().getFragmentManager(), "example");

    }
}
