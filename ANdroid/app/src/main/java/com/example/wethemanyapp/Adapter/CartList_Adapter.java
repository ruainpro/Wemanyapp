package com.example.wethemanyapp.Adapter;

import static com.example.wethemanyapp.Fragment.Cart_Fragment.*;
import static com.example.wethemanyapp.Fragment.Cart_Fragment.finalProductValue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.wethemanyapp.Fragment.Cart_Fragment;
import com.example.wethemanyapp.Fragment.HomeFragment;
import com.example.wethemanyapp.Interface.Interface_Product;
import com.example.wethemanyapp.Model.Carts;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.R;
import com.example.wethemanyapp.Url;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartList_Adapter extends RecyclerView.Adapter<CartList_Adapter.CartListViewHolder>{

    Context context;
    ArrayList<Carts> carts= new ArrayList<Carts>();
    private static final String TAG = CartList_Adapter.class.getSimpleName();
    RequestManager glide;
    double totalCOst;
    Cart_Fragment cart_Fragment;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Url.URLone).addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.client(httpClient.build()).build();

    public CartList_Adapter(Context context, ArrayList<Carts> carts,Cart_Fragment cart_Fragment) {
        this.context = context;
        this.carts = carts;
        this.glide = Glide.with(context.getApplicationContext());
        this.cart_Fragment=cart_Fragment;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartList_Adapter.CartListViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final Carts ostbindit = carts.get(position);
        holder.productName.setText(ostbindit.getProduct().getName());
        holder.productCount.setText("Quantity: "+String.valueOf(ostbindit.getQuantity()));

        double priceAmnt=ostbindit.getQuantity()*ostbindit.getProduct().getPrice();
        holder.productCost.setText(" Price: "+String.valueOf(priceAmnt));

        holder.productName.setText(ostbindit.getProduct().getName());
        Date dateMe= Date.from(ostbindit.getCartedDate().toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/dd/");
        String date = sdf.format(dateMe);
        holder.cartitemcartedDate.setText("Added On: "+date);

        if(ostbindit.getProduct().getImages() !=null){
            String imageUrl= Url.URLone+"api/auth/getImages/"+ostbindit.getProduct().getImages();
            glide.load(imageUrl).into(holder.productImage);
        }

        holder.checkBox2_addforpayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG,"Status : "+isChecked);

                if(isChecked ==true){
                    cart_Fragment.cartedOne.add(ostbindit);
                }else{
                    cart_Fragment.cartedOne.remove(position);
                }
                cart_Fragment.displayTheTotalorSomething();

            }
        });

        holder.Image_DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteImem(ostbindit.getId());
                notifyDataSetChanged();
            }
        });

    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Carts> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        carts = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }


    // Inner Class
    public static class CartListViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage,Image_DeleteBtn;
        TextView productName, productCost, productCount, cartitemcartedDate;
        ConstraintLayout constraintLayout;
        CheckBox checkBox2_addforpayment;

        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.image);
            Image_DeleteBtn = itemView.findViewById(R.id.Image_Delete);

            productName = itemView.findViewById(R.id.title);
            productCost = itemView.findViewById(R.id.price);
            productCount = itemView.findViewById(R.id.cart_product_count);

            cartitemcartedDate = itemView.findViewById(R.id.cartitemcartedDate);

            checkBox2_addforpayment = itemView.findViewById(R.id.checkBox2_addforpayment);

        }
    }

    public void deleteImem(String id){

        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String BEARER_TOKEN  = preferences.getString("BEARER_TOKEN",null);//second parameter default value.

        Interface_Product productClient = retrofit.create(Interface_Product.class);
        String BearerToken="Bearer "+ BEARER_TOKEN;
        Log.d(TAG,BearerToken);
        Call<MessageResponse> call = productClient.deleteCartdataById(BearerToken,id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if(response.body().getReturnStatus()==1){
                    Toast.makeText(context,"Sucessfully Deleted",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            Log.d(TAG," onFailure ");
            }
        });

    }

}
