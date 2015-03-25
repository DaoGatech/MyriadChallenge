package com.example.tmizzle2005.test.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tmizzle2005.test.Fragment.Info_Fragment;
import com.example.tmizzle2005.test.Inteface.API;
import com.example.tmizzle2005.test.POJO.KdInfo;
import com.example.tmizzle2005.test.POJO.KingdomItem;
import com.example.tmizzle2005.test.R;
import com.example.tmizzle2005.test.adapter.InfoAdapter;
import com.example.tmizzle2005.test.adapter.KingdomAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class KingDomList extends ActionBarActivity {
    private RecyclerView mRecyclerView  ;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
    private SharedPreferences prefs;
    private int totalSize = 0;
    private ArrayList<String> test = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kingdom_list);
        prefs = getSharedPreferences("savedData", Context.MODE_PRIVATE);
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Updating Complete Quests...",true);
        dialog.show();
        loadKingDom();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        toolbar.setTitle(prefs.getString("storedName",""));
        toolbar.setSubtitle(prefs.getString("storedEmail",""));
        setSupportActionBar(toolbar);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            prefs.edit().remove("storedEmail").commit();
            Intent back = new Intent(KingDomList.this,SignUp.class);
            startActivity(back);

        } else if(id == R.id.progress) {
            Intent progress = new Intent(KingDomList.this,Progress.class);
            startActivity(progress);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        loadKingDom();
        super.onResume();
    }
    private void loadKingDom() {
    //specify endpoint and build the restadapter instance
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://challenge2015.myriadapps.com")
                .build();
        final API api=restAdapter.create(API.class);
        api.getKingdoms(new Callback<ArrayList<KingdomItem>>() {
            @Override
            public void success(ArrayList<KingdomItem> li, Response response) {
                if(!prefs.contains("totalsize" + prefs.getString("storedEmail","") + new SignUp().getDifferKey())) {
                    SharedPreferences.Editor editor = getSharedPreferences("savedData", Context.MODE_PRIVATE).edit();
                    editor.putInt("totalsize" +  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0 - li.size()).apply();
                }
                if(!prefs.contains("KingdomFinished" + prefs.getString("storedEmail","") + new SignUp().getDifferKey())) {
                    SharedPreferences.Editor editor = getSharedPreferences("savedData", Context.MODE_PRIVATE).edit();
                    editor.putInt("KingdomFinished" +  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0 - li.size()).apply();
                }
                for(int i = 0; i < li.size();i++) {
                        final String name = li.get(i).getName();
                        api.getKdInfo(li.get(i).getId(), new Callback<KdInfo>() {
                            @Override
                            public void success(KdInfo result, Response response) {
                                int size = result.getKingDomQuests().size();
                                if(!prefs.contains(name +  prefs.getString("storedEmail","") + new SignUp().getDifferKey())) {
                                    SharedPreferences.Editor editor = getSharedPreferences("savedData", Context.MODE_PRIVATE).edit();
                                    editor.putInt(name + prefs.getString("storedEmail","") + new SignUp().getDifferKey(),size).apply();
                                }
                                if(!prefs.contains(name + "size" + prefs.getString("storedEmail","") + new SignUp().getDifferKey())) {
                                    SharedPreferences.Editor editor = getSharedPreferences("savedData", Context.MODE_PRIVATE).edit();
                                    editor.putInt(name + "size" +  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),size).apply();
                                }

                            }
                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getApplicationContext(),"Opps. Error Connecting to the Server",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });


                }
                mAdapter = new KingdomAdapter(prefs,getApplicationContext(),li);
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),"Opps. Error Connecting to the Server",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    public void addTotal(int add) {
        totalSize += add;
    }


}
