package com.example.user.keepingmeontrack;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.keepingmeontrack.alarm.RemindActivity;
import com.example.user.keepingmeontrack.models.Goal;
import com.example.user.keepingmeontrack.models.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDate;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by elifasli on 12.02.2018.
 */

public class FinanceGoalAdd extends BaseActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    String uID;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


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
          double totalmoney;
          double totalWeek;
    RelativeLayout relative5;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.frame)
    FrameLayout frame;

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.fab1)
    CircleButton fab1;
    @BindView(R.id.faizorani)
    EditText faizoranii;
    long millisecond;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_goal_add);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar_finance_goal_add);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reminding_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        pref = FinanceGoalAdd.this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        uID = pref.getString("uID", null);

        Users users = BaseActivity.getInstance().getUser();
        Toast.makeText(this, users.getUserName(), Toast.LENGTH_SHORT).show();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase");


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateControl()) {
                    LocalDate now = new LocalDate();
                    LocalDate laterr = calculate(now);
                    Toast.makeText(FinanceGoalAdd.this, "succsess", Toast.LENGTH_SHORT).show();
                    String key = myRef.child("finance").push().getKey();
                    Goal newGoal = new Goal(key, uID, goalName.getText().toString(), totalMoney.getText().toString(), dailyAllowance.getText().toString(),
                            now.toString(), laterr.toString(), spinner.getSelectedItem().toString(), totalWeek, 1);

                    myRef.child("finance").child(key).setValue(newGoal);

                    setReminding();
                    Intent intent = new Intent(FinanceGoalAdd.this, MainTabActivity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(FinanceGoalAdd.this, "unsucsess", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


        private void setReminding() {


            //getting the alarm manager
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


            //creating a pending intent using the intent

            Intent i = new Intent(this, RemindActivity.class);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

            //setting the repeating alarm that will be fired every day
            //
            //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis,millisecond, pi);
            if (spinner.getSelectedItemPosition() == 1) {
                millisecond = 1000 * 60;
            } else if (spinner.getSelectedItemPosition() == 2) {
                millisecond = 1000 * 60 * 60 * 24;
                // selectedRemind = daily;
            } else if (spinner.getSelectedItemPosition() == 3) {
                millisecond = 1000 * 60 * 60 * 24 * 7;
                // selectedRemind = weekly;
            } else if (spinner.getSelectedItemPosition() == 4) {
                millisecond = 1000 * 60 * 60 * 24 * 30;
                //  selectedRemind = monthly;
            }


            am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + millisecond, millisecond, pi);


        }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public LocalDate calculate(LocalDate timee) {

        double day;
        double week;
        double faiz;
        double faizorani;

        double weekmoney;



        totalmoney = Double.parseDouble(totalMoney.getText().toString());
        weekmoney = Double.parseDouble(dailyAllowance.getText().toString());
        faizorani = Double.parseDouble(faizoranii.getText().toString());

        if (totalmoney % weekmoney == 0) {
            week = totalmoney / weekmoney;
            day = week * 7;
        } else {
            week = (totalmoney / weekmoney);
            day = (week * 7) + (totalmoney % weekmoney);
        }
        //Faiz = (Anapara x Faiz Oranı x Anaparanın Faizde Kaldığı Gün) / 36500
        faiz = ((totalmoney * faizorani * day) / 36500) * 7;
        totalmoney = totalmoney + faiz;
        if (totalmoney % weekmoney == 0) {
            totalWeek = totalmoney / weekmoney;

        } else {
            totalWeek = (totalmoney / weekmoney) + 1;

        }
       // Toast.makeText(this, ""+totalmoney, Toast.LENGTH_SHORT).show();
        int x= ((int) totalWeek);
        x=x*7;
        Toast.makeText(this, " " + totalWeek, Toast.LENGTH_SHORT).show();
        LocalDate later =timee.plusDays(x);
        Toast.makeText(this, ""+later, Toast.LENGTH_SHORT).show();
    return later;
    }

    public boolean validateControl() {

        if (goalName.getText().toString().equals("") || totalMoney.getText().toString().equals("") || dailyAllowance.getText().toString().equals("")) {

            return false;
        } else {
            return true;
        }
    }
}
