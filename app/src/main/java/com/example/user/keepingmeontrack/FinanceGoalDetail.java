package com.example.user.keepingmeontrack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.models.Goal;
import com.example.user.keepingmeontrack.models.Network;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
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
    @BindView(R.id.barchart)
    BarChart barchart;
    String userNAme;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button btnOpenDialog;
    TextView textInfo;
    String goalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_goal_detail);

        ButterKnife.bind(this);
        pref = FinanceGoalDetail.this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        userNAme = getUser().getUserName();
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
                            value2 = value;
                        }

                    } catch (Exception e) {
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
        goalId = obje.getId();
        //  calculateDate(obje.getStartDate(),obje.getEndDate());


    }

    /*
        public void calculateDate(String startDate, String finishDate){
            int startDateInt=Integer.parseInt(startDate);
            int finishDateInt=Integer.parseInt(finishDate);
            Toast.makeText(this,  "" +   finishDateInt, Toast.LENGTH_SHORT).show();


        }*/
    public void setGraph() {

        barchart.getDescription().setEnabled(false);
        setData(3);
        barchart.setFitBars(true);

    }

    private void setData(int count) {
        ArrayList<BarEntry> yvals = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * 200);
            yvals.add(new BarEntry(i, (int) value));
        }
        BarDataSet set = new BarDataSet(yvals, "Data Set ");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);


        BarData data = new BarData(set);
        barchart.setData(data);
        barchart.invalidate();
        barchart.animateY(200);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(FinanceGoalDetail.this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(R.string.delete);
        builder.setMessage(R.string.context);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                myRef.child(goalId).removeValue();
                Toast.makeText(FinanceGoalDetail.this, "Your goal was Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FinanceGoalDetail.this, MainTabActivity.class);
                startActivity(intent);

            }

        })
                .setNegativeButton(R.string.no, null)
                .show();

    }


    private void openDialog() {
        LayoutInflater inflater = LayoutInflater.from(FinanceGoalDetail.this);
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
                Network newGoal = new Network(value2.getName(), subEditText.getText().toString(), userNAme, 0, 0, key, FirebaseAuth.getInstance().getCurrentUser().getUid());
                myRef2.child("networking").child(key).setValue(newGoal);
                Intent intent = new Intent(FinanceGoalDetail.this, MainTabActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FinanceGoalDetail.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

}
