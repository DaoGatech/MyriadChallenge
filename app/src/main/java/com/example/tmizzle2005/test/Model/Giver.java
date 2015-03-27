package com.example.tmizzle2005.test.Model;

/**
 * Created by tmizzle2005 on 3/19/15.
 * This is the holder of the information of the quest giver
 */
public class Giver {
    private int id;
    private String name;
    private String image;

    /**
     * this method gets the id of the giver
     * @return the giver's id
     */
    public int getGiverId() {
        return id;
    }

    /**
     * This method returns the name of the giver
     * @return the name of the giver
     */
    public String getGiverName() {
        return name;
    }

    /**
     * this method returns the image url of the giver
     * @return the image of the giver
     */
    public String getGiverImage() {
        return image;
    }
}
