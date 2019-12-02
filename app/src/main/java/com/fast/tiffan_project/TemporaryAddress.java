package com.fast.tiffan_project;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class TemporaryAddress extends DialogFragment {
    EditText town, city, street, house;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message, container, false);
        town = view.findViewById(R.id.town);
        city = view.findViewById(R.id.city);
        street = view.findViewById(R.id.street);
        house = view.findViewById(R.id.house);

        Button bSaveAction = view.findViewById(R.id.actionSave);
        bSaveAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save if fields are not empty
                if (TextUtils.isEmpty(town.getText())) {
                    town.setError("Town is required!");
                } else if (TextUtils.isEmpty(city.getText())) {
                    city.setError("City is required!");
                } else if (TextUtils.isEmpty(street.getText())) {
                    street.setError("Street is required!");
                } else if (TextUtils.isEmpty(house.getText())) {
                    house.setError("House is required!");
                } else {
                    AddressSingleton addressSingleton = AddressSingleton.get_Instance();
                    addressSingleton.setmCity(city.getText().toString());
                    addressSingleton.setmTown(town.getText().toString());
                    addressSingleton.setmStreet(street.getText().toString());
                    addressSingleton.setmHouse(house.getText().toString());
                    addressSingleton.setmAddress(city.getText().toString() + ", " + town.getText().toString() + ", " + street.getText().toString() + ", " + house.getText().toString());
                    addressSingleton.setStatus("CHANGED");
                    //Leaving
                    Objects.requireNonNull(getDialog()).dismiss();
                }
            }
        });

        Button bCancelAction = view.findViewById(R.id.actionCancel);
        bCancelAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return view;
    }
}
