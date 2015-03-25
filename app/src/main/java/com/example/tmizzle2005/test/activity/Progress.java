package com.example.tmizzle2005.test.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tmizzle2005.test.Inteface.API;
import com.example.tmizzle2005.test.POJO.SignUpMessage;
import com.example.tmizzle2005.test.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Progress extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questprogress);
        SharedPreferences prefs = getSharedPreferences("savedData", Context.MODE_PRIVATE);
        ProgressBar questComplete = (ProgressBar)findViewById (R.id.progressCircle);
        int remain = prefs.getInt("KingdomFinished"  +  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0);
        int qsize = prefs.getInt("totalsize" +  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0);
        Toast.makeText(getApplicationContext(),String.valueOf(remain),Toast.LENGTH_LONG).show();
        questComplete.setProgress(((qsize-remain)/qsize) * 100);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
