package com.example.user.keepingmeontrack;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.user.keepingmeontrack.fragments.LoginFragment;
import com.example.user.keepingmeontrack.models.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText sinUpUserName = findViewById(R.id.signup_username);
        final EditText sinUpPassword = findViewById(R.id.signup_password);
        final EditText singUPEmail = findViewById(R.id.signup_email);
        Button sinUpBtn = findViewById(R.id.sing_up_btn);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        sinUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sinUpUserName.getText().toString().isEmpty() || sinUpPassword.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "You need to fill everywhere.", Toast.LENGTH_SHORT).show();
                } else if (isValidEmail(singUPEmail.getText().toString().trim()) == false) {
                    Toast.makeText(RegisterActivity.this, "You need to right your mail address correctly.", Toast.LENGTH_SHORT).show();
                } else {
                    Users newUser = new Users(sinUpUserName.getText().toString(), sinUpPassword.getText().toString(), singUPEmail.getText().toString());
                    myRef.push().setValue(newUser);

                }
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void returnLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginFragment.class);
        startActivity(intent);

    }
}
