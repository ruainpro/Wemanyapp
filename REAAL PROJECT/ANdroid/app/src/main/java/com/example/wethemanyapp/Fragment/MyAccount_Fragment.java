package com.example.wethemanyapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wethemanyapp.Adapter.Historyproduct_adapter;
import com.example.wethemanyapp.Adapter.ProductFrame_Adapter;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.Model.Purchasing;
import com.example.wethemanyapp.R;
import com.example.wethemanyapp.Url;

import java.util.ArrayList;

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
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.client(httpClient.build()).build();

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
        getAllPurchasedProduct(rootView);
        purchasingproductRecycleView = rootView.findViewById(R.id.historyRecycle_product_item);

        return rootView;
    }


    public void getAllPurchasedProduct(View view) {

        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken="Bearer "+ Url.cookie;
        Log.d(TAG,BearerToken);
        Call<MessageResponse> call = productClient.getAllPurchasedProduct(BearerToken);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG,"isSuccessful");
                }
                if (response.isSuccessful()) {
                    MessageResponse messageResponse=response.body();
                    purchasingreturnValueList= messageResponse.getPurchasedValueList();
                    setRecentlyViewedRecycler(purchasingreturnValueList);

                }
            }


            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }


        });


    }


    private void setRecentlyViewedRecycler(ArrayList<Purchasing> recentlyViewedDataList) {

        ArrayList<Purchasing> purchasingarray = new ArrayList<Purchasing>(recentlyViewedDataList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        purchasingproductRecycleView.setLayoutManager(layoutManager);
        purchasingproductFrame_adapter = new Historyproduct_adapter(getContext(),purchasingarray,MyAccount_Fragment.this);
        purchasingproductRecycleView.setAdapter(purchasingproductFrame_adapter);
        purchasingproductFrame_adapter.notifyDataSetChanged();
    }
}