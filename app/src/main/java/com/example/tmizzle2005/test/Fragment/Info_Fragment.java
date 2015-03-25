package com.example.tmizzle2005.test.Fragment;

/**
 * Created by tmizzle2005 on 3/19/15.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.devspark.robototextview.util.RobotoTextViewUtils;
import com.devspark.robototextview.util.RobotoTypefaceManager;
import com.devspark.robototextview.widget.RobotoTextView;
import com.example.tmizzle2005.test.POJO.KdInfo;
import com.example.tmizzle2005.test.POJO.Quest;
import com.example.tmizzle2005.test.R;
import com.example.tmizzle2005.test.activity.KingdomInfo;
import com.example.tmizzle2005.test.activity.SignUp;
import com.squareup.picasso.Picasso;

public class Info_Fragment extends Fragment {
    public static final Info_Fragment addKingDomInfo(KdInfo info)
    {
        Info_Fragment f = new Info_Fragment();
        Bundle bdl = new Bundle(5);
        bdl.putString("type","kingdom");
        bdl.putString("name",info.getKingDomName());
        bdl.putString("image",info.getKingDomImage());
        bdl.putString("climate",info.getKingDomClimate());
        bdl.putString("population",String.valueOf(info.getKingDomPopulation()));
        bdl.putString("questnum",String.valueOf(info.getKingDomQuests().size()));
        f.setArguments(bdl);
        return f;
    }

    public static final Info_Fragment addQuestInfo(String kdname,Quest info)
    {
        Info_Fragment f = new Info_Fragment();
        Bundle bdl = new Bundle(7);
        bdl.putString("type","quest");
        bdl.putString("belong",kdname);
        bdl.putString("name",info.getQuestName());
        bdl.putString("id",String.valueOf(info.getQuestID()));
        bdl.putString("image",info.getQuestImage());
        bdl.putString("description",info.getQuestDescription());
        bdl.putString("giverName",info.getQuestGiver().getGiverName());
        bdl.putString("giverImage",info.getQuestGiver().getGiverImage());
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String type = getArguments().getString("type");
        if(type.equals("kingdom")) {
            View v = inflater.inflate(R.layout.kingdom_info, container, false);
            Toolbar toolbar = (Toolbar) v.findViewById(R.id.info_tool_bar);
            toolbar.setTitle(getArguments().getString("name"));
            toolbar.setSubtitle("Number of Quests: " + getArguments().getString("questnum"));
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
            ImageView image = (ImageView) v.findViewById(R.id.kingdom_avatar);
            TextView kdclimate = (TextView) v.findViewById(R.id.kdclimate);
            TextView kdpopulation = (TextView) v.findViewById(R.id.kdpopulation);
            Picasso.with(v.getContext())
                    .load(getArguments().getString("image"))
                    .into(image);
            RobotoTextView kdname = (RobotoTextView) v.findViewById(R.id.kdname);
            kdname.setText(getArguments().getString("name"));
            kdclimate.setText(getArguments().getString("climate"));
            kdpopulation.setText(getArguments().getString("population"));
            return v;
        } else {
            View v = inflater.inflate(R.layout.quest_info, container, false);
            final ImageView isComplete = (ImageView) v.findViewById(R.id.isComplete);
            final ToggleButton complete = (ToggleButton) v.findViewById(R.id.complete);
            final RobotoTextView isCom = (RobotoTextView) v.findViewById(R.id.isCom);
            final SharedPreferences prefs = getActivity().getSharedPreferences("savedData", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = getActivity().getSharedPreferences("savedData", Context.MODE_PRIVATE).edit();
            String result = prefs.getString(getArguments().getString("name")+ prefs.getString("storedEmail","") + new SignUp().getDifferKey(), null);
            if(result.equals("complete")) {
                complete.setChecked(false);
                isComplete.setImageResource(R.drawable.complete);
                isCom.setText("Complete");

            } else {
                complete.setChecked(true);
                isComplete.setImageResource(R.drawable.incomplete);
                isCom.setText("Incomplete");
            }
            Toolbar toolbar = (Toolbar) v.findViewById(R.id.quest_tool_bar);
            toolbar.setTitle(getArguments().getString("name"));
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewPager mPager = KingdomInfo.pager;
                    mPager.setCurrentItem(mPager.getCurrentItem()-1);
                }
            });
            complete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(!complete.isChecked()) {
                        editor.putString(getArguments().getString("name")+ prefs.getString("storedEmail","") + new SignUp().getDifferKey(), "complete").apply();
                        isComplete.setImageResource(R.drawable.complete);
                        isCom.setText("Complete");
                        editor.putInt(getArguments().getString("belong")+ prefs.getString("storedEmail","") + new SignUp().getDifferKey(),prefs.getInt(getArguments().getString("belong")+  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0) - 1).apply();
                        editor.putInt("totalQuestComplete"+ prefs.getString("storedEmail","") + new SignUp().getDifferKey(),prefs.getInt("totalQuestComplete"+  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0) - 1).apply();
                    } else {
                        editor.putString(getArguments().getString("name")+ prefs.getString("storedEmail","") + new SignUp().getDifferKey(), "incomplete").apply();
                        isComplete.setImageResource(R.drawable.incomplete);
                        isCom.setText("Incomplete");
                        editor.putInt(getArguments().getString("belong")+ prefs.getString("storedEmail","") + new SignUp().getDifferKey(),prefs.getInt(getArguments().getString("belong")+ prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0) + 1).apply();
                        editor.putInt("totalQuestComplete"+ prefs.getString("storedEmail","") + new SignUp().getDifferKey(),prefs.getInt("totalQuestComplete"+  prefs.getString("storedEmail","") + new SignUp().getDifferKey(),0) + 1).apply();
                    }
                }
            });
            ImageView qimage = (ImageView) v.findViewById(R.id.qimage);
            ImageView gimage = (ImageView) v.findViewById(R.id.giveravatar);
            RobotoTextView qname = (RobotoTextView) v.findViewById(R.id.qname);
            RobotoTextView gname = (RobotoTextView) v.findViewById(R.id.givername);
            TextView qdesc = (TextView) v.findViewById(R.id.qdesc);
            if(getArguments().getString("description").equals("")) {
                qdesc.setText("No Description Available");
            } else {
                qdesc.setText(getArguments().getString("description"));
            }
            if(!getArguments().getString("image").equals("")) {
                Picasso.with(v.getContext())
                        .load(getArguments().getString("image"))
                        .into(qimage);
            }
            if(!getArguments().getString("giverImage").equals("")) {
                Picasso.with(v.getContext())
                        .load(getArguments().getString("giverImage"))
                        .into(gimage);
            }
            qname.setText(getArguments().getString("name"));
            gname.setText(getArguments().getString("giverName"));

            return v;
        }
    }

}
