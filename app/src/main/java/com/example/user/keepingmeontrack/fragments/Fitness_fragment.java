package com.example.user.keepingmeontrack.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.keepingmeontrack.R;

/**
 * Created by User on 06.02.2018.
 */

public class Fitness_fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_fitness, container, false);
       // ((Swape_Tab)getActivity()).updateStatusBarColor("#ef268f");
        return rootView;}
}
