package com.example.user.keepingmeontrack.swipeanimation;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.user.keepingmeontrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Mahmoud on 2/05/2018.
 */


public class IntroActivity extends AppCompatActivity {


    /**
     * this is the introactivity it will set adapter for transitions and pagetransformer
     * now we go to pagetransformer it will swipe between pages.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        mViewPager =  findViewById(R.id.viewpager);

        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(new IntroAdapter(getSupportFragmentManager()));

        // Set a PageTransformer
        mViewPager.setPageTransformer(false, new IntroPageTransformer());
    }


}