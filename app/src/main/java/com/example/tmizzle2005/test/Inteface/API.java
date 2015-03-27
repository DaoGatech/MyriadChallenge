package com.example.tmizzle2005.test.Inteface;

/**
 * Created by tmizzle2005 on 3/15/15.
 */

import com.example.tmizzle2005.test.Model.KdInfo;
import com.example.tmizzle2005.test.Model.KingdomItem;
import com.example.tmizzle2005.test.Model.SignUpMessage;

import java.util.ArrayList;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


public interface API {

    @GET("/api/v1/kingdoms")
    void getKingdoms(Callback<ArrayList<KingdomItem>> li);

    @FormUrlEncoded
    @POST("/api/v1/subscribe")
    void signUp(@Field("email") String e, Callback<SignUpMessage> result);

    @GET("/api/v1/kingdoms/{id}")
    void getKdInfo(@Path("id") int id, Callback<KdInfo> li);
}