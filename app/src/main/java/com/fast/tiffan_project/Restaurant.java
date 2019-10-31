package com.fast.tiffan_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Restaurant extends AppCompatActivity  {


    ViewPager viewpager ;
    PagerViewAdapter pagerView_adapter;
    private CartItems MyCart = CartItems.get_Instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "NETWORK NOT AVAILABLE!", Toast.LENGTH_LONG);
            setContentView(R.layout.no_internet);
        }

        else {

            Redirecting();


            setContentView(R.layout.activity_restaurant);

            viewpager = findViewById(R.id.fragment_container);
            pagerView_adapter = new PagerViewAdapter(getSupportFragmentManager(), 4);

            viewpager.setAdapter(pagerView_adapter);

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.getOrCreateBadge(R.id.action_cart).setNumber(MyCart.getSize());
            bottomNavigationView.setElevation(100);


//            bottomNavigationView.setOnNavigationItemSelectedListener(bottommOnNavigationItemSelectedListener);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            viewpager.setCurrentItem(0 , false);
                            Toast.makeText(Restaurant.this, "Home", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.action_orders:
                            viewpager.setCurrentItem(1 , false);
                            Toast.makeText(Restaurant.this, "Orders", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.action_profile:
                            viewpager.setCurrentItem(2 , false);
                            Toast.makeText(Restaurant.this, "Profile", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.action_cart:
                            viewpager.setCurrentItem(3, false);
                            Toast.makeText(Restaurant.this, "Cart", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return true;
                }
            });

//            fm.beginTransaction().add(R.id.fragment_container , fragmentOrder , "Order")
//                    .addToBackStack(null).hide(fragmentOrder).commit();
//            fm.beginTransaction().add(R.id.fragment_container , fragmentProfile , "Profile")
//                    .addToBackStack(null).hide(fragmentProfile).commit();
//            fm.beginTransaction().add(R.id.fragment_container , fragmentCart , "Cart")
//                    .addToBackStack(null).hide(fragmentCart).commit();
//
//            fm.beginTransaction().add(R.id.fragment_container , fragmentMenu , "menu")
//                    .addToBackStack(null);


//            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });

            viewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

        }

    }

    private void Redirecting() {

        SharedPreferences prefs = getSharedPreferences(MainActivity.SharePrefernce, MODE_PRIVATE);
        String Log_In = prefs.getString("LogIn", "false");//"No name defined" is the default value.

        if(Log_In.equals("false")){
            startActivity(new Intent(Restaurant.this, MainActivity.class));
            finish();
        }

    }

    public void set_Badge() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getOrCreateBadge(R.id.action_cart).setNumber(MyCart.getSize());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener bottommOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.action_home:
//                    fm.beginTransaction().hide(active).show(fragmentMenu).commit();
//                    active = fragmentMenu;
////                            viewpager.setCurrentItem(0);
//                    Toast.makeText(Restaurant.this, "Home", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_orders:
//                    fm.beginTransaction().hide(active).show(fragmentOrder).commit();
////                            viewpager.setCurrentItem(1);
//                    active=fragmentOrder;
//                    Toast.makeText(Restaurant.this, "Orders", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_profile:
//                    fm.beginTransaction().hide(active).show(fragmentProfile).commit();
//                    active=fragmentProfile;
////                            viewpager.setCurrentItem(2);
//                    Toast.makeText(Restaurant.this, "Profile", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_cart:
//                    fm.beginTransaction().hide(active).show(fragmentCart).commit();
//                    active=fragmentCart;
////                            viewpager.setCurrentItem(3);
//                    Toast.makeText(Restaurant.this, "Cart", Toast.LENGTH_SHORT).show();
//                    return true;
//            }
//            return false;
//        }
//    };


}//end of class
