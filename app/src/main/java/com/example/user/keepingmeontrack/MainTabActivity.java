package com.example.user.keepingmeontrack;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.user.keepingmeontrack.fragments.FinancialMainFragment;
import com.example.user.keepingmeontrack.fragments.FitnessMainFragment;
import com.example.user.keepingmeontrack.swipeanimation.IntroActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainTabActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    AppBarLayout appBarLayout;
    private int[] tabIcons = {
            R.drawable.fitness_icon_2,
            R.drawable.finance_icon
    };
    private FirebaseAuth mAuth;
    TabLayout tabLayout;
    Toolbar toolbar;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        mAuth = FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = MainTabActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        appBarLayout = findViewById(R.id.appbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        FloatingActionButton addNewGoal=findViewById(R.id.fab);

        addNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainTabActivity.this,FinanceGoalAdd.class);
                startActivity(intent);
            }
        });



        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        appBarLayout.setBackgroundColor(Color.parseColor("#FFB829"));
                        toolbar.setTitle("Finance");
                        break;
                    case 1:
                        appBarLayout.setBackgroundColor(Color.parseColor("#D6376B"));
                        toolbar.setTitle("Fitness");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        setupTabIcons();


    }



    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    FinancialMainFragment financial_fragment = new FinancialMainFragment();
                    return financial_fragment;


                case 1:
                    FitnessMainFragment fitness_fragment = new FitnessMainFragment();
                    return fitness_fragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
               logout();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
    public void logout(){
        editor.putBoolean("IS_LOGIN", false);
        editor.commit();
        mAuth.signOut();
        startActivity(new Intent(MainTabActivity.this, SplashActivity.class));
        finish();
    }
}

