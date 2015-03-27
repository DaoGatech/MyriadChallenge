package com.example.tmizzle2005.myriadChallenge.Model;

/**
 * Created by tmizzle2005 on 3/19/15.
 * this method holds the information of a quest
 */
public class Quest {
    private int id;
    private String name;
    private String description;
    private String image;
    private Giver giver;

    /**
     * this method returns the quest id
     * @return the quest id
     */
    public int getQuestID() {
        return id;
    }

    /**
     * this method returns the quest's name
     * @return the quest's name
     */
    public String getQuestName(){
        return name;
    }

    /**
     * this method the quest's decription
     * @return the quest's description
     */
    public String getQuestDescription() {
        return description;
    }

    /**
     * this method returns the quest's image
     * @return the quest's image
     */
    public String getQuestImage() {
        return image;
    }

    /**
     * this method returns the quest's giver
     * @return the quest giver
     */
    public Giver getQuestGiver() {
        return giver;
    }
}
