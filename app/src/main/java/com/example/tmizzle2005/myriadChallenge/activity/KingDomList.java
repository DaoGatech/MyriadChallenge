package com.example.tmizzle2005.myriadChallenge.activity;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.tmizzle2005.myriadChallenge.Inteface.API;
import com.example.tmizzle2005.myriadChallenge.Model.KdInfo;
import com.example.tmizzle2005.myriadChallenge.Model.KingdomItem;
import com.example.tmizzle2005.myriadChallenge.R;
import com.example.tmizzle2005.myriadChallenge.adapter.KingdomAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tmizzle2005 on 3/19/15.
 * This activity is to display the list of the kingdoms
 */
public class KingDomList extends ActionBarActivity {
    //declare the recyclerview, its adapter and its layout manager
    private RecyclerView mRecyclerView  ;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //declare the toolbar
    private Toolbar toolbar;
    //declare the SharedPreferences variable to get the saved value
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize the view for this activity
        setContentView(R.layout.kingdom_list);
        //initialize the SharedPreferences variable
        prefs = getSharedPreferences("savedData", Context.MODE_PRIVATE);
        //Show the dialog box
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Updating Complete Quests...",true);
        dialog.show();
        //Call the function loadKingdom to get the list of kingdoms
        loadKingDom();
        //initialize the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Initialize the toolbar. Set the title as the name and the subtitle as the email
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        toolbar.setTitle(prefs.getString("storedName",""));
        toolbar.setSubtitle(prefs.getString("storedEmail",""));
        setSupportActionBar(toolbar);
        //This function is to resolve the error when loading the number of "complete" quests
        //it basically runs the loadKingdom function again with the delay.
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        loadKingDom();
                        dialog.dismiss();
                    }
                },
                1000
        );
        //Save the total number of quests.The contains method is to check if the saved variable is
        // already there. If not, save the value
        if(!prefs.contains("totalsize" + prefs.getString("storedEmail","") +
                new SignUp().getDifferKey())) {
            SharedPreferences.Editor editor = getSharedPreferences("savedData",
                    Context.MODE_PRIVATE).edit();
            editor.putInt("totalsize" +  prefs.getString("storedEmail","")
                    + new SignUp().getDifferKey(),20).apply();
        }
        //Save the total number of quests remaining.The contains method is to check if the saved
        // variable is already there. If not, save the value
        if(!prefs.contains("KingdomFinished" + prefs.getString("storedEmail","") +
                new SignUp().getDifferKey())) {
            SharedPreferences.Editor editor = getSharedPreferences("savedData",
                    Context.MODE_PRIVATE).edit();
            editor.putInt("KingdomFinished" +  prefs.getString("storedEmail","") +
                    new SignUp().getDifferKey(),20).apply();
        }
    }

    @Override
    protected void onStart() {
        loadKingDom();
        super.onStart();
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
            Intent back = new Intent(KingDomList.this,SignUp.class);
            startActivity(back);
            //if the user clicks the progress button in the menu, go to Progress Activity
        } else if(id == R.id.progress) {
            Intent progress = new Intent(KingDomList.this,Progress.class);
            startActivity(progress);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        //if the user goes back to KingDomList Activity from another activity, simply rerun the
        //loadKingDom method
        loadKingDom();
        super.onResume();
    }

    /**
     * this function calls the API by retrofit and gets the list of kingdoms
     */
    private void loadKingDom() {
        //specify endpoint and build the restadapter instance
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://challenge2015.myriadapps.com")
                .build();
        final API api=restAdapter.create(API.class);
        //call the getKingdoms method from the Interface
        api.getKingdoms(new Callback<ArrayList<KingdomItem>>() {
            @Override
            public void success(ArrayList<KingdomItem> li, Response response) {
                // traverse the list of each Kingdoms
                for(int i = 0; i < li.size();i++) {
                    // get the name of the kingdom
                    final String name = li.get(i).getName();
                    //call the getKdInfo from the interface to get the number of quests of each
                    // kingdom
                    api.getKdInfo(li.get(i).getId(), new Callback<KdInfo>() {
                        @Override
                        public void success(KdInfo result, Response response) {
                            // return the size of quests for each kingdom.
                            int size = result.getKingDomQuests().size();
                            // save the number of quests remaining of each kingdom
                            if(!prefs.contains(name +  prefs.getString("storedEmail","")
                                    + new SignUp().getDifferKey())) {
                                SharedPreferences.Editor editor = getSharedPreferences("savedData",
                                        Context.MODE_PRIVATE).edit();
                                editor.putInt(name + prefs.getString("storedEmail","")
                                        + new SignUp().getDifferKey(),size).apply();
                            }
                            // save the number of quests of each kingdom
                            if(!prefs.contains(name + "size" + prefs.getString("storedEmail","")
                                    + new SignUp().getDifferKey())) {
                                SharedPreferences.Editor editor = getSharedPreferences("savedData",
                                        Context.MODE_PRIVATE).edit();
                                editor.putInt(name + "size" +  prefs.getString("storedEmail","")
                                        + new SignUp().getDifferKey(),size).apply();
                            }

                        }
                        @Override
                        public void failure(RetrofitError error) {
                            //if connecting fails, simply close the activity
                            Toast.makeText(getApplicationContext(),
                                    "Opps. Error Connecting to the Server",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });


                }
                //save the list of kingdoms to the adapter
                mAdapter = new KingdomAdapter(prefs,getApplicationContext(),li);
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void failure(RetrofitError error) {
                // if fails, simply close the activity
                Toast.makeText(getApplicationContext(),"Opps. Error Connecting to the Server",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

}