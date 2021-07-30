 package com.example.wethemanyapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wethemanyapp.Implementation.UsersImplementation;
import com.example.wethemanyapp.Model.User;
import com.google.android.material.tabs.TabLayout;

 public class MainActivity extends AppCompatActivity {

     UsersImplementation usersImplenttt = new UsersImplementation(this,MainActivity.this);

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout =(TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){

                LinearLayout loginLayout =findViewById(R.id.loginLayout);
                LinearLayout registerLayout =findViewById(R.id.registerLayout);
                int position = tab.getPosition();
                switch(position) {
                    case 0:
//                        registerLayout.setVisibility(View.);
                        loginLayout.setVisibility(View.VISIBLE);
                        registerLayout.setVisibility(View.GONE);
                        // code block
                        break;
                    case 1:

                        loginLayout.setVisibility(View.GONE);
                        registerLayout.setVisibility(View.VISIBLE);

                        // code block
                        break;
                    default:
                        loginLayout.setVisibility(View.VISIBLE);
                        registerLayout.setVisibility(View.GONE);
                        // code block
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout loginLayout =findViewById(R.id.loginLayout);
                loginLayout.setVisibility(View.VISIBLE);


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Button loginBtn= findViewById(R.id.button_login);
        Button signBtn= findViewById(R.id.button_register_signup);

        EditText ediText_register_email= (EditText) findViewById(R.id.ediText_register_email);
        EditText ediText_register_password= (EditText) findViewById(R.id.ediText_register_password);
        EditText ediText_register_confirmpassword= (EditText) findViewById(R.id.ediText_register_confirmpassword);

        // Login Button Action
        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                EditText ediText_login_email= (EditText) findViewById(R.id.ediText_login_email);
                String ediText_login_emailText=ediText_login_email.getText().toString();
                EditText ediText_login_password= (EditText) findViewById(R.id.ediText_login_password);
                String ediText_login_passwordText=ediText_login_password.getText().toString();

                if(ediText_login_emailText.isEmpty() || ediText_login_passwordText.isEmpty() ){
                    ediText_login_email.setError("Enter Email Please");
                    ediText_login_password.setError("Enter Password Please");

                    Toast.makeText(MainActivity.this, "Enter Email and Password Please", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    User user=new User();
                    user.setEmail(ediText_login_emailText);
                    user.setPassword(ediText_login_passwordText);

                    Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
                    usersImplenttt.getLoginCHeck(user);


                }
            }
        });

        // SignUp Button Action
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText ediText_register_email= (EditText) findViewById(R.id.ediText_register_email);
                String ediText_register_emailText=ediText_register_email.getText().toString();
                EditText ediText_register_password= (EditText) findViewById(R.id.ediText_register_password);
                String ediText_register_passwordText=ediText_register_password.getText().toString();
                EditText ediText_register_confirmpassword= (EditText) findViewById(R.id.ediText_register_confirmpassword);
                String ediText_register_confirmpasswordText=ediText_register_confirmpassword.getText().toString();


                if(ediText_register_emailText.isEmpty() || ediText_register_passwordText.isEmpty() || ediText_register_confirmpasswordText.isEmpty()){

                    ediText_register_email.setError("Enter Email Please");
//                    ediText_login_email.setForeground(new C);
                    ediText_register_password.setError("Enter Password Please");
                    ediText_register_confirmpassword.setError("Enter Confirm Password Please");
                    return;
                }else{

                    if(ediText_register_passwordText.equalsIgnoreCase(ediText_register_confirmpasswordText)){

                    }else{
                        ediText_register_password.setError("Password and Confirm Password Donot Match Please");
                        ediText_register_confirmpassword.setError("Password and Confirm Password Donot Match Please");
                    }
                }

            }
        });



    }
}