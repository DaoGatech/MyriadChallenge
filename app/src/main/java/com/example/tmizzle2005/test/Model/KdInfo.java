package com.example.tmizzle2005.test.Model;

import java.util.ArrayList;

/**
 * Created by tmizzle2005 on 3/18/15.
 */
public class KdInfo {
    private int id;
    private String name;
    private String image;
    private String climate;
    private int population;
    private ArrayList<Quest> quests;

    public int getKingdomId() {
        return id;
    }

    public String getKingDomName() {
        return name;
    }

    public String getKingDomImage() {
        return image;
    }

    public String getKingDomClimate() {
        return climate;
    }

    public int getKingDomPopulation() {
        return population;
    }

    public ArrayList<Quest> getKingDomQuests() {
        return quests;
    }


}
