package com.example.user.keepingmeontrack.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.FinanceGoalAdd;
import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.adapters.GoalAdapter;
import com.example.user.keepingmeontrack.models.Goal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 06.02.2018.
 */

public class FinancialMainFragment extends Fragment {

    @BindView(R.id.financeList)
    ListView financeGoalListview;

    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String uID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main_finance, container, false);
        ButterKnife.bind(this, rootView);

        /**
         *Firebase connection
         */
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase").child("finance");
        getdata();

        /**
         * SharedPreference for user id
         */
        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        uID = pref.getString("uID", null);

        return rootView;
    }

    /**
     * get data from the firebase
     */
    public void getdata() {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Goal> financeGoalList = new ArrayList<>();
                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {

                    Goal value = verigetir.getValue(Goal.class);
                        financeGoalList.add(value);
                }
                setdata(financeGoalList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }

    /**
     * Set Adapter
     */
    public void setdata(ArrayList<Goal> list) {

        GoalAdapter adapte = new GoalAdapter(getContext(), list);
        financeGoalListview.setAdapter(adapte);

    }
}
