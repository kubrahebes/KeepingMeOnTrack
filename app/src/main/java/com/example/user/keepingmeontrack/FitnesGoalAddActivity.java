package com.example.user.keepingmeontrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.user.keepingmeontrack.models.FitnessGoal;
import com.example.user.keepingmeontrack.models.Goal;
import com.example.user.keepingmeontrack.models.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FitnesGoalAddActivity extends BaseActivity {


    @BindView(R.id.group1)
    RadioGroup group1;
    @BindView(R.id.group2)
    RadioGroup group2;
    @BindView(R.id.group3)
    RadioGroup group3;
    @BindView(R.id.group4)
    RadioGroup group4;
    @BindView(R.id.group5)
    RadioGroup group5;
    @BindView(R.id.fab1)
    CircleButton fab1;

    FirebaseDatabase database;
    DatabaseReference myRef;
    String uID;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    RadioButton question1;
    RadioButton question2;
    RadioButton question3;
    RadioButton question4;
    RadioButton question5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitnes_goal_add);
        ButterKnife.bind(this);
        group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = group1.getCheckedRadioButtonId();
                question1 = findViewById(id);
            }
        });
       group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {
               int id2 = group2.getCheckedRadioButtonId();
               question2 = findViewById(id2);
           }
       });
       group3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {

               int id3 = group3.getCheckedRadioButtonId();
               question3 = findViewById(id3); }
       });
       group4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {
               int id4 = group4.getCheckedRadioButtonId();
               question4 = findViewById(id4);
           }
       });
      group5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup radioGroup, int i) {
              int id5 = group5.getCheckedRadioButtonId();
              question5 = findViewById(id5);
          }
      });

        uID = getUser().getUid();

       // Users users = BaseActivity.getInstance().getUser();
        //Toast.makeText(this, users.getUserName(), Toast.LENGTH_SHORT).show();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase");

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        String key = myRef.child("fitness").push().getKey();
        FitnessGoal newGoal = new FitnessGoal( question1.getText().toString(), question2.getText().toString(), question3.getText().toString(), question4.getText().toString(), question5.getText().toString(),key,uID);

        myRef.child("fitness").child(key).setValue(newGoal);
        Intent intent=new Intent(FitnesGoalAddActivity.this,MainTabActivity.class);
        startActivity(intent);

    }
}
