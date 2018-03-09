package com.example.user.keepingmeontrack.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.models.FitnessGoal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 14.02.2018.
 */

public class FitnessGoalAdapter extends ArrayAdapter<FitnessGoal> {
    ArrayList<FitnessGoal> arrayList;

    public FitnessGoalAdapter(Activity context, ArrayList<FitnessGoal> arrayList) {
        super(context, 0, arrayList);

        //addAll(CacheHelper.getInstance(getContext()).getStoreInfo().branches);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;


        view = LayoutInflater.from(getContext()).inflate(
                R.layout.fitness_list_item, parent, false);
        holder = new ViewHolder(view);
        view.setTag(holder);


        FitnessGoal goalObje = getItem(position);
        holder.tvGoalName.setText(goalObje.getAnswer5());
        holder.explanation.setText(goalObje.getAnswer1() + " " + "for " + goalObje.getAnswer2());
        holder.result.setText(goalObje.getAnswer3());
        if (goalObje.getAnswer2().equals("Lose Weight")) {
            holder.icon.setBackgroundResource(R.drawable.calculate);

        } else if (goalObje.getAnswer2().equals("Build Muscle")) {
            holder.icon.setBackgroundResource(R.drawable.muscle);

        } else if (goalObje.getAnswer2().equals("Improve Muscle Tone")) {
            holder.icon.setBackgroundResource(R.drawable.musclearm);

        } else if (goalObje.getAnswer2().equals("Improve Flexibility")) {
            holder.icon.setBackgroundResource(R.drawable.sport);

        }

        return view;
    }


    class ViewHolder {
        @BindView(R.id.tv_goal_name)
        TextView tvGoalName;
        @BindView(R.id.explanation)
        TextView explanation;
        @BindView(R.id.result)
        TextView result;
        @BindView(R.id.goal_image)
        ImageView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

