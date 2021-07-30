package com.example.wethemanyapp.Implementation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.example.wethemanyapp.Activity_Fragment_JoinMaian;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.Interface.Interface_Users;
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

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.client(httpClient.build()).build();

    List<?> messageResponse;

    public ProductImplementation(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public ProductImplementation() {
    }

    public String getAllTheProduct() {

        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken="Bearer "+ Url.cookie;
        Call<MessageResponse> call = productClient.getALlProduct(BearerToken);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (context != null) {
                    if (!response.isSuccessful()) {

                    }
                    if (response.isSuccessful()) {
                        messageResponse = response.body().getReturnValueList();

                        Log.d(TAG,"********************************************************************");
                        Log.d(TAG,response.body().toString());


                        Log.d(TAG,"********************************************************************");
//                        messageResponse

                    }
                }

            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }


        });

        return "Hello";
    }
}
