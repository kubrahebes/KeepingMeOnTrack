package com.example.user.keepingmeontrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.models.Goal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 14.02.2018.
 */

public class GoalAdapter extends ArrayAdapter<Goal> {
    public GoalAdapter(@NonNull Context context, @NonNull List<Goal> objects) {
        super(context, 0, objects);
    }

    @BindView(R.id.goal_image)
    ImageView icon;
    @BindView(R.id.tv_goal_name)
    TextView goalName;
    @BindView(R.id.first_day)
    TextView startDate;
    @BindView(R.id.finish_day)
    TextView endDate;
    @BindView(R.id.goal)
    TextView type;
    @BindView(R.id.days)
    TextView reminding;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.finance_list_item, parent, false);

            ButterKnife.bind(this,v);
            Goal obje = getItem(position);
            if (obje != null) {

                goalName.setText(obje.getName());
                startDate.setText(obje.getStartDate());
                endDate.setText(obje.getEndDate());
            //    Toast.makeText(getContext(), obje.getName(), Toast.LENGTH_SHORT).show();
                if (obje.getType() == 1) {
                    type.setText("GOAL");
                    icon.setBackgroundResource(R.drawable.goalicon);

                } else {
                    type.setText("DEBIT");
                    icon.setBackgroundResource(R.drawable.debiticon);
                }
                reminding.setText(obje.getReminding());
            }
        }
        return v;
    }
}
