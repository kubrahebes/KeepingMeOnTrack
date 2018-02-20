package com.example.user.keepingmeontrack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.keepingmeontrack.fragments.FinancialMainFragment;
import com.example.user.keepingmeontrack.fragments.LoginFragment;
import com.example.user.keepingmeontrack.models.Goal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by elifasli on 12.02.2018.
 */

public class FinanceGoalAdd extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    String uID;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab1)
    FloatingActionButton fab1;
    @BindView(R.id.imgGoalName)
    ImageView imgGoalName;
    @BindView(R.id.goalName)
    EditText goalName;
    @BindView(R.id.relative1)
    RelativeLayout relative1;
    @BindView(R.id.imgHowMuch)
    ImageView imgHowMuch;
    @BindView(R.id.totalMoney)
    EditText totalMoney;
    @BindView(R.id.relative2)
    RelativeLayout relative2;
    @BindView(R.id.imgDailyAllowance)
    ImageView imgDailyAllowance;
    @BindView(R.id.dailyAllowance)
    EditText dailyAllowance;
    @BindView(R.id.relative3)
    RelativeLayout relative3;
    @BindView(R.id.imgReminding)
    ImageView imgReminding;

    @BindView(R.id.relative5)
    RelativeLayout relative5;
    @BindView(R.id.card_view)
    CardView cardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_goal_add);
        ButterKnife.bind(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        toolbar.setTitle(R.string.finance_goal_tab_title);
        pref = FinanceGoalAdd.this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        uID = pref.getString("uID", null);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase");


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateControl()) {
                    Toast.makeText(FinanceGoalAdd.this, "succsess", Toast.LENGTH_SHORT).show();
                    String key = myRef.child("finance").push().getKey();
                    Goal newGoal = new Goal(key, uID, goalName.getText().toString(), totalMoney.getText().toString(), dailyAllowance.getText().toString(),
                           "subat", "mart", "Every Day", 1);

                    myRef.child("finance").child(key).setValue(newGoal);


                    FinancialMainFragment fragment = new FinancialMainFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment);
                    transaction.commit();
                    finish();


                } else {
                    Toast.makeText(FinanceGoalAdd.this, "unsucsess", Toast.LENGTH_SHORT).show();
                }
            }

        });

        RatingBar rate = (RatingBar)findViewById(R.id.ratingBar);


        float rating = rate.getRating();
    }

    public boolean validateControl() {

        if (goalName.getText().toString().equals("") || totalMoney.getText().toString().equals("") || dailyAllowance.getText().toString().equals("") ) {

            return false;
        } else {
            return true;
        }
    }
}
