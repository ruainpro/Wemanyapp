package com.example.wethemanyapp;


import com.example.wethemanyapp.Interface.Interface_Users;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {


    private static Retrofit retrofit;
    public static String cookie = "";
    public static String user = "";
    public static String uid = "";
    public static String URLone="http://10.0.2.2:8080/";

    public static Interface_Users getEndPoints() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                          .baseUrl("http://192.168.188.51:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Interface_Users.class);
    }
}


