package com.fast.tiffan_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Restaurant extends AppCompatActivity  {


    ViewPager viewpager ;
    PagerViewAdapter pagerView_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        viewpager = findViewById(R.id.fragment_container);
        pagerView_adapter = new PagerViewAdapter(getSupportFragmentManager(), 4);

        viewpager.setAdapter(pagerView_adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getOrCreateBadge(R.id.action_cart).setNumber(2);
        bottomNavigationView.setElevation(100);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        viewpager.setCurrentItem(0);
                        Toast.makeText(Restaurant.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_orders:
                        viewpager.setCurrentItem(1);
                        Toast.makeText(Restaurant.this, "Orders", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        viewpager.setCurrentItem(2);
                        Toast.makeText(Restaurant.this, "Profile", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_cart:
                        viewpager.setCurrentItem(3);
                        Toast.makeText(Restaurant.this, "Cart", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }
}
