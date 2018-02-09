package com.example.user.keepingmeontrack.fragments;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import com.example.user.keepingmeontrack.AddDialogFragment;
import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.RegisterActivity;

/**
 * Created by User on 06.02.2018.
 */

public class Login_fragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,


                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_login , container, false);
        TextView registerB = rootView.findViewById(R.id.register);
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterActivity();
            }
        });

        TextView forgotB = rootView.findViewById(R.id.login_forget_click);
        forgotB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showAddProductDialog();
            }
        });



        return rootView;
}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                showRegisterActivity();

            case R.id.login_forget_click:
                showAddProductDialog();
                break;

        }
    }

    private void showAddProductDialog() {
        AddDialogFragment dialog = new AddDialogFragment ();
        dialog.show(getActivity().getFragmentManager(), "example");

    }

    private void showRegisterActivity() {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);

    }

}


