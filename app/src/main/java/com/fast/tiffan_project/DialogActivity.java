package com.fast.tiffan_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class DialogActivity extends DialogFragment {
    private DatabaseReference myDatabaseReference;

    Button bSaveAction, bCancelAction;
    EditText town, city, street, house;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.message,container,false);
        town=view.findViewById(R.id.town);
        city=view.findViewById(R.id.city);
        street=view.findViewById(R.id.street);
        house=view.findViewById(R.id.house);

        bSaveAction=view.findViewById(R.id.actionSave);
        bSaveAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save if fields are not empty
                if(TextUtils.isEmpty(town.getText())){
                    town.setError("Town is required!");
                }else if (TextUtils.isEmpty(city.getText())){
                    city.setError("City is required!");
                }
                else if (TextUtils.isEmpty(street.getText())){
                    street.setError("Street is required!");
                }else if (TextUtils.isEmpty(house.getText())){
                    house.setError("House is required!");
                }
                else {
                    //Updating data in firebase
                    SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(MainActivity.SharePrefernce, MODE_PRIVATE);
                    String phone = prefs.getString("phone", "notsaved");//"No name defined" is the default value.
                    if(!phone.equals("notsaved")){
                        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Address");
                        myDatabaseReference.child("Town").setValue(town.getText().toString());
                        myDatabaseReference.child("City").setValue(city.getText().toString());
                        myDatabaseReference.child("Street").setValue(street.getText().toString());
                        myDatabaseReference.child("House").setValue(house.getText().toString());
                    }
                    //Leaving
                    getDialog().dismiss();
                }
            }
        });

        bCancelAction=view.findViewById(R.id.actionCancel);
        bCancelAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });



        return view;
    }
}
