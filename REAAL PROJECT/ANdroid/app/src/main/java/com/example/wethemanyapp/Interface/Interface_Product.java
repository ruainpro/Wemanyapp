package com.example.wethemanyapp.Interface;

import com.example.wethemanyapp.Model.Carts;
import com.example.wethemanyapp.Model.JwtResponse;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.Model.Purchasing;
import com.example.wethemanyapp.Model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Interface_Product {

    @GET("/api/auth/user/")
    Call<MessageResponse> getALlProduct(@Header("Authorization") String authHeader);

    @GET("/api/auth/user/{id}")
    Call<MessageResponse> getALlProductById(@Header("Authorization") String authHeader,@Path("id") String post_id);

//     cARTS
    @POST("/api/auth/user/addToCart")
    Call<MessageResponse> cartTheProduct(@Header("Authorization") String authHeader, @Body Carts carts);

    @GET("/api/auth/user/getAllCartdata")
    Call<MessageResponse> getAllCartsInfoById(@Header("Authorization") String authHeader);

    @GET("/api/auth/user/getAllPurchasedProduct")
    Call<MessageResponse> getAllPurchasedProduct(@Header("Authorization") String authHeader);


//    @POST("/api/auth/User/purchaseProduct")s
//    Call<MessageResponse> getpaymentWork(@Body Purchasing purchasing);

    @POST("/api/auth/User/purchaseProduct")
    Call<String> getpaymentWork(@Header("Authorization") String authHeader,@Body Purchasing purchasing);

}
