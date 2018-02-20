package com.example.user.keepingmeontrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;

/**
 * Created by ReCodedMerve on 19/02/2018.
 */

public class FinanceDebitAdd extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_debit_add);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reminding_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // rating bar ekleme ama basma
        RatingBar rate = (RatingBar)findViewById(R.id.ratingBar);

//use ratings within event listner code block

        float rating = rate.getRating();
    }
 //   public float RatinBarClass{

    //}
}
