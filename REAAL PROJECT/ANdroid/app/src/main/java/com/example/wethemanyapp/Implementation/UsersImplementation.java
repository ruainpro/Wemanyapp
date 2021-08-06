package com.example.wethemanyapp.Implementation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.example.wethemanyapp.Activity_Fragment_JoinMaian;
import com.example.wethemanyapp.Interface.Interface_Users;
import com.example.wethemanyapp.Model.JwtResponse;
import com.example.wethemanyapp.Model.MessageResponse;
import com.example.wethemanyapp.Model.User;
import com.example.wethemanyapp.R;
import com.example.wethemanyapp.Url;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersImplementation {

    private static final String TAG = UsersImplementation.class.getSimpleName();
    public String returnresponses;

    String BASE_URL = "http://10.0.2.2:8080/";
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.client(httpClient.build()).build();
    Context context;
    Activity activity;
    String userloginreturn="false";

    public UsersImplementation(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }


    public UsersImplementation() {
        this.context = context;
        this.activity = activity;
    }


    public String getloginstatus(){
//        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

        userloginreturn="true";
        return "true";
    }

    public String getloginstatusfalse(){

        userloginreturn="false";
        return "false";
    }


    public String getLoginCHeck(User user) {

        boolean bool=false;
        Interface_Users userClient = retrofit.create(Interface_Users.class);
//        Call<JwtResponse> call = userClient.loginUser(user);
        Call<JwtResponse> call=userClient.loginUser(user);

        call.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                Log.d(TAG, "------------------------------------------------");
                Log.d(TAG, "------------------------------------------------");

                if(context !=null) {
                    if(!response.isSuccessful())
                    {
                        EditText editText=activity.findViewById(R.id.ediText_login_email);
                        editText.setText("Invalid Credentials");
                        EditText editText2=activity.findViewById(R.id.ediText_login_password);
                        editText2.setText("Invalid Credentials");
                        getloginstatusfalse();
                    }
                   if(response.isSuccessful()) {
                       getloginstatus();
                       Url.user=response.body().getEmail();
                       String mysession = response.headers().get("Set-Cookie");
                       Url.cookie=response.body().getAccessToken();

                       System.out.println(response.toString());
                       Intent intent = new Intent(context, Activity_Fragment_JoinMaian.class);
                       intent.putExtra("mycookie",mysession);
                       context.startActivity(intent);
                   }
                    }
                }
            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
        return userloginreturn;
    }

    public String registerMe(User user) {

        boolean bool=false;
        Interface_Users userClient = retrofit.create(Interface_Users.class);
//        Call<JwtResponse> call = userClient.loginUser(user);
        Call<MessageResponse> call=userClient.signUpUsers(user);

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(context !=null) {
                    if(!response.isSuccessful())
                    {
                        EditText editText=activity.findViewById(R.id.ediText_register_email);
                        editText.setError(" Email Already Existed");
                        getloginstatusfalse();
                    }
                    if(response.isSuccessful()) {
                        Toast.makeText(context, "Sucessfully Created An Account", Toast.LENGTH_SHORT).show();
                        getloginstatus();

                    }
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
        return userloginreturn;


    }
}