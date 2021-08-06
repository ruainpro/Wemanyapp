package com.example.wethemanyapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.wethemanyapp.Adapter.ProductFrame_Adapter;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.Product;
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
 * Use the {@link Product_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Product_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView productRecycleView;

    ProductFrame_Adapter productFrame_adapter;
    ArrayList<Product> returnValueList= new ArrayList<Product>();

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.client(httpClient.build()).build();

    private static final String TAG = Product_Fragment.class.getSimpleName();

//    List<Product> productList;

    public Product_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Product_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Product_Fragment newInstance(String param1, String param2) {
        Product_Fragment fragment = new Product_Fragment();
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
        View view= inflater.inflate(R.layout.fragment_product_, container, false);

        getAllPostData(view);

        productRecycleView = view.findViewById(R.id.Recycle_product_item);

        EditText earachTxt=view.findViewById(R.id.search_fragmentSeeting);
        earachTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(String.valueOf(s).isEmpty())){
                    filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;

    }

    public void getAllPostData(View viww) {

        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken="Bearer "+ Url.cookie;
//      String BearerToken=Url.cookie;
        Log.d(TAG,BearerToken);
        Call<MessageResponse> call = productClient.getALlProduct(BearerToken);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d(TAG,"isSuccessful");
                    }
                    if (response.isSuccessful()) {
                        MessageResponse messageResponse=response.body();
                        returnValueList= messageResponse.getReturnValueList();
                        setRecentlyViewedRecycler(returnValueList);

                    }
                }


            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }


        });


    }


    private void setRecentlyViewedRecycler(ArrayList<Product> recentlyViewedDataList) {

        ArrayList<Product> productarray = new ArrayList<Product>(recentlyViewedDataList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        productRecycleView.setLayoutManager(layoutManager);
        productFrame_adapter = new ProductFrame_Adapter(getContext(),productarray,productarray);
        productRecycleView.setAdapter(productFrame_adapter);
        productFrame_adapter.notifyDataSetChanged();
    }

    private void filter(String text) {

        ArrayList<Product> filteredlist= new ArrayList<Product>();
        for (Product item : returnValueList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase()) || (item.getCategory().toLowerCase().contains(text.toLowerCase()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
           Log.d(TAG,"No Data Found");
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            productFrame_adapter.filterList(filteredlist);
        }
    }


}