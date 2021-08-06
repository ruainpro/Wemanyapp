package com.example.wethemanyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.wethemanyapp.Adapter.ViewPagerAdapter;
import com.example.wethemanyapp.Fragment.History_Fragment;
import com.example.wethemanyapp.Fragment.HomeFragment;
import com.example.wethemanyapp.Fragment.MyAccount_Fragment;
import com.example.wethemanyapp.Fragment.Product_Fragment;
import com.example.wethemanyapp.Implementation.ProductImplementation;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.Model.Carts;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.Product;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

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

    int quantityOne=1;

    ProductImplementation productImplementation=new ProductImplementation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ImageView imageView_Backbtn=findViewById(R.id.imageView_backBtn);
        imageView_Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        Intent intent = getIntent();
        product_Id = intent.getStringExtra("product_Id");
        doRendenderandOtherAction(product_Id);

        Button button2AddtoCart=findViewById(R.id.button2);
        button2AddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carts carts=new Carts();
                carts.setProductid(product_Id);
                carts.setQuantity(quantityOne);

                boolean returnBoolean=productImplementation.addToCart(carts);
            }
        });

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

                    TextView productName, priceName, category, C02emission,productDesc;
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

                    productDesc=findViewById(R.id.textView13);
                    productDesc.setText(returnValueList.getDescription());


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

        TextView textView14= (TextView) findViewById(R.id.textView14);

        ImageView imageView8MINUS=findViewById(R.id.imageView8);
        imageView8MINUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityOne=quantityOne-1;
                textView14.setText(String.valueOf(quantityOne));
            }
        });

        ImageView imageView9PLUS=findViewById(R.id.imageView9);
        imageView9PLUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityOne=quantityOne+1;
                textView14.setText(String.valueOf(quantityOne));

            }
        });
    }
}