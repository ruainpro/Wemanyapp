package com.example.wethemanyapp.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.wethemanyapp.Adapter.CartList_Adapter;
import com.example.wethemanyapp.Adapter.ProductFrame_Adapter;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.Model.Carts;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.PaymentInfo;
import com.example.wethemanyapp.Model.Product;
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

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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
        getAlCartData(rootView);
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

        Button continue_button=(Button) rootView.findViewById(R.id.continue_button);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity(),"Just Passed");
                startCheckout();
                PaymentConfiguration.init(
                        getContext(),
                        "pk_test_51JKHBOAjTCM92WB7z0EwBG7JH1mOwKnwpvdQOojyKsDta1aloOLbWHgpx25bdq0vVsadt6uOzwibXi0WqUZUJa8p00JG3zlV9n"
                );
            }
        });


        return rootView;
    }

    private void filter(String text) {

        ArrayList<Carts> filteredlist= new ArrayList<Carts>();
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



    public void getAlCartData(View view) {

        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken="Bearer "+ Url.cookie;

        Call<MessageResponse> call = productClient.getAllCartsInfoById(BearerToken);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG,"isSuccessful");
                    Log.d(TAG,response.toString());
                }
                if (response.isSuccessful()) {
                    Log.d(TAG,response.toString());

                    MessageResponse messageResponse=response.body();
                    cartsreturnValueList= messageResponse.getCartsValueList();
                    setRecentlyViewedRecycler(cartsreturnValueList);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }

        });


    }


    private void setRecentlyViewedRecycler(ArrayList<Carts> recentlyViewedDataList) {
//        ArrayList<Carts> productarray = new ArrayList<Carts>(recentlyViewedDataList);

        Log.d(TAG,"=============================");
        Log.d(TAG,recentlyViewedDataList.toString());
        Log.d(TAG,"=============================");
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        cartsRecycleView.setLayoutManager(layoutManager);
        cartlistFrame_adapter = new CartList_Adapter(getContext(),recentlyViewedDataList,Cart_Fragment.this);
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


    private void startCheckout() {

        TextView textview_price= (TextView)getActivity().findViewById(R.id.totalPriceCart);

        Log.d(TAG,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//        Log.d(TAG,textview_price.getText().toString());
        Log.d(TAG,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");


        Purchasing purchasing=new Purchasing();
        purchasing.setUserEmail(Url.user);

        PaymentInfo paymentInfo=new PaymentInfo();
        paymentInfo.setPaymentInfo("pk_test_51JKHBOAjTCM92WB7z0EwBG7JH1mOwKnwpvdQOojyKsDta1aloOLbWHgpx25bdq0vVsadt6uOzwibXi0WqUZUJa8p00JG3zlV9n");
        paymentInfo.setPaymentMedium("US");
//        paymentInfo.setPayment_Amount(Double.valueOf(textview_price.getText().toString()));
        paymentInfo.setPayment_Amount(1000);
        purchasing.setPaymentInfo(paymentInfo);


        boolean bool=false;
        Interface_Product userClient = retrofit.create(Interface_Product.class);
//        Call<JwtResponse> call = userClient.loginUser(user);
        String BearerToken="Bearer "+ Url.cookie;

        retrofit2.Call<String> call=userClient.getpaymentWork(BearerToken,purchasing);

        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                new PayCallback(Cart_Fragment.this);
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {

            }
        });



        // Hook up the pay button to the card widget and stripe instance
        Button payButton = dialog.findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> {
            Log.d(TAG,"123433333333333");

            CardInputWidget cardInputWidget = dialog.findViewById(R.id.cardInputWidget);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);
            }
        });
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message);
        if (restartDemo) {
            builder.setPositiveButton("Restart demo",
                    (DialogInterface dialog, int index) -> {
                        CardInputWidget cardInputWidget = getActivity().findViewById(R.id.cardInputWidget);
                        cardInputWidget.clear();
                        startCheckout();
                    });
        } else {
            builder.setPositiveButton("Ok", null);
        }
        builder.create().show();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).toString(),
                type
        );

        // The response from the server includes the Stripe publishable key and
        // PaymentIntent details.
        // For added security, our sample app gets the publishable key from the server
        String stripePublishableKey = responseMap.get("pk_test_51JKHBOAjTCM92WB7z0EwBG7JH1mOwKnwpvdQOojyKsDta1aloOLbWHgpx25bdq0vVsadt6uOzwibXi0WqUZUJa8p00JG3zlV9n");
        paymentIntentClientSecret = responseMap.get("sk_test_51JKHBOAjTCM92WB7EGezDMkWyFzs4mGxr5sIUemdVyBkpWZT6GIiHaTKKazE7j1kaWPqlGkiolYBeYcwbkR66xYM00g7mmMjoe");

        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
        stripe = new Stripe(
                getContext(),
                Objects.requireNonNull(stripePublishableKey)
        );
    }

    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<Cart_Fragment> activityRef;

        PayCallback(@NonNull Cart_Fragment activity) {
            activityRef = new WeakReference<>(activity);
        }


        @Override
        public void onFailure(Call call, Throwable t) {
            final Cart_Fragment activity = activityRef.get();
            if (activity == null) {
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                  Toast.makeText(activity.getContext(), "Error: " +"ss", Toast.LENGTH_LONG ).show();
                }

            });

        }

//        @Override
//        public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//
//            activity.runOnUiThread(() ->
//                    Toast.makeText(
//                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
//                    ).show()
//            );
//        }



        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response) {
            final Cart_Fragment activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if(!response.isSuccessful()){

            }

            if (!response.isSuccessful()) {

//                Cart_Fragment.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getContext(),"any mesage",Toast.LENGTH_LONG).show();
                    }
                });


                new Thread(new Runnable() {                    @Override
                    public void run() {
                        Toast.makeText(activity.getContext(), "Error: " + response.toString(), Toast.LENGTH_LONG).show();
                    }
                });
//                );
            } else {
                try {
                    activity.onPaymentSuccess(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<Cart_Fragment> activityRef;

        PaymentResultCallback(@NonNull Cart_Fragment activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final Cart_Fragment activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent),
                        true
                );
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(),
                        false
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final Cart_Fragment activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString(), false);
        }
    }
}