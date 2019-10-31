package com.fast.tiffan_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class fragment_order extends Fragment {


    Context context = getActivity();
    DatabaseReference myDatabaseReference;

    TextView t1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);




//
//        SharedPreferences prefs = context.getSharedPreferences(MainActivity.SharePrefernce, MODE_PRIVATE);
//        String phone = prefs.getString("phone", "notsaved"); //"No name defined" is the default value.
//        if (!phone.equals("notsaved")) {
//            myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Order");
//
//        }

        return view;
    }
}