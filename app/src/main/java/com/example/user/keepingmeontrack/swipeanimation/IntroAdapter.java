package com.example.user.keepingmeontrack.swipeanimation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.keepingmeontrack.R;

/**
 * Created by Mahmoud on 2/05/2018.
 */

public class IntroAdapter extends FragmentPagerAdapter {



    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                return IntroFragment.newInstance(position, R.drawable.fitness); // blue
            case 1:
                return IntroFragment.newInstance(position, R.drawable.financee); // green
            case 2:
                return IntroFragment.newInstance(position, R.drawable.blank_background);
            default:
                return IntroFragment.newInstance(position, R.drawable.blank_background);
        }
    }
    @Override
    public int getCount() {
        return 3;
    }

}





