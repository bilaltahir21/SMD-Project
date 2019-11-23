package com.fast.tiffan_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class fragment_profile extends Fragment {

    DatabaseReference myDatabaseReference;

    TextView name, phone, address, town, city , house_num , street;
    String mAddress=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile , null);

        initialize_Variables(view);

        Button change_Address = view.findViewById(R.id.btn_changeAdd);
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


        change_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogActivity alert=new DialogActivity();
                alert.show(getFragmentManager(), "PopUp");
            }
        });

//        change_Address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChangeAddress(profile);
//            }
//        });


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
                String txt_name, txt_city, txt_town, txt_street, txt_house;
                try {
                    txt_name = dataSnapshot.child("First Name").getValue().toString() + " " + dataSnapshot.child("Last Name").getValue().toString();
                    txt_city = dataSnapshot.child("Address").child("City").getValue().toString();
                    txt_town = dataSnapshot.child("Address").child("Town").getValue().toString();
                    txt_street = dataSnapshot.child("Address").child("Street").getValue().toString();
                    txt_house = dataSnapshot.child("Address").child("House").getValue().toString();
                    String txt_address = txt_town + ", " + txt_city + ", " + txt_house;

                    AddressSingleton addressSingleton = AddressSingleton.get_Instance();
                    addressSingleton.setmCity(txt_city);
                    addressSingleton.setmTown(txt_town);
                    addressSingleton.setmStreet(txt_street);
                    addressSingleton.setmHouse(txt_house);
                    addressSingleton.setmAddress(txt_address);

                    mAddress=txt_address;

                    name.setAllCaps(true);
                    name.setText(txt_name);
                    phone.setText(number);
                    city.setText(txt_city);
                    town.setText(txt_town);
                    house_num.setText(txt_house);
                    street.setText(txt_street);
                    address.setText(txt_address);
                }
                catch (Exception a)
                {
                    Toast.makeText(getContext() , a.toString(), Toast.LENGTH_LONG).show();
                }
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
        city = view.findViewById(R.id.txt_city);
        town = view.findViewById(R.id.txt_town);
        street=view.findViewById(R.id.street);
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