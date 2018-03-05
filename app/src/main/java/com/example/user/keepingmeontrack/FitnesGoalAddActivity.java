package com.example.user.keepingmeontrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FitnesGoalAddActivity extends AppCompatActivity {

    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.radio3)
    RadioButton radio3;
    @BindView(R.id.group1)
    RadioGroup group1;
    @BindView(R.id.radio4)
    RadioButton radio4;
    @BindView(R.id.radio5)
    RadioButton radio5;
    @BindView(R.id.radio6)
    RadioButton radio6;
    @BindView(R.id.radio7)
    RadioButton radio7;
    @BindView(R.id.radio8)
    RadioButton radio8;
    @BindView(R.id.group2)
    RadioGroup group2;
    @BindView(R.id.radio9)
    RadioButton radio9;
    @BindView(R.id.radio10)
    RadioButton radio10;
    @BindView(R.id.radio11)
    RadioButton radio11;
    @BindView(R.id.radio12)
    RadioButton radio12;
    @BindView(R.id.radio13)
    RadioButton radio13;
    @BindView(R.id.group3)
    RadioGroup group3;
    @BindView(R.id.radio14)
    RadioButton radio14;
    @BindView(R.id.radio15)
    RadioButton radio15;
    @BindView(R.id.radio16)
    RadioButton radio16;
    @BindView(R.id.radio17)
    RadioButton radio17;
    @BindView(R.id.radio18)
    RadioButton radio18;
    @BindView(R.id.group4)
    RadioGroup group4;
    @BindView(R.id.radio19)
    RadioButton radio19;
    @BindView(R.id.radio20)
    RadioButton radio20;
    @BindView(R.id.radio21)
    RadioButton radio21;
    @BindView(R.id.radio22)
    RadioButton radio22;
    @BindView(R.id.radio23)
    RadioButton radio23;
    @BindView(R.id.radio24)
    RadioButton radio24;
    @BindView(R.id.radio25)
    RadioButton radio25;
    @BindView(R.id.radio26)
    RadioButton radio26;
    @BindView(R.id.radio27)
    RadioButton radio27;
    @BindView(R.id.radio28)
    RadioButton radio28;
    @BindView(R.id.radio29)
    RadioButton radio29;
    @BindView(R.id.radio30)
    RadioButton radio30;
    @BindView(R.id.radio31)
    RadioButton radio31;
    @BindView(R.id.group5)
    RadioGroup group5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitnes_goal_add);
        ButterKnife.bind(this);
int id=group1.getCheckedRadioButtonId();

    }
}
