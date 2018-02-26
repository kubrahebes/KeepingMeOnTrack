package com.example.user.keepingmeontrack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.adapters.CardsDataAdapter;
import com.example.user.keepingmeontrack.models.Item;
import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mahmoud on 2/24/2018.
 */

public class UserNetwork extends Activity {
    @BindView(R.id.btn_chat)
    ImageView btnChat;
    @BindView(R.id.btn_like)
    ImageView btnLike;
    @BindView(R.id.btn_dislike)
    ImageView btnDislike;
    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.networking_main);
        ButterKnife.bind(this);

        mCardStack = (CardStack) findViewById(R.id.card_stack);

        mCardStack.setContentResource(R.layout.networking_card_content);
//        mCardStack.setStackMargin(20);


        ArrayList<Item> goals = new ArrayList<>();

        mCardAdapter = new CardsDataAdapter(this, goals);
        mCardAdapter.add(new Item("Fitness Workout", "I will workout to shape my body.", "Mahmoud AlMuhammed"));
        mCardAdapter.add(new Item("Chest Workout", "I grow my muscle in one month.", "Rewan Ali"));
        mCardAdapter.add(new Item("Buy car ", "I will buy a Mercedes :D.", "Kubra Hebes"));
        mCardAdapter.add(new Item("Buy new Macbook", "I need to save money to buy new Mac.", "Elif"));
        mCardAdapter.add(new Item("Diet", "I will lose 3kg every week.", "Merve"));
        mCardAdapter.add(new Item("Nothing", "I will not responde on your Emails :P", "John"));
        mCardAdapter.add(new Item("Goal Name", "I wanna lose weight in two days.", "Someone"));

        mCardStack.setAdapter(mCardAdapter);
        mCardStack.setEnableLoop(!mCardStack.isEnableLoop());


        if (mCardStack.getAdapter() != null) {
            Log.i("MyActivity", "Card Stack size: " + mCardStack.getAdapter().getCount());
        }


    }

    @OnClick({R.id.btn_chat, R.id.btn_like, R.id.btn_dislike})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_chat:
                Toast.makeText(UserNetwork.this,
                        "I am chat!", Toast.LENGTH_LONG).show();
                break;
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
}
