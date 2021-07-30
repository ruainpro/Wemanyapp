package com.example.wethemanyapp.Interface;

import com.example.wethemanyapp.Model.JwtResponse;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.Model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface Interface_Product {

    @GET("/api/auth/user/")
    Call<MessageResponse> getALlProduct(@Header("Authorization") String authHeader);

    @GET("/api/auth/user/{id}")
    Call<MessageResponse> getALlProductById(@Header("Authorization") String authHeader,@Path("id") String post_id);
}
