package com.example.user.keepingmeontrack.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.adapters.GoalAdapter;
import com.example.user.keepingmeontrack.models.Goal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by User on 06.02.2018.
 */

public class FinancialMainFragment extends Fragment {
    ArrayList<Goal> financeGoalList;
    ListView financeGoalListview;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("datbase").child("finance");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main_finance, container, false);
        // getdata();
        financeGoalListview = rootView.findViewById(R.id.financeList);


        return rootView;
    }

    public void getdata() {


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Goal> financeGoalList = new ArrayList<>();
                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    Goal value = verigetir.getValue(Goal.class);

                    if (value.getUid().equals("kubra")) {

                        financeGoalList.add(new Goal(value.getName(), value.getStartDate(), value.getEndDate(), value.getReminding(), value.getType()));
                        setdata(financeGoalList);
                    } else {
                        Toast.makeText(getContext(), "hatata", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                // Failed to read value
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void setdata(ArrayList<Goal> list) {

        GoalAdapter adapte = new GoalAdapter(getContext(), R.layout.finance_list_item, list);
        financeGoalListview.setAdapter(adapte);

    }
}
