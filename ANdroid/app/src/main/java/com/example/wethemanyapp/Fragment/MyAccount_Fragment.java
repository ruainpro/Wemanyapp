package com.example.wethemanyapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wethemanyapp.Adapter.Historyproduct_adapter;
import com.example.wethemanyapp.Adapter.ProductFrame_Adapter;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.MainActivity;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.Model.Purchasing;
import com.example.wethemanyapp.Product_Detail;
import com.example.wethemanyapp.R;
import com.example.wethemanyapp.Url;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAccount_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccount_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView purchasingproductRecycleView;

    Historyproduct_adapter purchasingproductFrame_adapter;
    ArrayList<Purchasing> purchasingreturnValueList= new ArrayList<Purchasing>();

//    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//
//    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());
//
//    Retrofit retrofit = builder.client(httpClient.build()).build();



    private static final String TAG = MyAccount_Fragment.class.getSimpleName();


    public MyAccount_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccount_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccount_Fragment newInstance(String param1, String param2) {
        MyAccount_Fragment fragment = new MyAccount_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_my_account_, container, false);
        TextView textViewsetEmail=(TextView) rootView.findViewById(R.id.textView5);

//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.connectTimeout(30, TimeUnit.SECONDS);
//        httpClient.readTimeout(300, TimeUnit.SECONDS);
//        httpClient.writeTimeout(30, TimeUnit.SECONDS);
//        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());
//        Retrofit retrofit = builder.client(httpClient.build()).build();

        // Getting the login User Value Stored in SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String UserEmail = preferences.getString("User_EMAIL",null);//second parameter default value.
        textViewsetEmail.setText(UserEmail);

        getAllPurchasedProduct(rootView);
        purchasingproductRecycleView = rootView.findViewById(R.id.historyRecycle_product_item);

        // Button and Code For Logout
        Button settingBtn=rootView.findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences preferences = getContext().getSharedPreferences("MY_APP",Context.MODE_PRIVATE);
                if(! preferences.getString("firstVisit",null).isEmpty() ||
                        preferences.getString("firstVisit",null) !=null){
                }else{
                    preferences.edit().putString("firstVisit","true").apply();
                    String url = "https://docs.google.com/forms/d/e/1FAIpQLSfK-RobkOJmxP8VZ2BBXJ9dnUWKTN8wRUnQuZwAwAn380kS5g/viewform?vc=0&c=0&w=1&flr=0";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

                SharedPreferences settings = getContext().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                settings.edit().remove("BEARER_TOKEN").commit();
                settings.edit().remove("User_EMAIL").commit();

                Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                getContext().getApplicationContext().startActivity(intent);
            }
        });

        return rootView;
    }


    public void getAllPurchasedProduct(View view) {

        SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String BEARER_TOKEN  = preferences.getString("BEARER_TOKEN",null);//second parameter default value.

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        httpClient.writeTimeout(10, TimeUnit.SECONDS);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();

        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken="Bearer "+ BEARER_TOKEN;
        Call<MessageResponse> call = productClient.getAllPurchasedProduct(BearerToken);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                Log.d(TAG,response.toString());


                if (!response.isSuccessful()) {
                    Log.d(TAG,"!response.isSuccessful()");
                }
                if (response.isSuccessful()) {
                    Log.d(TAG,"isSuccessful");

                    MessageResponse messageResponse=response.body();
                    purchasingreturnValueList= messageResponse.getPurchasedValueList();
                    if(purchasingreturnValueList !=null){
                        setRecentlyViewedRecycler(purchasingreturnValueList);

                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.d(TAG,t.toString());

            }

        });


    }


    private void setRecentlyViewedRecycler(ArrayList<Purchasing> recentlyViewedDataList) {


        if( ! recentlyViewedDataList.isEmpty() ||  recentlyViewedDataList !=null){
            ArrayList<Purchasing> purchasingarray = new ArrayList<Purchasing>(recentlyViewedDataList);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
            purchasingproductRecycleView.setLayoutManager(layoutManager);
            purchasingproductFrame_adapter = new Historyproduct_adapter(getContext(),purchasingarray,MyAccount_Fragment.this);
            purchasingproductRecycleView.setAdapter(purchasingproductFrame_adapter);
            purchasingproductFrame_adapter.notifyDataSetChanged();
        }

    }
}