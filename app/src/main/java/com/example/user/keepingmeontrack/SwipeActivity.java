package com.example.user.keepingmeontrack;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.user.keepingmeontrack.fragments.FinancialFragment;
import com.example.user.keepingmeontrack.fragments.FitnessFragment;
import com.example.user.keepingmeontrack.fragments.LoginFragment;


public class SwipeActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe__tab);

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
                    FitnessFragment fitness_fragment = new FitnessFragment();
                    return fitness_fragment;

                case 1:
                    FinancialFragment financial_fragment = new FinancialFragment();
                    return financial_fragment;
                case 2:
                    LoginFragment login_fragment = new LoginFragment();
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
