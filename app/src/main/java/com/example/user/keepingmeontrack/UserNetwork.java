package com.example.user.keepingmeontrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.adapters.CardsDataAdapter;
import com.example.user.keepingmeontrack.models.Network;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mahmoud on 2/24/2018.
 */

public class UserNetwork extends Activity {

    @BindView(R.id.btn_like)
    ImageView btnLike;
    @BindView(R.id.btn_dislike)
    ImageView btnDislike;
    @BindView(R.id.card_stack)
    CardStack cardStack;
    @BindView(R.id.back)
    ImageButton back;

    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Network shareGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.networking_main);
        ButterKnife.bind(this);




        mCardStack = findViewById(R.id.card_stack);

        mCardStack.setContentResource(R.layout.networking_card_content);
//        mCardStack.setStackMargin(20);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("datbase").child("networking");
        getdata();
    }

    @OnClick({R.id.btn_like, R.id.btn_dislike})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_like:
                Toast.makeText(UserNetwork.this,
                        "I Like that!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_dislike:
                Toast.makeText(UserNetwork.this,
                        "I don't like that!", Toast.LENGTH_LONG).show();
                break;
        }
    }


    /**
     * get data from the firebase
     */
    public void getdata() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Network> financeGoalList = new ArrayList<>();
                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    //  mProgress.cancel();
                    shareGoal = verigetir.getValue(Network.class);
                    financeGoalList.add(shareGoal);
                }
                setdata(financeGoalList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(UserNetwork.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Set Adapter
     */
    public void setdata(final ArrayList<Network> list) {
        ArrayList<Network> goal = new ArrayList<>();

        mCardAdapter = new CardsDataAdapter(this, goal);
        for (int i = 0; i < list.size(); i++) {
            mCardAdapter.add(new Network(list.get(i).getGoalName(), list.get(i).getGoalDesc(), list.get(i).getUserName(), list.get(i).getLike(), list.get(i).getDislike()));
        }
        mCardStack.setAdapter(mCardAdapter);
        // mCardStack.setEnableLoop(!mCardStack.isEnableLoop());


        if (mCardStack.getAdapter() != null) {
            Log.i("MyActivity", "Card Stack size: " + mCardStack.getAdapter().getCount());
        }

        mCardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int direction, float distance) {
                Log.d("direction", String.valueOf(direction));
                Log.d("distance", String.valueOf(distance));
                if (distance > 100) {
                    if (direction == 0 || direction == 2) {
                        int dislikeUpdate = list.get(mCardStack.getCurrIndex()).getDislike();
                        dislikeUpdate++;
                        myRef.child(list.get(mCardStack.getCurrIndex()).getId()).child("dislike")
                                .setValue(dislikeUpdate);
                    } else {
                        int likeUpdate = list.get(mCardStack.getCurrIndex()).getLike();
                        likeUpdate++;
                        myRef.child(list.get(mCardStack.getCurrIndex()).getId()).child("like")
                                .setValue(likeUpdate);
                    }
                }
                return true;
            }

            @Override
            public boolean swipeStart(int i, float v) {
                return false;
            }

            @Override
            public boolean swipeContinue(int i, float v, float v1) {
                return false;
            }

            @Override
            public void discarded(int i, int i1) {

            }

            @Override
            public void topCardTapped() {
            }
        });

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        Intent intent = new Intent(UserNetwork.this, MainTabActivity.class);
        startActivity(intent);
        finish();
    }
}
