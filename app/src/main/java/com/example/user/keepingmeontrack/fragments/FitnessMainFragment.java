package com.example.user.keepingmeontrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.user.keepingmeontrack.FitnesGoalAddActivity;
import com.example.user.keepingmeontrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by User on 06.02.2018.
 */

public class FitnessMainFragment extends Fragment {
    @BindView(R.id.financeList)
    ListView financeList;
    @BindView(R.id.addNweGoal)
    FloatingActionButton addNweGoal;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main_fitness, container, false);


        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.addNweGoal)
    public void onViewClicked() {
        Intent intent=new Intent(getContext(), FitnesGoalAddActivity.class);
        startActivity(intent);
    }
}
