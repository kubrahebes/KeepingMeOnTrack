package com.example.user.keepingmeontrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.user.keepingmeontrack.swipeanimation.IntroActivity;

/**
 * Created by Mahmoud on 1/31/2018.
 */

public class SplashActivity extends AppCompatActivity {

    // create the sheredPreference for user


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /**
     * Called when the activity is first created.
     */


    Thread splashTread;
    Animation anim;
    ImageView img;

    SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);




        img = (ImageView) findViewById(R.id.splash);
        anim = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        img.setAnimation(anim);

        pref = SplashActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pref.getBoolean("IS_LOGIN", false)) {
                    startActivity(new Intent(SplashActivity.this, MainTabActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                }
            }
        }, 3000);


    }

 /*   private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        ImageView iv = findViewById(R.id.splash);
        //iv.clearAnimation();
        anim = AnimationUtils.loadAnimation(this, R.anim.zoom_in);

        anim.reset();
        iv.startAnimation(anim);

    }

    */
}