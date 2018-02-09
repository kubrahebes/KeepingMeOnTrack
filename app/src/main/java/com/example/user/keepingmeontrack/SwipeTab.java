package com.example.user.keepingmeontrack;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.user.keepingmeontrack.fragments.Financial_fragment;
import com.example.user.keepingmeontrack.fragments.Fitness_fragment;
import com.example.user.keepingmeontrack.fragments.Login_fragment;


public class SwipeTab extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swape__tab);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fitness_fragment fitness_fragment = new Fitness_fragment();
                    return fitness_fragment;

                case 1:
                    Financial_fragment financial_fragment = new Financial_fragment();
                    return financial_fragment;
                case 2:
                    Login_fragment login_fragment = new Login_fragment();
                    return login_fragment;

                default:
                    return null;

            }
        }

        @Override
        public int getCount() {

            return 3;
        }
    }

}
