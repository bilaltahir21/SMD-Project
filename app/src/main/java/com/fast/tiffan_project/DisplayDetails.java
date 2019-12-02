package com.fast.tiffan_project;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DisplayDetails extends DialogFragment {


    private RecyclerView recyclerView;
    private ArrayList<DataListForCart> Array;


    DisplayDetails(ArrayList<DataListForCart> Array) {
        this.Array = Array;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_details, container, false);
//        details = view.findViewById(R.id.calculations);
        //Code for details goes in the following area

        Objects.requireNonNull(getDialog()).setCanceledOnTouchOutside(false);

        recyclerView = view.findViewById(R.id.recyclerView_bill);


        settingTheRecyclerView();

        Button bDismissAction = view.findViewById(R.id.btn_dismiss);
        bDismissAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        return view;
    }


    private void settingTheRecyclerView() {
        AdapterForBill myAdapter = new AdapterForBill(this.Array, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
    }

}