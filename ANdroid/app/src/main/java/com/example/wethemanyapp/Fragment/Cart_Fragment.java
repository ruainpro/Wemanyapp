package com.example.wethemanyapp.Fragment;

import static android.graphics.Color.GREEN;

import static androidx.core.content.ContextCompat.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wethemanyapp.Adapter.CartList_Adapter;
import com.example.wethemanyapp.Adapter.ProductFrame_Adapter;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.Model.Carts;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.PaymentInfo;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.Model.PurchasedProduct;
import com.example.wethemanyapp.Model.Purchasing;
import com.example.wethemanyapp.R;
import com.example.wethemanyapp.Url;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cart_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String paymentIntentClientSecret;
    private Stripe stripe;

    public ArrayList<Carts> cartedOne=new ArrayList<Carts>();


    RecyclerView cartsRecycleView;

    CartList_Adapter cartlistFrame_adapter;
    ArrayList<Carts> cartsreturnValueList= new ArrayList<Carts>();

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.client(httpClient.build()).build();

    private static final String TAG = Cart_Fragment.class.getSimpleName();

    public static double finalProductValue=0;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    Dialog dialog;

    String BEARER_TOKEN="";
    ArrayList<Carts> filteredlist= new ArrayList<Carts>();

    public Cart_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cart_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Cart_Fragment newInstance(String param1, String param2) {
        Cart_Fragment fragment = new Cart_Fragment();
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
        View rootView= inflater.inflate(R.layout.fragment_cart_, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        BEARER_TOKEN  = preferences.getString("BEARER_TOKEN",null);//second parameter default value..

        getAlCartData();
        cartsRecycleView = rootView.findViewById(R.id.recyclerview);

        EditText earachTxt=rootView.findViewById(R.id.search_fragmentcartlist);
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

        if(finalProductValue >=1){
            Toast.makeText(getContext(), "Value AYo", Toast.LENGTH_SHORT).show();
        }

        // This Button WIll appear a CheckOut Button
        Button continue_button=(Button) rootView.findViewById(R.id.continue_button);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity(),"Just Passed");
                startCheckout();
            }
        });

        return rootView;
    }

    private void filter(String text) {

//        ArrayList<Carts> filteredlist= new ArrayList<Carts>();
        for (Carts item : cartsreturnValueList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getCartedDate().toString().toLowerCase().contains(text.toLowerCase()) ||
                    (item.getProduct().getCategory().toLowerCase().contains(text.toLowerCase()) ||
                    (item.getProduct().getName().toLowerCase().contains(text.toLowerCase())
                    ))) {
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
            cartlistFrame_adapter.filterList(filteredlist);
        }
    }

    public void getAlCartData() {

        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken="Bearer "+BEARER_TOKEN;

        Call<MessageResponse> call = productClient.getAllCartsInfoById(BearerToken);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG,"getAlCartData :  isSuccessful");
                    Log.d(TAG,response.toString());
                }
                if (response.isSuccessful()) {
                    MessageResponse messageResponse=response.body();
//                    cartsreturnValueList.addAll(messageResponse.getCartsValueList());
//                    cartsreturnValueList= messageResponse.getCartsValueList();
                    setRecentlyViewedRecycler(messageResponse.getCartsValueList());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }

        });
    }

    private void setRecentlyViewedRecycler(ArrayList<Carts> recentlyViewedDataList) {

        cartsreturnValueList.addAll(recentlyViewedDataList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        cartsRecycleView.setLayoutManager(layoutManager);
        cartlistFrame_adapter = new CartList_Adapter(getContext(),cartsreturnValueList,Cart_Fragment.this);
        cartsRecycleView.setAdapter(cartlistFrame_adapter);
        cartlistFrame_adapter.notifyDataSetChanged();
    }

    public void showDialog(Activity activity, String msg){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customcheckout);

        Button dialogButton = (Button) dialog.findViewById(R.id.payButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Toast.makeText(getContext(), "Kaam Garyo", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

    }

//  ----------------------------   Below This is a file relted to Payment ---------------------

    public void startCheckout() {

        // Hook up the pay button to the card widget and stripe instance
        Button payButton = dialog.findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> {
            Log.d(TAG,"pay button clicked: ");
            CardInputWidget cardInputWidget = dialog.findViewById(R.id.cardInputWidget);
            TextView textview_price= (TextView)getActivity().findViewById(R.id.totalPriceCart);

            // Creataing ana purchasedobject and initialaising data for further Use
            Purchasing purchasing=new Purchasing();

            //Retrieve token wherever necessary
            SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
            String UserEmail = preferences.getString("User_EMAIL",null);//second parameter default value.
            purchasing.setUserEmail(UserEmail);

            PaymentInfo paymentInfo=new PaymentInfo();
            paymentInfo.setPaymentInfo("pk_test_51JKHBOAjTCM92WB7z0EwBG7JH1mOwKnwpvdQOojyKsDta1aloOLbWHgpx25bdq0vVsadt6uOzwibXi0WqUZUJa8p00JG3zlV9n");
            paymentInfo.setPaymentMedium("US");
        paymentInfo.setPayment_Amount(Double.valueOf(textview_price.getText().toString()));
//            paymentInfo.setPayment_Amount(1000);
            purchasing.setPaymentInfo(paymentInfo);
            ArrayList<PurchasedProduct> purchasedProductsArraya =new ArrayList<PurchasedProduct>();
           if( (! cartedOne.isEmpty()) || (cartedOne !=null)){
               for(Carts carts:cartedOne){
                   PurchasedProduct purchasedProduct=new PurchasedProduct();
                   purchasedProduct.setProductId(carts.getProductid());
                   purchasedProduct.setProductQuantity(carts.getQuantity());
                   purchasedProduct.setCartId(carts.getId());
                   purchasedProductsArraya.add(purchasedProduct);

               }
               purchasing.setPurchasedproduct(purchasedProductsArraya);
               purchasedOne(purchasing);
           }


        });

    }

    public void purchasedOne(Purchasing purchasing) {


        OkHttpClient.Builder httpClient2 = new OkHttpClient.Builder();
        httpClient2.connectTimeout(60, TimeUnit.SECONDS);
        httpClient2.readTimeout(60, TimeUnit.SECONDS);
        httpClient2.writeTimeout(60, TimeUnit.SECONDS);
        Retrofit.Builder builder2 = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit2 = builder2.client(httpClient.build()).build();

        boolean bool=false;
        Interface_Product userClient = retrofit2.create(Interface_Product.class);
        String BearerToken="Bearer "+ BEARER_TOKEN;

        retrofit2.Call<MessageResponse> call=userClient.purchaseProduct(BearerToken,purchasing);
        call.enqueue(new retrofit2.Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {


                if(response.isSuccessful()){
                    dialog.cancel();
                    Log.d(TAG,"response.isSuccessful()");
                    cartsreturnValueList.clear();
                    cartedOne.clear();
                    getAlCartData();

                    LinearLayout returnmessageHodler=getView().findViewById(R.id.returnmessageHodler);
                    returnmessageHodler.setVisibility(View.VISIBLE);
                    TextView purchaseReturnMessage=getView().findViewById(R.id.purchaseReturnMessage);
                    purchaseReturnMessage.setText("Sucessfully Buyed The product, Check Email Receipt");

                    TextView purchaseReturnsucesssorfaialure=getView().findViewById(R.id.purchaseReturnsucesssorfaialure);
                    purchaseReturnsucesssorfaialure.setText("Sucess");
                    purchaseReturnsucesssorfaialure.setTextColor(getResources().getColor(R.color.teal_700));


//                    if(response.body().getReturnStatus()==1) {
//                        Log.d(TAG,response.toString());
//                        dialog.cancel();
////                        afterTheREfresh("sucess");
//                    }
                }else{
                    Log.d(TAG,"response.is  NOT Successful()");
                    dialog.cancel();
                }
                dialog.cancel();
            }

            @Override
            public void onFailure(retrofit2.Call<MessageResponse> call, Throwable t) {
                dialog.cancel();
                Log.d(TAG,"     Throwable t  "+t.toString());
            }
        });
    }

    public void afterTheREfresh(String status){
        dialog.cancel();
        if(status.equals("sucess")){
//            getAlCartData(getView());
//            returnmessageHodler
           TextView purchaseReturnMessage=getView().findViewById(R.id.purchaseReturnMessage);
            purchaseReturnMessage.setText("Sucessfully Buyed The product, Check Email Receipt");

            TextView purchaseReturnsucesssorfaialure=getView().findViewById(R.id.purchaseReturnsucesssorfaialure);
            purchaseReturnsucesssorfaialure.setText("Sucess");
            purchaseReturnsucesssorfaialure.setTextColor(getResources().getColor(R.color.teal_700));

        }
        else{
        TextView purchaseReturnMessage=getView().findViewById(R.id.purchaseReturnMessage);
        purchaseReturnMessage.setText("Failed To Buyed The product, Sorry");

        TextView purchaseReturnsucesssorfaialure=getView().findViewById(R.id.purchaseReturnsucesssorfaialure);
        purchaseReturnsucesssorfaialure.setText("Sorry");
        purchaseReturnsucesssorfaialure.setTextColor(getResources().getColor(R.color.teal_700));
    }

    }
    public void displayTheTotalorSomething(){

        Button continue_button=(Button) getView().findViewById(R.id.continue_button);

        if(!cartedOne.isEmpty() || cartedOne!=null){
            double totalCOst=0;
            for(Carts cat:cartedOne){
                double priceAmnt=cat.getQuantity()*cat.getProduct().getPrice();
                totalCOst=totalCOst+priceAmnt;
            }
            TextView totalPriceCart= getView().findViewById(R.id.totalPriceCart);
            totalPriceCart.setText(String.valueOf(totalCOst));

            TextView totalaCountOfProduct= getView().findViewById(R.id.totalaPricecountCarts);
            totalaCountOfProduct.setText("Total Price (" +cartedOne.size()+ " items)");
            continue_button.setVisibility(View.VISIBLE);
        }

        if(cartedOne.size()==0){
            Log.d(TAG," 1 ===  "+cartedOne.size());
            continue_button.setVisibility(View.GONE);
        }

    }


    // Deleting an Cart Item
    public void deleteCartItem(String id){

        SharedPreferences preferences = getContext().getApplicationContext().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String BEARER_TOKEN  = preferences.getString("BEARER_TOKEN",null);//second parameter default value.

        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken="Bearer "+ BEARER_TOKEN;
        Log.d(TAG,BearerToken);
        Call<MessageResponse> call = productClient.deleteCartdataById(BearerToken,id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if(response.body().getReturnStatus()==1){
                    Toast.makeText(getContext(),"Sucessfully Deleted",Toast.LENGTH_SHORT);
                    cartsreturnValueList.clear();
                    cartedOne.clear();
                    getAlCartData();

                    showDialog("Sucessfull", "Sucessfully Deleted");


                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.d(TAG," onFailure ");
            }
        });

    }



    public void showDialog(String title, String Message){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext()).setTitle(title).
                setMessage(Message);

        final AlertDialog alert = dialog.create();
        alert.show();

// Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 2000);

    }


}