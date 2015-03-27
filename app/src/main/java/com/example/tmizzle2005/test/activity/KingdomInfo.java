package com.example.tmizzle2005.test.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.tmizzle2005.test.Fragment.Info_Fragment;
import com.example.tmizzle2005.test.Inteface.API;
import com.example.tmizzle2005.test.Model.KdInfo;
import com.example.tmizzle2005.test.R;
import com.example.tmizzle2005.test.adapter.InfoAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tmizzle2005 on 3/19/15.
 * This activity is to display the information of the kingdom
 */
public class KingdomInfo extends FragmentActivity {
    InfoAdapter pageAdapter; //declare the adapter of the fragment
    public static ViewPager pager; //declare the view pager
    public static HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize the view of the fragment
        setContentView(R.layout.viewpager);
        //get the id of the kingdom
        String id = getIntent().getStringExtra("id");
        //call the function getFragments, with "id" as the parameter.
        getFragments(id);
    }

    /* This function gets all the fragments
     * @params id the id of the kingdom
     */
    private void getFragments(String id){
        //specify endpoint and build the restadapter instance
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://challenge2015.myriadapps.com")
                .build();
        API api=restAdapter.create(API.class);
        // Call the function get kingdom info
        api.getKdInfo(Integer.parseInt(id), new Callback<KdInfo>() {
            @Override
            public void success(KdInfo result, Response response) {
                List<Fragment> fragmentLists = new ArrayList<Fragment>();
                ArrayList<String> names = new ArrayList<String>();
                //add the kingdom page to the fragments list
                fragmentLists.add(Info_Fragment.addKingDomInfo(result));
                for(int i = 0; i < result.getKingDomQuests().size(); i++) {
                    SharedPreferences prefs = getSharedPreferences("savedData", Context.MODE_PRIVATE);
                    //save the state of each quest as "incomplete". The key is the combination of
                    //the quest name, the email of the user and and 6-digit numbers
                    if(!prefs.contains(result.getKingDomQuests().
                            get(i).getQuestName() + prefs.getString("storedEmail","") + new SignUp().getDifferKey())) {
                        SharedPreferences.Editor editor = getSharedPreferences("savedData", Context.MODE_PRIVATE).edit();
                        editor.putString(result.getKingDomQuests().get(i).
                                getQuestName() + prefs.getString("storedEmail","") + new SignUp().getDifferKey(), "incomplete").apply();

                    }
                    names.add(result.getKingDomQuests().get(i).getQuestName());
                    //add the "quest" page to the fragment list
                    fragmentLists.add(Info_Fragment.addQuestInfo(result.getKingDomName(), result.getKingDomQuests().get(i)));
                }
                map.put(result.getKingDomName(),names);
                //initialize the adapter of viewpager
                pageAdapter = new InfoAdapter(getSupportFragmentManager(), fragmentLists);
                pager = (ViewPager)findViewById(R.id.viewpager);
                //set adapter
                pager.setAdapter(pageAdapter);
            }
            @Override
            public void failure(RetrofitError error) {
                //if the server responds slowly, simply finish the activity and go back
                Toast.makeText(getApplicationContext(),"Opps. Error Connecting to the Server",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}