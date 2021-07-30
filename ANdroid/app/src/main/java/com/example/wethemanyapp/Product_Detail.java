package com.example.wethemanyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.wethemanyapp.Fragment.Product_Fragment;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.Product;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Product_Detail extends AppCompatActivity {


    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.client(httpClient.build()).build();

    private static final String TAG = Product_Detail.class.getSimpleName();

    String product_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        product_Id = intent.getStringExtra("product_Id");
        doRendenderandOtherAction(product_Id);
    }

    public void doRendenderandOtherAction(String product_Id){

        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken="Bearer "+ Url.cookie;
//        String BearerToken=Url.cookie;
        Log.d(TAG,BearerToken);
        Call<MessageResponse> call = productClient.getALlProductById(BearerToken,product_Id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG,"isSuccessful");
                }
                if (response.isSuccessful()) {

                    MessageResponse messageResponse=response.body();
                    Product returnValueList= (Product) messageResponse.getReturnValue();
                        Log.d(TAG,"--------------       getReturnValue ------------------");
                        Log.d(TAG,returnValueList.toString());
                    TextView productName, priceName, category, C02emission;
                    ImageView imageView;

                    String imageUrl=Url.URLone+"api/auth/getImages/"+returnValueList.getImages();
                    Log.d(TAG,imageUrl);

                    productName=findViewById(R.id.textView11);
                    productName.setText(returnValueList.getName());
                    priceName=findViewById(R.id.textView12);
                    priceName.setText("$ "+returnValueList.getPrice());

                    category=findViewById(R.id.textView7);
                    category.setText(returnValueList.getCategory());

                    C02emission=findViewById(R.id.textView10);
                    C02emission.setText("Co2 Emission: "+String.valueOf(returnValueList.getCo2Emission()));


                    RequestManager glide =Glide.with(getApplicationContext());
                    ImageView imageViewSHow=(ImageView) findViewById(R.id.imageView7);
//                    imageView.setImageURI(Uri.parse(imageUrl));

                    if (returnValueList.getImages() ==null ) {
                        glide.load(imageUrl).into(imageViewSHow);
                    } else {
                        glide.load(imageUrl).into(imageViewSHow);


                    }

                }
            }


            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }


        });
    }
}