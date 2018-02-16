package com.example.user.keepingmeontrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.keepingmeontrack.fragments.FinancialMainFragment;
import com.example.user.keepingmeontrack.fragments.FitnessMainFragment;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainTabActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.container)
    ViewPager container;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private int[] tabIcons = {
            R.drawable.fitness_icon_2,
            R.drawable.finance_icon
    };
    private FirebaseAuth mAuth;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);

        pref = MainTabActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        container.setAdapter(mSectionsPagerAdapter);

        container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        appbar.setBackgroundColor(Color.parseColor("#FFB829"));
                        toolbar.setTitle("Finance");
                        break;
                    case 1:
                        appbar.setBackgroundColor(Color.parseColor("#D6376B"));
                        toolbar.setTitle("Fitness");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(container));
        setupTabIcons();


    }


    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(tabIcons[1]);
        tabs.getTabAt(1).setIcon(tabIcons[0]);

    }



    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(MainTabActivity.this, FinanceGoalAdd.class);
        startActivity(intent);
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

    public void logout() {
        editor.putBoolean("IS_LOGIN", false);
        editor.commit();
        mAuth.signOut();
        startActivity(new Intent(MainTabActivity.this, SplashActivity.class));
        finish();
    }
}

