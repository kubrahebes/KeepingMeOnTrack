package com.example.user.keepingmeontrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.fragments.FinancialMainFragment;
import com.example.user.keepingmeontrack.models.Goal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDate;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ReCodedMerve on 19/02/2018.
 */

public class FinanceDebitAdd extends BaseActivity {
    @BindView(R.id.imgDebitName)
    ImageView imgDebitName;
    @BindView(R.id.debitName)
    EditText debitName;
    @BindView(R.id.imgHowMuch)
    ImageView imgHowMuch;
    @BindView(R.id.debitTotalMoney)
    EditText debitTotalMoney;
    @BindView(R.id.imgDailyAllowance)
    ImageView imgDailyAllowance;
    @BindView(R.id.debitDailyAllowance)
    EditText debitDailyAllowance;
    @BindView(R.id.imgReminding)
    ImageView imgReminding;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.relative3)
    RelativeLayout relative3;
    @BindView(R.id.relative4)
    RelativeLayout relative4;
    RelativeLayout relative5;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.faizorani)
    EditText faizoranii;

    double totalWeek;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String uID;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @BindView(R.id.fab1)
    CircleButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_debit_add);
        ButterKnife.bind(this);


        Toolbar toolbar = findViewById(R.id.toolbar_finance);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        pref = FinanceDebitAdd.this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        uID = pref.getString("uID",null);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase");



        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reminding_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


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
        double totalmoney;
        double weekmoney;


        totalmoney = Double.parseDouble(debitTotalMoney.getText().toString());
        weekmoney = Double.parseDouble(debitDailyAllowance.getText().toString());
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
        int x= ((int) totalWeek);
        x=x*7;
        //Toast.makeText(this, " " + totalWeek, Toast.LENGTH_SHORT).show();
        LocalDate later =timee.plusDays(x);
      //  Toast.makeText(this, ""+later, Toast.LENGTH_SHORT).show();
        return later;

    }

    @OnClick(R.id.fab1)
    public void onViewClicked() {
        if (validateControl()) {
            Toast.makeText(FinanceDebitAdd.this, "succsess", Toast.LENGTH_SHORT).show();
            LocalDate now = new LocalDate();
            LocalDate laterr =calculate(now);
            String key = myRef.child("finance").push().getKey();
            Goal newGoal = new Goal(key, uID, debitName.getText().toString(), debitTotalMoney.getText().toString(), debitDailyAllowance.getText().toString(),
                    now.toString(), laterr.toString(), spinner.getSelectedItem().toString(), totalWeek,2);

            myRef.child("finance").child(key).setValue(newGoal);

         
          Intent intent =new Intent(FinanceDebitAdd.this,MainTabActivity.class);
          startActivity(intent);



        } else {
            Toast.makeText(FinanceDebitAdd.this, "unsucsess", Toast.LENGTH_SHORT).show();
        }
    }
    //   public float RatinBarClass{

    //}
    public boolean validateControl() {

        if (debitName.getText().toString().equals("") || debitTotalMoney.getText().toString().equals("") || debitDailyAllowance.getText().toString().equals("")) {

            return false;
        } else {
            return true;
        }
    }
}

