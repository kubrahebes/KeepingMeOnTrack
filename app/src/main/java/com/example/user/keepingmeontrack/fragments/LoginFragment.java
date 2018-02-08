package com.example.user.keepingmeontrack.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.RegisterActivity;

/**
 * Created by User on 06.02.2018.
 */

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login , container, false);
        Button singInBtn=rootView.findViewById(R.id.register_btn);
        singInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        return rootView;}
}
