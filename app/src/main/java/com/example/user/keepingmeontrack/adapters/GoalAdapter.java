package com.example.user.keepingmeontrack.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.models.Goal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 14.02.2018.
 */

public class GoalAdapter extends ArrayAdapter<Goal> {
    ArrayList<Goal> arrayList;

    public GoalAdapter(Activity context, ArrayList <Goal> arrayList) {
        super(context, 0,arrayList);

        //addAll(CacheHelper.getInstance(getContext()).getStoreInfo().branches);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.finance_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        Goal goalObje = getItem(position);
        holder.tvGoalName.setText(goalObje.getName());
        holder.firstDay.setText(goalObje.getStartDate());
        holder.finishDay.setText(goalObje.getEndDate());
        if (goalObje.getType() == 1) {
            holder.goalTextView.setText("GOAL");
            holder.goalImage.setBackgroundResource(R.drawable.goalicon);
        } else {
            holder.goalTextView.setText("DEBT");
            holder.goalImage.setBackgroundResource(R.drawable.debiticon);
        }
        return view;
    }


    class ViewHolder {
        @BindView(R.id.goal_image)
        ImageView goalImage;
        @BindView(R.id.tv_goal_name)
        TextView tvGoalName;
        @BindView(R.id.first_day)
        TextView firstDay;
        @BindView(R.id.finish_day)
        TextView finishDay;
        @BindView(R.id.goal)
        TextView goalTextView;
        @BindView(R.id.days)
        TextView days;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

