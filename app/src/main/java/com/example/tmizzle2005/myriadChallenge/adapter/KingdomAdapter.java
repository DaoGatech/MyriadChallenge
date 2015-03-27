package com.example.tmizzle2005.myriadChallenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.example.tmizzle2005.myriadChallenge.Model.KingdomItem;
import com.example.tmizzle2005.myriadChallenge.R;
import com.example.tmizzle2005.myriadChallenge.activity.KingdomInfo;
import com.example.tmizzle2005.myriadChallenge.activity.SignUp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This is the adapter for the list of kingdoms in RecyclerView
 */
public class KingdomAdapter extends RecyclerView.Adapter<KingdomAdapter.ViewHolder> {
    private static ArrayList<KingdomItem> itemsData = new ArrayList<KingdomItem>();
    private Context context;
    private SharedPreferences prefs;
    public KingdomAdapter(SharedPreferences prefs, Context context, ArrayList<KingdomItem> itemsData) {
        this.itemsData = itemsData;
        this.context = context;
        this.prefs = prefs;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public KingdomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {


        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kingdom_item, parent, false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //get the remaining quests
        int remain = prefs.getInt(itemsData.get(position).getName()
                +  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0);
        //get the size of quests
        int qsize = prefs.getInt(itemsData.get(position).getName()
                + "size" +  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0);
        viewHolder.txtViewTitle.setText(itemsData.get(position).getName());
        //load the picture of each kingom py Picasso library
        Picasso.with(context)
                .load(itemsData.get(position).getImage())
                .into(viewHolder.imgViewIcon);
        //show the complete and incomplete number of quests
        viewHolder.questComplete.setText("Completed: " + (qsize-remain) + "/" + qsize);

    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public  RobotoTextView txtViewTitle;
        public ImageView imgViewIcon;
        public RobotoTextView questComplete;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            final View v = itemLayoutView;
            txtViewTitle = (RobotoTextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
            questComplete = (RobotoTextView) itemLayoutView.findViewById(R.id.questcomplete);
            itemLayoutView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Intent next = new Intent(view.getContext(),KingdomInfo.class);
            next.putExtra("id", String.valueOf(itemsData.get(getPosition()).getId()));
            view.getContext().startActivity(next);
        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
