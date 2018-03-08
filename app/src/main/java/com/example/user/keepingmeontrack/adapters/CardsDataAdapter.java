package com.example.user.keepingmeontrack.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.models.Network;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CardsDataAdapter extends ArrayAdapter<Network> {

    private static final String LOG_TAG = CardsDataAdapter.class.getSimpleName();


    public CardsDataAdapter(Activity context, ArrayList<Network> androidFlavors) {

        super(context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.networking_card_content, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);


        Network currentGoal = getItem(position);


       holder.goalName.setText(currentGoal.getGoalName());
        holder.goalContent.setText(" "+ currentGoal.getGoalDesc());
       // holder.userName.setText(currentGoal.getUserName());
        holder.dislike.setText(" "+currentGoal.getDislike());
         holder.like.setText(" "+currentGoal.getLike());

        return convertView;
    }

    public class ViewHolder {
        @BindView(R.id.goal_name)
        TextView goalName;
        @BindView(R.id.goal_content)
        TextView goalContent;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.dislike)
        TextView dislike;
        @BindView(R.id.like)
        TextView like;

       public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}