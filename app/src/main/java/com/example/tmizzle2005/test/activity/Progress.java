package com.example.tmizzle2005.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import com.devspark.robototextview.widget.RobotoTextView;
import com.example.tmizzle2005.test.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class shows the current progress of the user
 */
public class Progress extends Activity {
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize the view of the activity
        setContentView(R.layout.questprogress);
        //initialize the SharedPreferences variable
        prefs = getSharedPreferences("savedData", Context.MODE_PRIVATE);
        //initialize the progressbar
        ProgressBar questComplete = (ProgressBar)findViewById (R.id.progressCircle);
        // get the remaining quests to complete
        double remain = Double.valueOf(prefs.getInt("KingdomFinished"
                + prefs.getString("storedEmail", "") + new SignUp().getDifferKey(), 0));
        //get the total number of quests available
        double qsize = Double.valueOf(prefs.getInt("totalsize"
                + prefs.getString("storedEmail", "") + new SignUp().getDifferKey(), 0));
        //get the percent complete
        double prog = ((qsize - remain)/qsize) * 100.0;
        //convert from double to int
        int progress = (int) prog;
        //set the progress bar with the percent complete
        questComplete.setProgress(progress);
        //initilize the percent text
        RobotoTextView percent = (RobotoTextView) findViewById(R.id.percent);
        percent.setText(String.valueOf(progress) + "%");
        // get the current ti,e
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        //initialize the toolbar. Set the title to be the name's progress
        //set the subtitle to be the current time
        //set the back button to finish the activity
        Toolbar tool = (Toolbar) findViewById(R.id.progress_tool_bar);
        tool.setTitle(prefs.getString("storedName","") + "'s progress");
        tool.setSubtitle(dateFormat.format(date));
        tool.setNavigationIcon(R.drawable.ic_back);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // if the user clicks the logout button in the menu
        if (id == R.id.logout) {
            //clear the store email in our Sharedpreferences
            prefs.edit().remove("storedEmail").commit();
            //go back to the SignUp activity
            Intent back = new Intent(Progress.this,SignUp.class);
            startActivity(back);
            //if the user clicks the progress button in the menu, go to Progress Activity
        } else if(id == R.id.progress) {
            Intent progress = new Intent(Progress.this,Progress.class);
            startActivity(progress);
        }

        return super.onOptionsItemSelected(item);
    }

}
