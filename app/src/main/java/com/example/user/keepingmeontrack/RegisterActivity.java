package com.example.user.keepingmeontrack;


import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.keepingmeontrack.fragments.LoginFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;

    EditText sinUpPassword;
    EditText singUPEmail;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText sinUpUserName = findViewById(R.id.signup_username);
       sinUpPassword = findViewById(R.id.signup_password);
        singUPEmail = findViewById(R.id.signup_email);
        Button sinUpBtn = findViewById(R.id.sing_up_btn);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        sinUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getusers();

            }
        });


                }

                public void getusers(){
                    mAuth.createUserWithEmailAndPassword(singUPEmail.getText().toString(), sinUpPassword.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Signup Success!", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
/*
                                        LoginFragment fragment = new LoginFragment();
                                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.frame_layout, fragment);
                                        transaction.commit();


                                        finish();*/
                                        Intent intent=new Intent(RegisterActivity.this,MainTabActivity.class);
                                        startActivity(intent);
                                      //  updateUI(user);
                                    } else {

                                        Toast.makeText(RegisterActivity.this, "Email address wrong or your password is too easy please change your password.", Toast.LENGTH_SHORT).show();
                                      //  updateUI(null);


                                        // ...
                                    }  }
                            });

                }




}
