package com.example.wethemanyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.wethemanyapp.Adapter.ViewPagerAdapter;
import com.example.wethemanyapp.Fragment.Cart_Fragment;
import com.example.wethemanyapp.Fragment.History_Fragment;
import com.example.wethemanyapp.Fragment.HomeFragment;
import com.example.wethemanyapp.Fragment.MyAccount_Fragment;
import com.example.wethemanyapp.Fragment.Product_Fragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Activity_Fragment_JoinMaian extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private HomeFragment homeFragment;
    private Product_Fragment product_Fragment;
    private History_Fragment history_fragment;
    private MyAccount_Fragment myAccount_fragment;
    private Cart_Fragment cart_Fragment;

    FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    MenuItem meneuitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_join_maian);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeFragment = new HomeFragment();
        product_Fragment = new Product_Fragment();
        history_fragment = new History_Fragment();
        myAccount_fragment = new MyAccount_Fragment();
        cart_Fragment = new Cart_Fragment();

        frameLayout = (FrameLayout) findViewById(R.id.mainframe);

        Bundle bundle = new Bundle();
        homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);


        Bundle bundle2 = new Bundle();
        product_Fragment = new Product_Fragment();
        product_Fragment.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        cart_Fragment = new Cart_Fragment();
        cart_Fragment.setArguments(bundle3);

        Bundle bundle4 = new Bundle();
        myAccount_fragment = new MyAccount_Fragment();
        myAccount_fragment.setArguments(bundle4);


        setFragment(homeFragment);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottmnavigationmainnavs);
        // It will select whichc icon or tabs is selected from menu or id and will render to it'ss specific Framelayout
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_Product:
                        setFragment(product_Fragment);
                        return true;

                    case R.id.nav_Cart:
                        setFragment(cart_Fragment);
                        return true;

                    case R.id.nav_Account:
                        setFragment(myAccount_fragment);
                        return true;

                    default:
                        return false;

                }

            }
        });

    }
    // It will help to add the fragment on the container frame orlayout i.e mainframe
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.replace(R.id.mainframe, fragment);
        fragmentTransaction.commit();
    }

}