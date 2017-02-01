package com.example.ravi.customnavigationdrawer;

import com.google.gson.JsonArray;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Ravi on 01-02-2017.
 */

public interface Apiservice
{
@GET("/json/movies.json")
    public void Mymeth(Callback<JsonArray> callback);
}
