package com.example.tmizzle2005.test.activity;

/**
 * Created by tmizzle2005 on 3/27/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.example.tmizzle2005.test.R;
import com.example.tmizzle2005.test.adapter.NicerQuestAdapter;

import java.util.ArrayList;

public class Nicer_Quest extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nicer_quest);
        int questSize = Integer.parseInt(getIntent().getStringExtra("size"));
        String name = getIntent().getStringExtra("name");
        GridView gv = (GridView) findViewById(R.id.nicerQuest);
        gv.setAdapter(new NicerQuestAdapter(this,name,questSize));
        Toolbar toolbar = (Toolbar)findViewById(R.id.grid_tool_bar);
        toolbar.setTitle("Quests' Quick View");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }
}