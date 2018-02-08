package com.example.user.keepingmeontrack.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.RegisterActivity;
import com.example.user.keepingmeontrack.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by User on 06.02.2018.
 */

public class LoginFragment extends Fragment {
    DatabaseReference myRef;
    FirebaseDatabase database;
    EditText sinInUserName;
    EditText sinUInPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        sinInUserName = rootView.findViewById(R.id.user_name_edt);
        sinUInPassword = rootView.findViewById(R.id.password_edt);
        //final EditText singInEmail = rootView.findViewById(R.id.signup_email);
        Button sinInBtn = rootView.findViewById(R.id.sing_in_btn);
        Button singUpBtn = rootView.findViewById(R.id.register_btn);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");


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
                getdata();
            }
        });
        return rootView;
    }

    public void getdata() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    Users value = verigetir.getValue(Users.class);

                    if (value.getUserPassWord() != null) {
                        if (value.getUserPassWord().equals(sinUInPassword.getText().toString()) && value.getUserName().equals(sinInUserName.getText().toString())) {
                            Toast.makeText(getContext(), "successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
