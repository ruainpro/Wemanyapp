package com.example.wethemanyapp.Adapter;

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
import com.example.wethemanyapp.Fragment.MyAccount_Fragment;
import com.example.wethemanyapp.Model.Carts;
import com.example.wethemanyapp.Model.Product;
import com.example.wethemanyapp.Model.PurchasedProduct;
import com.example.wethemanyapp.Model.Purchasing;
import com.example.wethemanyapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Historyproduct_adapter extends RecyclerView.Adapter<Historyproduct_adapter.HistoryproductViewHolder>{

    Context context;
    ArrayList<Purchasing> purchasing= new ArrayList<>();
    private static final String TAG = Historyproduct_adapter.class.getSimpleName();
    RequestManager glide;
    double totalCOst;
    MyAccount_Fragment myAccount_Fragment;


    public Historyproduct_adapter(Context context, ArrayList<Purchasing> purchasing, MyAccount_Fragment myAccount_Fragment) {
        this.context = context;
        this.purchasing = purchasing;
        this.myAccount_Fragment = myAccount_Fragment;
        glide = Glide.with(context);

    }

    @NonNull
    @Override
    public HistoryproductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.historyitem, parent, false);

        return new Historyproduct_adapter.HistoryproductViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HistoryproductViewHolder holder, int position) {
        final Purchasing purchasingFinal = purchasing.get(position);

        String valueofTotalProduct="";
        String totalProductName="";

        for (int i = 0; i <purchasingFinal.getPurchasedproduct().size() ; i++) {
            if(i==0){
                totalProductName=purchasingFinal.getPurchasedproduct().get(i).getProductresponse().getName();
            }else{
                totalProductName=totalProductName+" , "+ purchasingFinal.getPurchasedproduct().get(i).getProductresponse().getName();
            }
        }

        Date dateMe= Date.from(purchasingFinal.getPurchasedDate().toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/dd/");
        String date = sdf.format(dateMe);

        holder.historyCartDate.setText(date);
        holder.productName.setText(totalProductName);
        holder.hisstorydetailsid.setText("Total Product: "+String.valueOf(purchasingFinal.getPurchasedproduct().size()));
        holder.hisotrydetailprice.setText("Total Amount: "+ String.valueOf(purchasingFinal.getPaymentInfo().getPayment_Amount()));

    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Purchasing> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        purchasing = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return purchasing.size();
    }

    // Inner Class
    public static class HistoryproductViewHolder extends RecyclerView.ViewHolder{

        TextView productName, hisstorydetailsid, hisotrydetailprice,historyCartDate;
        ConstraintLayout constraintLayout;

        public HistoryproductViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.hisitoryproductid);
            hisstorydetailsid = itemView.findViewById(R.id.hisstorydetailsid);
            hisotrydetailprice = itemView.findViewById(R.id.hisotrydetailprice);
            historyCartDate = itemView.findViewById(R.id.historyCartDate);

        }
    }
}
