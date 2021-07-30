package com.example.wethemanyapp.Interface;

import com.example.wethemanyapp.Model.JwtResponse;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Interface_Users {

    @POST("api/auth/signin")
    Call<JwtResponse> loginUser(@Body User user);
}
