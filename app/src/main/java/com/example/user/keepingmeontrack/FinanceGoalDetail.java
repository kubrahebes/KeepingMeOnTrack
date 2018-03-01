package com.example.user.keepingmeontrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.models.Goal;
import com.example.user.keepingmeontrack.models.Network;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by elifasli on 16.02.2018.
 */

public class FinanceGoalDetail extends BaseActivity {


    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    Goal value;
Goal value2;
    String selectedItem;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.totalMoney)
    TextView totalMoney;
    @BindView(R.id.piechart)
    PieChart piechart;
    @BindView(R.id.daily)
    ImageView daily;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.startDate)
    TextView startDate;
    @BindView(R.id.finishDate)
    TextView finishDate;
    @BindView(R.id.separator)
    View separator;
    @BindView(R.id.relative2)
    RelativeLayout relative2;
    @BindView(R.id.allowance)
    ImageView allowance;
    @BindView(R.id.dailyallowance)
    TextView dailyallowance;
    @BindView(R.id.totalmoney)
    TextView totalmoney;
    @BindView(R.id.relative3)
    RelativeLayout relative3;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.totalSaving)
    TextView totalSaving;
    @BindView(R.id.totalGoal)
    TextView totalGoal;
    String userNAme;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_goal_detail);
        ButterKnife.bind(this);
        pref = FinanceGoalDetail.this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        userNAme=getUser().getUserName();
        Intent intent = getIntent();
        selectedItem = intent.getStringExtra("selectId");


        /**
         *Firebase connection
         */
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase").child("finance");
        myRef2 = database.getReference("datbase");
        getdata();
        setGraph();

    }


    /**
     * get data from the firebase
     */
    public void getdata() {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {

                    try {
                        value = verigetir.getValue(Goal.class);

                        if (selectedItem.equals(value.getId())) {
                            setdata(value);
                            value2=value;
                        }

                    }
                    catch (Exception e) {
                        // This will catch any exception, because they are all descended from Exception
                        System.out.println("Error " + e.getMessage());
                    }


                    //  Toast.makeText(FinanceGoalDetail.this, ""+value.getId().equals(selectedItem), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(FinanceGoalDetail.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }

    /**
     * Set Adapter
     */
    public void setdata(Goal obje) {
        title.setText(obje.getName());
        totalMoney.setText(obje.getTotalMoney() + " $");
        startDate.setText(obje.getStartDate());
        finishDate.setText(obje.getEndDate());
        totalGoal.setText(obje.getTotalMoney() + " $");
        totalSaving.setText(obje.getDailyAllowance() + "  $");
        //  calculateDate(obje.getStartDate(),obje.getEndDate());


    }

    /*
        public void calculateDate(String startDate, String finishDate){
            int startDateInt=Integer.parseInt(startDate);
            int finishDateInt=Integer.parseInt(finishDate);
            Toast.makeText(this,  "" +   finishDateInt, Toast.LENGTH_SHORT).show();


        }*/
    public void setGraph() {

        piechart.setUsePercentValues(true);
        piechart.getDescription().setEnabled(false);
        piechart.setExtraOffsets(0, 0, 0, 0);

        piechart.setDragDecelerationFrictionCoef(0.95f);

        //içi dolu istersen hole false yap
        piechart.setDrawHoleEnabled(false);
        piechart.setHoleColor(Color.WHITE);
        piechart.setTransparentCircleRadius(61f);
        //şeffaflık kalksın diye transparanı 30f yap.

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(34f, "Hire"));
        yValues.add(new PieEntry(36f, "Foods"));
        yValues.add(new PieEntry(30f, "Technology"));

        piechart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Finance");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        piechart.setData(data);


    }


    /**
     * Menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_goal_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Toast.makeText(FinanceGoalDetail.this, "succsess", Toast.LENGTH_SHORT).show();
            String key = myRef.child("networking").push().getKey();
            Network newGoal = new Network(value2.getName(),"I will workout to shape my body",userNAme,0,0,key);
            myRef2.child("networking").child(key).setValue(newGoal);



        } else if (id == R.id.action_delete) {

        }


        return super.onOptionsItemSelected(item);
    }
}
