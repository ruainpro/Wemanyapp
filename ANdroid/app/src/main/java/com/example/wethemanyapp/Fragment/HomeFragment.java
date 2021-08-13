package com.example.wethemanyapp.Fragment;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wethemanyapp.R;
import com.example.wethemanyapp.Url;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = HomeFragment.class.getSimpleName();

    TextView allCategory;

    int carbonconstant= 52;

    int companyemission= 100;

    String UserEmail="";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
       View view= inflater.inflate(R.layout.fragment_home, container, false);
        //Retrieve token wherever necessary
        SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        UserEmail = preferences.getString("User_Name",null);//second parameter default value.

//        Getting flating button and initialising to here
        FloatingActionButton floatingActionButton=(FloatingActionButton) view.findViewById(R.id.add_fab);
        // On clicking the floating Button for calculating it will render it to here
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlert("Payment completed",
                        true);
            }
        });

        TextView home_nameofront=view.findViewById(R.id.home_nameofront);
        home_nameofront.setText(UserEmail);

        // Social Media Part
        ImageView imageView4_Facebook=(ImageView) view.findViewById(R.id.imageView4);
        imageView4_Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/wethemany.com.au"));
                startActivity(browserIntent);

            }
        });

        ImageView imageView10_Youtube=(ImageView) view.findViewById(R.id.imageView10);
        imageView10_Youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
                startActivity(browserIntent);
            }
        });

        ImageView imageView11_Instagram=(ImageView) view.findViewById(R.id.imageView11);
        imageView11_Instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/we.themany/"));
                startActivity(browserIntent);


            }
        });

        ImageView imageView12_Gmail=(ImageView) view.findViewById(R.id.imageView12);
        imageView12_Gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email= new Intent(Intent.ACTION_SENDTO);
                email.setData(Uri.parse("mailto:hello@wethemany.com.au"));
                email.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                email.putExtra(Intent.EXTRA_TEXT, "My Email message");
                startActivity(email);

            }
        });
        ImageView imageView13_Whatsapp=(ImageView) view.findViewById(R.id.imageView13);
        imageView13_Whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0123456789"));
                startActivity(callIntent);

            }
        });
        // Social Media Part


        return view;
    }

    private void displayAlert(@NonNull String title, boolean restartDemo){

        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.co2emissioncalculator, null);
        builder.setView(customLayout);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();


        ImageView imageView14_downloadfile = customLayout.findViewById(R.id.imageView14_downloadfile);
        imageView14_downloadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://drive.google.com/file/d/1F5FAtPOdf17Pj2koLdoHcYq0587L4EvP/view"));
                startActivity(browserIntent);

            }
        });


        Button buttonToCalculateoOfProductValue = customLayout.findViewById(R.id.button3_c02calcuate);
        buttonToCalculateoOfProductValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edittextofproductvalue = customLayout.findViewById(R.id.c02_productvaluefinal);
                String edittextofproductvalueStringVal=edittextofproductvalue.getText().toString();
                if(! edittextofproductvalueStringVal.isEmpty()){
                    double productvalue= Double.valueOf(edittextofproductvalueStringVal);

                    CardView cardview_co2emission=customLayout.findViewById(R.id.cardview_co2emission);
                    TextView c02emissison_actualanswer=customLayout.findViewById(R.id.c02emissison_actualanswer);

                    double absosluteEmission=(productvalue/100);
                    double c02EmisisionValue=((productvalue*carbonconstant*absosluteEmission)/100);

                    cardview_co2emission.setVisibility(View.VISIBLE);
                    c02emissison_actualanswer.setText(String.valueOf(c02EmisisionValue) +" Per Year ");
                }else{
                    edittextofproductvalue.setError("Please Enter Data");

                }

            }
        });


        Button button_c02closeIt = customLayout.findViewById(R.id.button_c02closeIt);
        button_c02closeIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });




    }


}