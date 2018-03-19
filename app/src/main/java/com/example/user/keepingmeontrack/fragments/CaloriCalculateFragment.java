package com.example.user.keepingmeontrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.keepingmeontrack.FitnessCaloriActivity;
import com.example.user.keepingmeontrack.FitnessDetailActivity;
import com.example.user.keepingmeontrack.FoodCaloriActivity;
import com.example.user.keepingmeontrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 06.02.2018.
 */

public class CaloriCalculateFragment extends Fragment {


    @BindView(R.id.foodCalori)
    FloatingActionButton foodCalori;
    @BindView(R.id.fitnessCalori)
    FloatingActionButton fitnessCalori;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.calori_calculate_fragment, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }



    @OnClick({R.id.foodCalori, R.id.fitnessCalori})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.foodCalori:
                Intent intent=new Intent(getContext(), FoodCaloriActivity.class);
                startActivity(intent);
                break;
            case R.id.fitnessCalori:
                Intent intent1=new Intent(getContext(), FitnessCaloriActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
