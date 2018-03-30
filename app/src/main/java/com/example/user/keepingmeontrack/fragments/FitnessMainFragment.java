package com.example.user.keepingmeontrack.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.FinanceGoalDetail;
import com.example.user.keepingmeontrack.FitnesGoalAddActivity;
import com.example.user.keepingmeontrack.FitnessDetailActivity;
import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.adapters.FitnessGoalAdapter;
import com.example.user.keepingmeontrack.adapters.GoalAdapter;
import com.example.user.keepingmeontrack.models.FitnessGoal;
import com.example.user.keepingmeontrack.models.Goal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    Unbinder unbinder;

    FitnessGoalAdapter adapte;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String uID;
    private ProgressDialog mProgress;
    FitnessGoal value;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main_fitness, container, false);


        unbinder = ButterKnife.bind(this, rootView);
        adapte = new FitnessGoalAdapter(getActivity(), new ArrayList<FitnessGoal>());

        mProgress = new ProgressDialog(getContext());
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(true);
        mProgress.setIndeterminate(true);
        //  mProgress.show();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase").child("fitness");

        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        uID = pref.getString("uID", null);
      //  Toast.makeText(getContext(), uID, Toast.LENGTH_SHORT).show();
        getdata();
        return rootView;
    }

    /**
     * get data from the firebase
     */
    public void getdata() {

        myRef.orderByChild("uid").equalTo(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // mProgress.cancel();
                ArrayList<FitnessGoal> financeGoalList = new ArrayList<>();
                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    value = verigetir.getValue(FitnessGoal.class);
                    //  Toast.makeText(getContext(), value.getUid(), Toast.LENGTH_SHORT).show();
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
    public void setdata(final ArrayList<FitnessGoal> list) {
        if (list.isEmpty()) {
         //   Toast.makeText(getActivity(), "Empty ListView", Toast.LENGTH_SHORT).show();

        } else {
            adapte.clear();
            adapte.addAll(list);
            financeList.setAdapter(adapte);
            financeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getContext(), FitnessDetailActivity.class);
                    intent.putExtra("selectId", list.get(i).getKey());

                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab1)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), FitnesGoalAddActivity.class);
        startActivity(intent);
    }


}
