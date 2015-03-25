package com.example.tmizzle2005.test.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.tmizzle2005.test.Fragment.Info_Fragment;
import com.example.tmizzle2005.test.Inteface.API;
import com.example.tmizzle2005.test.POJO.KdInfo;
import com.example.tmizzle2005.test.R;
import com.example.tmizzle2005.test.adapter.InfoAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tmizzle2005 on 3/19/15.
 */
public class KingdomInfo extends FragmentActivity {
    InfoAdapter pageAdapter;
    public static ViewPager pager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        String id = getIntent().getStringExtra("id");
        getFragments(id);
    }

    private void getFragments(String id){
        //specify endpoint and build the restadapter instance
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://challenge2015.myriadapps.com")
                .build();

        API api=restAdapter.create(API.class);
        api.getKdInfo(Integer.parseInt(id), new Callback<KdInfo>() {
            @Override
            public void success(KdInfo result, Response response) {
                List<Fragment> fList = new ArrayList<Fragment>();
                fList.add(Info_Fragment.addKingDomInfo(result));
                for(int i = 0; i < result.getKingDomQuests().size(); i++) {
                    SharedPreferences prefs = getSharedPreferences("savedData", Context.MODE_PRIVATE);
                    if(!prefs.contains(result.getKingDomQuests().
                            get(i).getQuestName() + prefs.getString("storedEmail","") + new SignUp().getDifferKey())) {
                        SharedPreferences.Editor editor = getSharedPreferences("savedData", Context.MODE_PRIVATE).edit();
                        editor.putString(result.getKingDomQuests().get(i).
                                getQuestName() + prefs.getString("storedEmail","") + new SignUp().getDifferKey(), "incomplete").apply();
                    }
                    fList.add(Info_Fragment.addQuestInfo(result.getKingDomName(),result.getKingDomQuests().get(i)));
                }
                pageAdapter = new InfoAdapter(getSupportFragmentManager(), fList);
                pager = (ViewPager)findViewById(R.id.viewpager);
                pager.setAdapter(pageAdapter);
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),"Opps. Error Connecting to the Server",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}
