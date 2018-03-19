package com.example.user.keepingmeontrack;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.keepingmeontrack.adapters.CaloriCalculateAdapter;
import com.example.user.keepingmeontrack.models.CaloriCalculate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FitnessCaloriActivity extends AppCompatActivity {
    @BindView(R.id.search_edit_text)
    EditText searchEditText;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.list_item)
    ListView listItem;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.textview)
    TextView textview;
    String searchWord;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    CaloriCalculate value;
    CaloriCalculateAdapter adapte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_calori);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase").child("finance");
        adapte = new CaloriCalculateAdapter(this,new ArrayList<CaloriCalculate>() );

    }

    @OnClick(R.id.search)
    public void onViewClicked() {
        searchWord = searchEditText.getText().toString();
        getData(searchWord);
    }

    public void getData(String searchWordd) {

       /* myRef.orderByChild("name").equalTo(searchWordd).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<CaloriCalculate> financeGoalList = new ArrayList<>();
                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    value = verigetir.getValue(CaloriCalculate.class);
                    //Toast.makeText(getContext(), value.getUid(), Toast.LENGTH_SHORT).show();
                    financeGoalList.add(value);
                }
                setdata(financeGoalList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(FoodCaloriActivity.this, "erorr", Toast.LENGTH_SHORT).show();

            }
        });
*/

        setdata(new ArrayList<CaloriCalculate>());

    }

    /**
     * Set Adapter
     */
    public void setdata(final ArrayList<CaloriCalculate> list) {
        listItem.setVisibility(View.VISIBLE);
        textview.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        if (list.isEmpty()) {
            list.add(new CaloriCalculate("pilav",190,"1 porsiyon"));
            list.add(new CaloriCalculate("Corba",190,"1 porsiyon"));
            list.add(new CaloriCalculate("Yemek",190,"1 porsiyon"));
            list.add(new CaloriCalculate("cikolata",190,"1 Adet"));
            adapte.clear();
            adapte.addAll(list);
            listItem.setAdapter(adapte);

            //  Toast.makeText(FoodCaloriActivity.this, "Empty ListView", Toast.LENGTH_SHORT).show();
        } else {
            adapte.clear();
            adapte.addAll(list);
            listItem.setAdapter(adapte);

        }
    }
}
