package com.example.tmizzle2005.test.adapter;

/**
 * Created by tmizzle2005 on 3/27/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.example.tmizzle2005.test.R;
import com.example.tmizzle2005.test.activity.KingdomInfo;
import com.example.tmizzle2005.test.activity.Nicer_Quest;
import com.example.tmizzle2005.test.activity.SignUp;

import java.util.ArrayList;

public class NicerQuestAdapter extends BaseAdapter {
    private Context context;
    private String name = "";
    private int size = 0;
    public NicerQuestAdapter(Context context,String name, int size) {
        this.context = context;
        this.name = name;
        this.size = size;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences prefs = context.getSharedPreferences("savedData", Context.MODE_PRIVATE);
        LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.quest_gridview, null);
        TextView txt = (TextView) v.findViewById(R.id.title);
        ArrayList<String> names = KingdomInfo.map.get(name);
        ImageView img = (ImageView)v.findViewById(R.id.isComp);
        RobotoTextView t = (RobotoTextView) v.findViewById(R.id.isCom);
        String result = prefs.getString(names.get(position) + prefs.getString("storedEmail","") + new SignUp().getDifferKey(), "");
        if(result.equals("complete")) {
            img.setImageResource(R.drawable.complete);
            t.setText("Complete");

        } else {
            img.setImageResource(R.drawable.incomplete);
            t.setText("Incomplete");
        }
        txt.setText(names.get(position));
        return v;
    }

    @Override public int getCount() {
        return size;
    }

    @Override public String getItem(int position) {
        return "";
    }

    @Override public long getItemId(int position) {
        return position;
    }
}

