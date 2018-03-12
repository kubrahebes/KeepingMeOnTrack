package com.example.user.keepingmeontrack;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

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

public class RegisterActivity extends BaseActivity {
    public boolean flag=false;

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
    private ProgressDialog mProgress;
    private Toast toast = null;
    SharedPreferences preferences;
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

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

        // Hook up the VideoView to our UI.
        videoBG = (VideoView) findViewById(R.id.videoView);

        // Build your video Uri
        Uri uri = Uri.parse("android.resource://" // First start with this,
                + getPackageName() // then retrieve your package name,
                + "/" // add a slash,
                + R.raw.sec_4); // and then finally add your video resource. Make sure it is stored
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



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase");
        mAuth = FirebaseAuth.getInstance();

        //Progress dialog for logging screen. Shows the user a progress while he is logging.
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Registering...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(true);
        mProgress.setIndeterminate(true);


    }

    public void getusers() {
        mAuth.createUserWithEmailAndPassword(signupEmail.getText().toString(), signupPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Signup Success!", Toast.LENGTH_SHORT).show();

                            //create a table in the firebase and add the new user information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Users newUser = new Users(signupUsername.getText().toString(), user.getEmail(), user.getUid());
                            myRef.child("users").child(user.getUid()).setValue(newUser);
                            saveUser(newUser);
                            // come back to login screen

                            LoginFragment fragment = new LoginFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, fragment);
                            transaction.commit();
                            finish();
                        }
                        else if(!task.isSuccessful() && flag )
                        {
                            mProgress.dismiss();
                            Toast.makeText(RegisterActivity.this, "Email address wrong or your password is too easy please change your password.", Toast.LENGTH_SHORT).show();}


                    }

                });

    }
    @OnClick(R.id.sing_up_btn)
    public void onViewClicked() {
        mProgress.show();  //Shows the progress dialog.
        isonline();//Checking if the user is online or not.
        getusers();
    }

    //Method for checking if there is an internet or not.
    public void isonline() {

        ConnectivityManager conMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            mProgress.hide();
            //Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, It appears you don't have Internet connection!");
            alert.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            alert.show();

        }
        else {
            flag=true;
        }

    }

    // I override onPause(), onResume(), and onDestroy() to properly handle the video when the user get back to app when he exits.


    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}
