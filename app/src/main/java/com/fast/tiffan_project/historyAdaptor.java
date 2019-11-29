package com.fast.tiffan_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class historyAdaptor extends RecyclerView.Adapter<historyAdaptor.MyViewHolder> {
    private ArrayList<DataListOfHistory> Array;
    Context context;

    historyAdaptor(ArrayList<DataListOfHistory> Array, Context context) {
        this.context = context;
        this.Array = Array;
    }

    @NotNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.history, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // set the data in items
        final DataListOfHistory value = Array.get(position);

        holder.status.setText(value.getStatus());
        holder.bill.setText(valueOf(value.getBill()));
        holder.address.setText(value.getAddress());
//        holder.date.setText(c.getDate());

        // implement setOnClickListener event on item view.

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
//                callnext(position);
            }
        });
    }

    String convert(int d) {
        return ("Progress : " + d + "%");
    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    private void callnext(int p) {
        //Toast.makeText(this.context, "Not Implemented yet ", Toast.LENGTH_SHORT).show();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView status, bill, address;// init the item view's
        Button details;


        MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            status = itemView.findViewById(R.id.txt_status);
            bill = itemView.findViewById(R.id.txt_bill);
            address = itemView.findViewById(R.id.txt_HistoryAddress);
//            date = itemView.findViewById(R.id.date);
            details = itemView.findViewById(R.id.btn_details);
        }
    }

}