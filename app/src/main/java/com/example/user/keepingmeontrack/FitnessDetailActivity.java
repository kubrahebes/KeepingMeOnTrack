package com.example.user.keepingmeontrack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.models.FitnessGoal;
import com.example.user.keepingmeontrack.models.Network;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FitnessDetailActivity extends BaseActivity {
    String userNAme;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button btnOpenDialog;
    TextView textInfo;
    String goalId;
    PieChart pieChart;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    String selectedItem;
    FitnessGoal value, value2;
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
    @BindView(R.id.allowance)
    ImageView allowance;
    @BindView(R.id.dailyallowance)
    TextView dailyallowance;
    @BindView(R.id.totalSaving)
    TextView totalSaving;
    @BindView(R.id.totalmoney)
    TextView totalmoney;
    @BindView(R.id.totalGoal)
    TextView totalGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_detail);


        ButterKnife.bind(this);
        pref = FitnessDetailActivity.this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        userNAme = getUser().getUserName();
        Intent intent = getIntent();
        selectedItem = intent.getStringExtra("selectId");


        /**
         *Firebase connection
         */
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase").child("fitness");
        myRef2 = database.getReference("datbase");
        getdata();


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
                        value = verigetir.getValue(FitnessGoal.class);

                        if (selectedItem.equals(value.getKey())) {
                            setdata(value);
                            value2 = value;
                        }
                    } catch (Exception e) {

                        System.out.println("Error " + e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(FitnessDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }


    /**
     * Set Adapter
     */
    public void setdata(FitnessGoal obje) {
        title.setText(obje.getAnswer5());
        totalMoney.setText("");
        startDate.setText(obje.getStartTime());
        finishDate.setText(obje.getEndDate());
        totalGoal.setText(obje.getAnswer2());
        totalSaving.setText(obje.getAnswer3());
        goalId = obje.getKey();
        DateTime now_ = new DateTime();
        DateTime startDate_ = new DateTime(obje.getStartTime());
        DateTime endDate_ = new DateTime(obje.getEndDate());


        setGraph(now_, startDate_, endDate_);


        // int restMoney = toplamPara - savingMoney;
        // setGraph(toplamPara, savingMoney, restMoney);
    }

    public void setGraph(DateTime now, DateTime startDate, DateTime endTime) {
        Duration duration = new Duration(startDate, now);
        int gecenSure = (int) duration.getStandardDays();
        Duration duration2 = new Duration(now, endTime);
        int kalanSure = (int) duration2.getStandardDays();
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, 0);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        //içi dolu istersen hole false yap
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(30f);
        //şeffaflık kalksın diye transparanı 30f yap.

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(kalanSure, ""));
        yValues.add(new PieEntry(gecenSure, ""));

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Fitness");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(3f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);


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

            openDialog();


        } else if (id == R.id.action_delete) {

            delete();

        }


        return super.onOptionsItemSelected(item);
    }


    public void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FitnessDetailActivity.this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(R.string.delete);
        builder.setMessage(R.string.context);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                myRef.child(goalId).removeValue();
                Toast.makeText(FitnessDetailActivity.this, "Your goal was Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FitnessDetailActivity.this, MainTabActivity.class);
                startActivity(intent);

            }

        })
                .setNegativeButton(R.string.no, null)
                .show();

    }


    private void openDialog() {
        LayoutInflater inflater = LayoutInflater.from(FitnessDetailActivity.this);
        View subView = inflater.inflate(R.layout.share_pop, null);
        final EditText subEditText = (EditText) subView.findViewById(R.id.dialogEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attention");
        builder.setMessage("Please describe your Goal");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String key = myRef.child("networking").push().getKey();
                Network newGoal = new Network(value2.getAnswer5(), subEditText.getText().toString(), userNAme, 0, 0, key, FirebaseAuth.getInstance().getCurrentUser().getUid());
                myRef2.child("networking").child(key).setValue(newGoal);
                Intent intent = new Intent(FitnessDetailActivity.this, MainTabActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FitnessDetailActivity.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

}