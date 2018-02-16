package com.example.user.keepingmeontrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static butterknife.ButterKnife.findById;

import com.example.user.keepingmeontrack.models.Goal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by elifasli on 12.02.2018.
 */

public class FinanceGoalAdd extends AppCompatActivity {
    EditText addGoalName,
            addTotalMoney,
            addDailyAllowance,
            addStartDate,
            addEndDate,
            addreminding;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String uID;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_goal_add);
        ButterKnife.bind(this);


       toolbar.setTitle(R.string.finance_goal_tab_title);

        addGoalName = findViewById(R.id.goalName);
        addTotalMoney = findViewById(R.id.totalMoney);
        addDailyAllowance = findViewById(R.id.dailyAllowance);
        addStartDate = findViewById(R.id.startDate);
        addEndDate = findViewById(R.id.finishDate);
        addreminding = findViewById(R.id.reminding);




        pref = FinanceGoalAdd.this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        uID = pref.getString("uID", null);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase");

        FloatingActionButton floatingActionButton = findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateControl()) {
                    Toast.makeText(FinanceGoalAdd.this, "succsess", Toast.LENGTH_SHORT).show();
                    Goal newGoal = new Goal(uID, addGoalName.getText().toString(), addTotalMoney.getText().toString(), addDailyAllowance.getText().toString(),
                            addStartDate.getText().toString(), addEndDate.getText().toString(), addreminding.getText().toString(), 1);
                    myRef.child("finance").push().setValue(newGoal);


                } else {
                    Toast.makeText(FinanceGoalAdd.this, "unsucsess", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    public boolean validateControl() {

        if (addGoalName.getText().toString().equals("") || addTotalMoney.getText().toString().equals("") || addDailyAllowance.getText().toString().equals("") || addreminding.getText().toString().equals(" ")) {

            return false;
        } else {
            return true;
        }
    }
}
