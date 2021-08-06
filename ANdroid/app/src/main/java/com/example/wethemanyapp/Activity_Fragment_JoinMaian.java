package com.example.wethemanyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.wethemanyapp.Adapter.ViewPagerAdapter;
import com.example.wethemanyapp.Fragment.Cart_Fragment;
import com.example.wethemanyapp.Fragment.History_Fragment;
import com.example.wethemanyapp.Fragment.HomeFragment;
import com.example.wethemanyapp.Fragment.MyAccount_Fragment;
import com.example.wethemanyapp.Fragment.Product_Fragment;
import com.google.android.material.badge.BadgeDrawable;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_join_maian);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        homeFragment = new HomeFragment();
        product_Fragment = new Product_Fragment();
        history_fragment = new History_Fragment();
        myAccount_fragment = new MyAccount_Fragment();
        cart_Fragment=new Cart_Fragment();
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(homeFragment, "Home");
        viewPagerAdapter.addFragment(product_Fragment, "Product");
        viewPagerAdapter.addFragment(cart_Fragment, "Cart");
//        viewPagerAdapter.addFragment(history_fragment, "History");
        viewPagerAdapter.addFragment(myAccount_fragment, "Account");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_icons8_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_open_box);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_cart_black);
//        tabLayout.getTabAt(3).setIcon(R.drawable.ic_history);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_user);

        BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(12);

    }

//    private class ViewPagerAdapter extends FragmentPagerAdapter {
//
//        private List<Fragment> fragments = new ArrayList<>();
//        private List<String> fragmentTitle = new ArrayList<>();
//
//        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
//            super(fm, behavior);
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            fragments.add(fragment);
//            fragmentTitle.add(title);
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return fragmentTitle.get(position);
//        }
//    }
}