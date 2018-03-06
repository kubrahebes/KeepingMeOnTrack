package com.example.user.keepingmeontrack.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.not_connected_text)
    TextView notConnectedText;
    private ProgressDialog mProgress;
    GoalAdapter adapte;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main_finance, container, false);
        ButterKnife.bind(this, rootView);

        adapte = new GoalAdapter(getActivity(), new ArrayList<Goal>());
        mProgress = new ProgressDialog(getContext());
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(true);
        mProgress.setIndeterminate(true);
        mProgress.show();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase").child("finance");

        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        uID = pref.getString("uID", null);

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getdata();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle(R.string.quit);
            builder.setMessage(R.string.really_quit);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    getActivity().finish();                         //Stop the activity
                }

            })
                    .setNegativeButton(R.string.no, null)
                    .show();
            notConnectedText.setText(R.string.noconnection_String);
        }


        return rootView;
    }

    /**
     * get data from the firebase
     */
    public void getdata() {

        myRef.orderByChild("uid").equalTo(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgress.cancel();
                ArrayList<Goal> financeGoalList = new ArrayList<>();
                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    value = verigetir.getValue(Goal.class);
                    //Toast.makeText(getContext(), value.getUid(), Toast.LENGTH_SHORT).show();
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
    public void setdata(final ArrayList<Goal> list) {
        if (list.isEmpty()) {
            Toast.makeText(getActivity(), "Empty ListView", Toast.LENGTH_SHORT).show();
        } else {
            adapte.clear();
            adapte.addAll(list);
            financeGoalListview.setAdapter(adapte);
            financeGoalListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getContext(), FinanceGoalDetail.class);
                    intent.putExtra("selectId", list.get(i).getId());

                    startActivity(intent);
                }
            });
        }
    }

    public void stopActivity(){

    }
}
