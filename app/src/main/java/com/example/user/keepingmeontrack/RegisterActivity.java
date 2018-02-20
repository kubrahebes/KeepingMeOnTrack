package com.example.user.keepingmeontrack;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.fragments.LoginFragment;
import com.example.user.keepingmeontrack.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseDatabase database;
    @BindView(R.id.signup_icon)
    ImageView signupIcon;
    @BindView(R.id.signup_username)
    EditText signupUsername;
    @BindView(R.id.signup_email)
    EditText signupEmail;
    @BindView(R.id.signup_password)
    EditText signupPassword;
    @BindView(R.id.sing_up_btn)
    Button singUpBtn;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    private FirebaseAuth mAuth;


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
        ButterKnife.bind(this);




        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase");
        mAuth = FirebaseAuth.getInstance();



    }

    public void getusers() {
        mAuth.createUserWithEmailAndPassword(signupEmail.getText().toString(), signupPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "createUserWithEmail:success", Toast.LENGTH_SHORT).show();

                            //create a table in the firebase and add the new user information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Users newUser = new Users(signupUsername.getText().toString(), user.getEmail(), user.getUid());
                            myRef.child("users").child(user.getUid()).setValue(newUser);

                            // come back to login screen

                            LoginFragment fragment = new LoginFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, fragment);
                            transaction.commit();


                            finish();

                        } else {

                            Toast.makeText(RegisterActivity.this, "Email address wrong or your password is too easy please change your password.", Toast.LENGTH_SHORT).show();
                            //  updateUI(null);


                            // ...
                        }
                    }
                });

    }


    @OnClick(R.id.sing_up_btn)
    public void onViewClicked() {
        getusers();
    }
}
