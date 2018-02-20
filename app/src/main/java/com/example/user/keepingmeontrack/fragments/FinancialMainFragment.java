package com.example.user.keepingmeontrack.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.FinanceGoalAdd;
import com.example.user.keepingmeontrack.FinanceGoalDetail;
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
    Goal value;
    private ProgressDialog mProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main_finance, container, false);
        ButterKnife.bind(this, rootView);


        mProgress = new ProgressDialog(getContext());
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.show();

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
                    mProgress.cancel();
                    value = verigetir.getValue(Goal.class);
                    if (value.getUid().equals(uID)) {
                        financeGoalList.add(value);
                    } else {
                        // Toast.makeText(getContext(), "Veri Yok", Toast.LENGTH_SHORT).show();
                    }
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
    public void setdata(final ArrayList<Goal> list) {

        GoalAdapter adapte = new GoalAdapter(getContext(), list);
        financeGoalListview.setAdapter(adapte);
        financeGoalListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), FinanceGoalDetail.class);
                intent.putExtra("selectId", list.get(i).getId());
                // Toast.makeText(getContext(), list.get(i).getId(), Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
    }
}
