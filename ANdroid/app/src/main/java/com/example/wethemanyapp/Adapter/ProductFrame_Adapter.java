package com.example.wethemanyapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.wethemanyapp.Fragment.Product_Fragment;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.Product_Detail;
import com.example.wethemanyapp.R;
import com.example.wethemanyapp.Url;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductFrame_Adapter extends RecyclerView.Adapter<ProductFrame_Adapter.ProductViewHolder>{

    Context context;
    ArrayList<Product> product= new ArrayList<>();
    ArrayList<Product> original= new ArrayList<>();
    private static final String TAG = ProductFrame_Adapter.class.getSimpleName();
    RequestManager glide;
    Product_Fragment product_fragment;

    public ProductFrame_Adapter(Context context, ArrayList<Product> product, Product_Fragment product_fragment) {
        this.context = context;
        this.product = product;
        glide = Glide.with(context.getApplicationContext());
        this.product_fragment=product_fragment;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_items, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final Product ostbindit = product.get(position);

        holder.productName.setText(product.get(position).getName());
        holder.productCost.setText(String.valueOf(product.get(position).getPrice()));
        if(ostbindit.getImages() !=null){
            String imageUrl= Url.URLone+"api/auth/getImages/"+ostbindit.getImages();
            glide.load(imageUrl).into(holder.productImage);
        }
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Product_Detail.class);
                intent.putExtra("product_Id",product.get(position).getId());
                intent.putExtra("cookie","cookie");

                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
            }
        });
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Product> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        product = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
//        System.out.println(product.toString());
        return product.size();
    }

    public  static class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage,deleteImage;
        TextView productName, productCost;
        ConstraintLayout constraintLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

//            productImage = itemView.findViewById(R.id.pr);
            productName = itemView.findViewById(R.id.prod_name);
            productCost = itemView.findViewById(R.id.prod_qty);
            productImage = itemView.findViewById(R.id.prod_image);
            constraintLayout=itemView.findViewById(R.id.recently_layout);

        }
    }


}
