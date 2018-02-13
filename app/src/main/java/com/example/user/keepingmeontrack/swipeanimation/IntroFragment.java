package com.example.user.keepingmeontrack.swipeanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Mahmoud on 2/05/2018.
 */

public class IntroFragment extends Fragment {

    private static final String PAGE = "page";
    private static final String IMAGE = "image";
    EditText sinInUserName;
    EditText sinUInPassword;
    private FirebaseAuth mAuth;
    private int mPage, IMAGE2;

    public static IntroFragment newInstance(int page, int image3) {
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        b.putInt(PAGE, page);
        b.putInt(IMAGE, image3);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
        mPage = getArguments().getInt(PAGE);

        if (!getArguments().containsKey(IMAGE))
            throw new RuntimeException("Fragment must contain a \"" + IMAGE + "\" argument!");
        IMAGE2 = getArguments().getInt(IMAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Select a layout based on the current page
        int layoutResId = 0;
        switch (mPage) {
            case 0:
                layoutResId = R.layout.intro_fragment_layout_1;
                break;
            case 1:
                layoutResId = R.layout.intro_fragment_layout_2;
                break;
            case 2:
                layoutResId = R.layout.fragment_login;
                break;

        }
        // Inflate the layout resource file
        View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);

        // Set the current page index as the View's tag (useful in the PageTransformer)

        if (layoutResId == R.layout.fragment_login) {
            final TextView signIn = view.findViewById(R.id.sing_in_btn);
            final TextView registerBtn = view.findViewById(R.id.register_btn);
            sinInUserName = view.findViewById(R.id.user_name_edt);
            sinUInPassword = view.findViewById(R.id.password_edt);
            final TextView forgotPwd = view.findViewById(R.id.login_forget_click);
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), RegisterActivity.class);
                    startActivity(intent);
                }
            });

            signIn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    LoginUser(sinInUserName.getText().toString(), sinUInPassword.getText().toString());
                }
            });
            forgotPwd.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    showAddProductDialog();
                }
            });


        }

        view.setTag(mPage);

        return view;
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
                            Toast.makeText(getContext(), "succsess", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the background color of the root view to the color specified in newInstance()
        View background = view.findViewById(R.id.intro_background);
        background.setBackgroundResource(IMAGE2);
    }


    private void showAddProductDialog() {
        ForgotPassDialog dialog = new ForgotPassDialog();
        dialog.show(getActivity().getFragmentManager(), "example");

    }
}