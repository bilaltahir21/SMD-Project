package com.fast.tiffan_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.MODE_PRIVATE;

public class fragment_profile extends Fragment {
    private DatabaseReference myDatabaseReference;
    public TextView name, phone, address, town, street, city , house_num;
    public Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile , null);

        initialize_Variables(view);

        Button change_Pass = view.findViewById(R.id.btn_changeAdd);
        Button logOut = view.findViewById(R.id.btn_logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences(MainActivity.SharePrefernce, MODE_PRIVATE).edit();
                editor.putString("LogIn", "false");
                editor.apply();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        final View profile = view.findViewById(R.id.profile);

        change_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeAddress(profile);
            }
        });


        btn=view.findViewById(R.id.btn_changeAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogActivity alert=new DialogActivity();
                alert.show(getFragmentManager(), "PopUp");
            }
        });

        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(MainActivity.SharePrefernce, MODE_PRIVATE);
        String phone = prefs.getString("phone", "notsaved");//"No name defined" is the default value.

        if(!phone.equals("notsaved")){
            DisplayUserData(phone);
        }
        return view;
    }

    private void DisplayUserData(final String number) {

        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(number);

        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setAllCaps(true);
                name.setText(dataSnapshot.child("First Name").getValue().toString() + " " + dataSnapshot.child("Last Name").getValue().toString());
                phone.setText(number);
                city.setText(dataSnapshot.child("Address").child("City").getValue().toString());
                town.setText(dataSnapshot.child("Address").child("Town").getValue().toString());
                house_num.setText(dataSnapshot.child("Address").child("House").getValue().toString());
                street.setText(dataSnapshot.child("Address").child("Street").getValue().toString());
                address.setText((town.getText().toString())+", "+(street.getText().toString())+", "+(house_num.getText().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialize_Variables(View view) {

        name = view.findViewById(R.id.txt_name);
        phone = view.findViewById(R.id.txt_number);
        address = view.findViewById(R.id.txt_address);
        town = view.findViewById(R.id.txt_town);
        city = view.findViewById(R.id.txt_city);
        street=view.findViewById(R.id.txt_street);
        house_num = view.findViewById(R.id.txt_houseNum);
    }

    private void ChangeAddress(View profile) {

//        change_address fragment = new change_address();
//        assert getFragmentManager() != null;
//        profile.setVisibility(View.GONE);
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.ChangeAdd_fragment, fragment);
//        //fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

}