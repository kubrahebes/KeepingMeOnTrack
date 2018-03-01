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



public class CardsDataAdapter extends ArrayAdapter<Network> {

    private static final String LOG_TAG = CardsDataAdapter.class.getSimpleName();


    public CardsDataAdapter(Activity context, ArrayList<Network> androidFlavors) {

        super(context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.networking_card_content, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Network currentGoal = getItem(position);

        TextView txtGoalName =  listItemView.findViewById(R.id.goal_name);
        TextView txtGoalDesc =  listItemView.findViewById(R.id.goal_content);
        TextView txtUserName =  listItemView.findViewById(R.id.user_name);

        txtGoalName.setText(currentGoal.getGoalName());
        txtGoalDesc.setText(currentGoal.getGoalDesc());
        txtUserName.setText(currentGoal.getUserName());

        return listItemView;
    }

}