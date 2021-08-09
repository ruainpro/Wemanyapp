package com.example.wethemanyapp.Implementation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wethemanyapp.Activity_Fragment_JoinMaian;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.Interface.Interface_Users;
import com.example.wethemanyapp.Model.Carts;
import com.example.wethemanyapp.Model.JwtResponse;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.R;
import com.example.wethemanyapp.Url;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductImplementation {

    private static final String TAG = ProductImplementation.class.getSimpleName();

    Context context;
    Activity activity;
    ArrayList<Product> returnValueList= new ArrayList<Product>();

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());


    Retrofit retrofit = builder.client(httpClient.build()).build();

    List<?> messageResponse;

    public ProductImplementation(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    boolean returnOfAddCart;

    public ProductImplementation() {
    }

    public ArrayList<Product> getAllTheProduct() {
        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken = "Bearer " + Url.cookie;
//        String BearerToken=Url.cookie;
        Log.d(TAG, BearerToken);
        Call<MessageResponse> call = productClient.getALlProduct(BearerToken);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "isSuccessful");
                }
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    returnValueList = messageResponse.getReturnValueList();
                    addToListMe(returnValueList);

                }
            }


            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }


        });

        return returnValueList;
    }

    public void addToListMe(ArrayList<Product> productTOAANALAYSE){
        returnValueList= productTOAANALAYSE;

        Log.d(TAG,returnValueList.toString());
    }

    public boolean addToCart(Carts carts, String token){


        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken = "Bearer " + token;
//        String BearerToken=Url.cookie;
        Log.d(TAG, BearerToken);

        Call<MessageResponse> call = productClient.cartTheProduct(BearerToken,carts);
       
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG,response.toString());
                    Log.d(TAG, "Not Sucessfull");
                }
                if (response.isSuccessful()) {
                    MessageResponse messageResponse = response.body();
                    Log.d(TAG, "Successful  "+messageResponse.getMessage());
                    returnOfAddCart=true;
                    Log.d(TAG, String.valueOf(returnOfAddCart));

                }
            }


            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }


        });

        return returnOfAddCart;
    }


}
