package com.example.user.keepingmeontrack.swipeanimation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.VideoView;

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
    private ProgressDialog mProgress;
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

    public static IntroFragment newInstance(int page, int image3) {
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        b.putInt(PAGE, page);
        b.putInt(IMAGE, image3);
        frag.setArguments(b);
        return frag;
    }

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Progress dialog for logging screen. Shows the user a progress while he is logging.
        mProgress = new ProgressDialog(getContext());
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(true);
        mProgress.setIndeterminate(true);


        pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();


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


            // Hook up the VideoView to our UI.
            videoBG = view.findViewById(R.id.videoViewLogin);

            // Build your video Uri
            Uri uri = Uri.parse("android.resource://" // First start with this,
                    + getActivity().getPackageName() // then retrieve your package name,
                    + "/" // add a slash,
                    + R.raw.sec_1); // and then finally add your video resource. Make sure it is stored
            // in the raw folder.

            // Set the new Uri to our VideoView
            videoBG.setVideoURI(uri);
            // Start the VideoView
            videoBG.start();



            // Set an OnPreparedListener for our VideoView. For more information about VideoViews,
            // check out the Android Docs: https://developer.android.com/reference/android/widget/VideoView.html
            videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mMediaPlayer = mediaPlayer;
                    // We want our video to play over and over so we set looping to true.
                    mMediaPlayer.setLooping(true);
                    // We then seek to the current posistion if it has been set and play the video.
                    if (mCurrentVideoPosition != 0) {
                        mMediaPlayer.seekTo(mCurrentVideoPosition);
                        mMediaPlayer.start();
                    }
                }
            });



            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), RegisterActivity.class);
                    startActivity(intent);
                }
            });

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProgress.show(); //Shows the progress dialog.
                    isOnline(); //Checking if the user is online or not.
                    LoginUser(sinInUserName.getText().toString(), sinUInPassword.getText().toString());
                }
            });
            forgotPwd.setOnClickListener(new View.OnClickListener() {
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
                            //set the shared Preference for user who can login
                            editor.putBoolean("IS_LOGIN", true);
                            editor.putString("uID",user.getUid());

                          
                           // Toast.makeText(getContext(), user.getUid(), Toast.LENGTH_SHORT).show();


                            editor.commit();


                            //Toast.makeText(getContext(), "succsess", Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                            clearEditext();
                            Intent intent = new Intent(getContext(), MainTabActivity.class);
                            startActivity(intent);

                        } else {
                            //set the shered Preference for user who cann't login

                            editor.putBoolean("IS_LOGIN", false);
                            editor.commit();
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void clearEditext() {
        sinInUserName.setText(" ");
        sinUInPassword.setText(" ");
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

    public void isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            mProgress.dismiss();
            Toast.makeText(getContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
        }
    }
}