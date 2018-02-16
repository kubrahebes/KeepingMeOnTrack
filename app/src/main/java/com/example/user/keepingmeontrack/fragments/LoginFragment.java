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
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by User on 06.02.2018.
 */

public class LoginFragment extends Fragment {

    @BindView(R.id.login_logo)
    ImageView loginLogo;
    @BindView(R.id.user_name_edt)
    EditText userNameEdt;
    @BindView(R.id.password_edt)
    EditText passwordEdt;
    @BindView(R.id.sing_in_btn)
    Button singInBtn;
    @BindView(R.id.login_forgot)
    TextView loginForgot;
    @BindView(R.id.login_forget_click)
    TextView loginForgetClick;
    @BindView(R.id.noaccount)
    TextView noaccount;
    @BindView(R.id.register_btn)
    TextView registerBtn;
    @BindView(R.id.intro_background)
    RelativeLayout introBackground;
    Unbinder unbinder;


    DatabaseReference myRef;
    FirebaseDatabase database;
    EditText sinInUserName;
    EditText sinUInPassword;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, rootView);


        return rootView;
    }

    /**
     * get user ont he firebas for login
     *
     * @param mail
     * @param password
     */

    private void LoginUser(String mail, final String password) {
        if (!validateForm()) {
            return;
        }
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

    /**
     * Validate control for the email and password EditText
     *
     * @return
     */
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

    /**
     * Dialog for  Forget password
     */

    private void showAddProductDialog() {
        ForgotPassDialog dialog = new ForgotPassDialog();
        dialog.show(getActivity().getFragmentManager(), "example");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sing_in_btn, R.id.login_forget_click, R.id.register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sing_in_btn:
                LoginUser(sinInUserName.getText().toString(), sinUInPassword.getText().toString());
                break;
            case R.id.login_forget_click:
                showAddProductDialog();
                break;
            case R.id.register_btn:
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
