package com.example.user.keepingmeontrack.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.models.CaloriCalculate;
import com.example.user.keepingmeontrack.models.Network;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CaloriCalculateAdapter extends ArrayAdapter<CaloriCalculate> {

    private static final String LOG_TAG = CaloriCalculateAdapter.class.getSimpleName();


    public CaloriCalculateAdapter(Activity context, ArrayList<CaloriCalculate> androidFlavors) {

        super(context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        convertView = LayoutInflater.from(getContext()).inflate(
                R.layout.calori_search_item, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.porsiyon_tur, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.turPorsiyon.setAdapter(adapter);
        CaloriCalculate caloriCalculate = getItem(position);
        holder.name.setText(caloriCalculate.getName());
        holder.calori.setText(""+caloriCalculate.getCalori());


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.calori)
        TextView calori;
        @BindView(R.id.porsiyon)
        EditText porsiyon;
        @BindView(R.id.turPorsiyon)
        Spinner turPorsiyon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}