package com.example.tmizzle2005.myriadChallenge.Inteface;

/**
 * Created by tmizzle2005 on 3/15/15.
 * the main API Interface of the app
 */

import com.example.tmizzle2005.myriadChallenge.Model.KdInfo;
import com.example.tmizzle2005.myriadChallenge.Model.KingdomItem;
import com.example.tmizzle2005.myriadChallenge.Model.SignUpMessage;

import java.util.ArrayList;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


public interface API {

    /**
     * this method gets list of kingdoms
     */
    @GET("/api/v1/kingdoms")
    void getKingdoms(Callback<ArrayList<KingdomItem>> li);

    /**
     * This method post the email to the server, receives the return message
     */
    @FormUrlEncoded
    @POST("/api/v1/subscribe")
    void signUp(@Field("email") String e, Callback<SignUpMessage> result);

    /*
     * this method gets the info about kingdom by its id
     */
    @GET("/api/v1/kingdoms/{id}")
    void getKdInfo(@Path("id") int id, Callback<KdInfo> li);
}