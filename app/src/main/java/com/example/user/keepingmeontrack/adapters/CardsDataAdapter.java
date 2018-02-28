package com.example.user.keepingmeontrack.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.keepingmeontrack.R;
import com.example.user.keepingmeontrack.models.Item;

import java.util.ArrayList;


/*
* {@link WordAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
* based on a data source, which is a list of {@link AndroidFlavor} objects.
* */
public class CardsDataAdapter extends ArrayAdapter<Item> {

    private static final String LOG_TAG = CardsDataAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param androidFlavors A List of AndroidFlavor objects to display in a list
     */
    public CardsDataAdapter(Activity context, ArrayList<Item> androidFlavors) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, androidFlavors);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.networking_card_content, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Item currentGoal = getItem(position);

        TextView txtGoalName =  listItemView.findViewById(R.id.goal_name);
        TextView txtGoalDesc =  listItemView.findViewById(R.id.goal_content);
        TextView txtUserName =  listItemView.findViewById(R.id.user_name);

        txtGoalName.setText(currentGoal.getGoalName());
        txtGoalDesc.setText(currentGoal.getGoalDesc());
        txtUserName.setText(currentGoal.getUserName());

        return listItemView;
    }

}