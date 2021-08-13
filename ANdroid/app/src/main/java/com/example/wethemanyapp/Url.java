package com.example.wethemanyapp;


import com.example.wethemanyapp.Interface.Interface_Users;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {


    private static Retrofit retrofit;
    public static String cookie = "";
    public static String user = "";
    public static String uid = "";
    public static String URLone="https://wemanyappru.herokuapp.com/";

    public static Interface_Users getEndPoints() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://wemanyappru.herokuapp.com/")
//                          .baseUrl("http://192.168.188.51:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Interface_Users.class);
    }
}


