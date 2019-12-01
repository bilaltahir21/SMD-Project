package com.fast.tiffan_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddressConfirmation extends DialogFragment {
    private TextView address;
    private Button btnChange, btnProceed;
    private DatabaseReference myDatabaseReference;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_confirmation, container, false);
        address = view.findViewById(R.id.address);

        myDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        btnChange = view.findViewById(R.id.change);
        btnProceed = view.findViewById(R.id.nah);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemporaryAddress temporaryAddress = new TemporaryAddress();
                temporaryAddress.show(getFragmentManager(), "PopUp");
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressSingleton addressSingleton = AddressSingleton.get_Instance();
                addressSingleton.setStatus("NOT CHANGED");
                //Leaving
                getDialog().dismiss();
            }
        });

        return view;
    }


}
