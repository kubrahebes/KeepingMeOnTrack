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
import com.example.user.keepingmeontrack.models.CheckItem;
import com.example.user.keepingmeontrack.models.Network;
import com.google.firebase.auth.FirebaseAuth;
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
    DatabaseReference mainRef;
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
        mainRef = database.getReference("datbase");
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
                final ArrayList<Network> financeGoalList = new ArrayList<>();
                for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                    //  mProgress.cancel();
                    shareGoal = verigetir.getValue(Network.class);
                    financeGoalList.add(shareGoal);
                }


                //Append the financeGoalList and if cards is liked or not
                mainRef.child("checkedcards").orderByChild("checker").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<CheckItem> checkItems = new ArrayList<>();
                        for (DataSnapshot verigetir : dataSnapshot.getChildren()) {
                            checkItems.add( verigetir.getValue(CheckItem.class));
                        }

                        findUncheckedNetworkCards (checkItems, financeGoalList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(UserNetwork.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //
    }


    //checking if the user liked or disliked that card before. If he didn't like then SHOW the card. If he liked then HIDE the card.
    private void findUncheckedNetworkCards(ArrayList<CheckItem> checkItems, ArrayList<Network> financeGoalList) {
        ArrayList<Network> finalGoalList = new ArrayList<>();
        for (Network network : financeGoalList) {
            boolean flag = true;
            for (CheckItem item : checkItems) {
                if (item.networkId.equals(network.getId())) {
                    flag = false;
                }
            }
            if (flag) {
                finalGoalList.add(network);
            }
        }
        setdata(finalGoalList);
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
                        mainRef.child("checkedcards").push().setValue(new CheckItem(FirebaseAuth.getInstance().getCurrentUser().getUid(), list.get(mCardStack.getCurrIndex()).getId()));
                    } else {
                        int likeUpdate = list.get(mCardStack.getCurrIndex()).getLike();
                        likeUpdate++;
                        myRef.child(list.get(mCardStack.getCurrIndex()).getId()).child("like")
                                .setValue(likeUpdate);
                        mainRef.child("checkedcards").push().setValue(new CheckItem(FirebaseAuth.getInstance().getCurrentUser().getUid(), list.get(mCardStack.getCurrIndex()).getId()));
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
