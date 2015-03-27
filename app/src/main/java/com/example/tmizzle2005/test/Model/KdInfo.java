package com.example.tmizzle2005.test.Model;

import java.util.ArrayList;

/**
 * Created by tmizzle2005 on 3/18/15.
 * This class holds the information of the kingdom
 */
public class KdInfo {
    private int id;
    private String name;
    private String image;
    private String climate;
    private int population;
    private ArrayList<Quest> quests;

    /**
     * this method returns the kingdom's id
     * @return the kingdom's id
     */
    public int getKingdomId() {
        return id;
    }

    /**
     * this method returns the kingdom's name
     * @return the kingdom's name
     */
    public String getKingDomName() {
        return name;
    }

    /**
     * this method returns the kingdom's image
     * @return the url of the kingdom's image
     */
    public String getKingDomImage() {
        return image;
    }

    /**
     * this method returns the kingdom's climate
     * @return the kingdom's climate
     */
    public String getKingDomClimate() {
        return climate;
    }

    /**
     * this method returns the kingdom's population
     * @return the kingdom's population
     */
    public int getKingDomPopulation() {
        return population;
    }

    /**
     * this method returns the list of quests
     * @return the list of quests
     */
    public ArrayList<Quest> getKingDomQuests() {
        return quests;
    }


}
