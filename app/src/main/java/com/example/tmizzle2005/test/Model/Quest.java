package com.example.tmizzle2005.test.Model;

/**
 * Created by tmizzle2005 on 3/19/15.
 */
public class Quest {
    private int id;
    private String name;
    private String description;
    private String image;
    private Giver giver;

    public int getQuestID() {
        return id;
    }

    public String getQuestName(){
        return name;
    }

    public String getQuestDescription() {
        return description;
    }

    public String getQuestImage() {
        return image;
    }

    public Giver getQuestGiver() {
        return giver;
    }
}
