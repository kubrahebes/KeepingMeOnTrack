package com.example.user.keepingmeontrack.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.models.Goal;

import java.util.List;

/**
 * Created by User on 14.02.2018.
 */

public class GoalAdapter extends ArrayAdapter<Goal> {
    public GoalAdapter(@NonNull Context context, int resource, @NonNull List<Goal> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.finance_list_item, null);
            Goal obje = getItem(position);

            ImageView icon=v.findViewById(R.id.goal_image);
            TextView goalName=v.findViewById(R.id.tv_goal_name);
            TextView startDate=v.findViewById(R.id.first_day);
            TextView endDate=v.findViewById(R.id.finish_day);
            TextView type=v.findViewById(R.id.goal);
            TextView reminding=v.findViewById(R.id.days);


            goalName.setText(obje.getName());
            startDate.setText(obje.getStartDate());
            endDate.setText(obje.getEndDate());
            if (obje.getType()==0){
                type.setText("GOAL");
                icon.setBackgroundResource(R.drawable.alarm_icon);

            }else {
                type.setText("DEBIT");
                icon.setBackgroundResource(R.drawable.calendar_day);
            }
            reminding.setText(obje.getReminding());
        }
        return v;
    }
}
