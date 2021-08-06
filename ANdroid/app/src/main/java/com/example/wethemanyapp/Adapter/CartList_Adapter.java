package com.example.wethemanyapp.Adapter;

import static com.example.wethemanyapp.Fragment.Cart_Fragment.finalProductValue;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.wethemanyapp.Fragment.Cart_Fragment;
import com.example.wethemanyapp.Fragment.HomeFragment;
import com.example.wethemanyapp.Model.Carts;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.R;
import com.example.wethemanyapp.Url;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CartList_Adapter extends RecyclerView.Adapter<CartList_Adapter.CartListViewHolder>{

    Context context;
    ArrayList<Carts> carts= new ArrayList<>();
    private static final String TAG = CartList_Adapter.class.getSimpleName();
    RequestManager glide;
    double totalCOst;
    Cart_Fragment cart_Fragment;

    public CartList_Adapter(Context context, ArrayList<Carts> carts,Cart_Fragment cart_Fragment) {
        this.context = context;
        this.carts = carts;
        this.glide = Glide.with(context);
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
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {

        final Carts ostbindit = carts.get(position);
        holder.productName.setText(ostbindit.getProduct().getName());
        holder.productCount.setText("Quantity: "+String.valueOf(ostbindit.getQuantity()));

        double priceAmnt=ostbindit.getQuantity()*ostbindit.getProduct().getPrice();
        if(position==0){
            totalCOst=0;
        }
        totalCOst=totalCOst+priceAmnt;
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

        holder.Image_DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(carts.size()-1==position){
           TextView totalPriceCart= cart_Fragment.getActivity().findViewById(R.id.totalPriceCart);
            totalPriceCart.setText(String.valueOf(totalCOst));

            TextView totalaCountOfProduct= cart_Fragment.getActivity().findViewById(R.id.totalaPricecountCarts);
            totalaCountOfProduct.setText("Total Price (" +carts.size()+ " items)");
        }
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

        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.image);
            Image_DeleteBtn = itemView.findViewById(R.id.Image_Delete);

            productName = itemView.findViewById(R.id.title);
            productCost = itemView.findViewById(R.id.price);
            productCount = itemView.findViewById(R.id.cart_product_count);

            cartitemcartedDate = itemView.findViewById(R.id.cartitemcartedDate);

        }
    }

}
