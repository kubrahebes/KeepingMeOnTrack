package com.example.user.keepingmeontrack;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import com.example.user.keepingmeontrack.fragments.FinancialMainFragment;
import com.example.user.keepingmeontrack.fragments.FitnessMainFragment;
import com.google.firebase.auth.FirebaseAuth;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainTabActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.container)
    ViewPager container;
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




                  //** Here is Circled Floating Action Button code **///

        //I removed the butterknife method for old floatingbutton because the whole code now uses only Java.//

        final ImageView fabIconNew = new ImageView(this);
        // Setting the icon in the center of the Floating Action Button
        fabIconNew.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.plus));

        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconNew)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .build();
        //Here I set the position to bottom of right.

        // Created menu items which are also Floating Action Buttons
        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        LayoutParams params = new LayoutParams(190, 190);
        rLSubBuilder.setLayoutParams(params);
        //Here I set the icons sizes 190-190.


        // Created an image view for each menu item
        ImageView subMenuDebit = new ImageView(this);
        ImageView subMenuAddGoal = new ImageView(this);
        // Set the icon for each menu item
        subMenuDebit.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.debit));
        subMenuAddGoal.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.goal));


        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .setStartAngle(260)
                .setEndAngle(200)
                .addSubActionView(rLSubBuilder.setContentView(subMenuDebit).build())
                .addSubActionView(rLSubBuilder.setContentView(subMenuAddGoal).build())
                .attachTo(rightLowerButton)
                .build();

        //Here is the Floating button menu builder


        // This will listen for menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();

            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }
        });

        // OnClickListeners for each menu item button
        subMenuDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent =new Intent(MainTabActivity.this,FinanceDebitAdd.class);
           startActivity(intent);
                // Here we need to start debit fragment or activity when It finished I will add it.
            }
        });


        subMenuAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTabActivity.this, FinanceGoalAdd.class);
                startActivity(intent);
                //Here I started FinanceGoalAdd activity.
            }
        });

        //** END OF Floating Action Button code **///


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

