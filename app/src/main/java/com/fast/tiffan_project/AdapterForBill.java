package com.fast.tiffan_project;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterForBill extends RecyclerView.Adapter<AdapterForBill.MyViewHolder> {

    private ArrayList<DataListForCart> Array;
    Context context;

    AdapterForBill (ArrayList<DataListForCart> Array, Context context) {
        this.Array = Array;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bill_view, parent, false);
        return new AdapterForBill.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DataListForCart Value = Array.get(position);

        holder.Name.setText(Value.getItemName());
        String Qty = Integer.toString(Value.getQuantity());
        holder.Quantity.setText(Qty);
        holder.Bill.setText(Value.getBill());

    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name, Quantity, Bill;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.txt_DishName);
            Quantity = itemView.findViewById(R.id.txt_BillQty);
            Bill = itemView.findViewById(R.id.txt_Box_bill);
        }
    }
}