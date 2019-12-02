package com.fast.tiffan_project;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.String.valueOf;

public class historyAdaptor extends RecyclerView.Adapter<historyAdaptor.MyViewHolder> {
    Context context;
    private ArrayList<DataListOfHistory> Array;

    private FragmentManager Manager;

    historyAdaptor(ArrayList<DataListOfHistory> Array, Context context , FragmentManager Manager) {
        this.context = context;
        this.Array = Array;
        this.Manager = Manager;
    }

    @NotNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // set the data in items
        final DataListOfHistory value = Array.get(position);

        holder.status.setText(value.getStatus());
        holder.bill.setText(valueOf(value.getBill()));
        holder.address.setText(value.getAddress());
//      holder.date.setText(c.getDate());
//      implement setOnClickListener event on item view.

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<DataListForCart> temp = value.getHistory_CartItems();
//                DisplayDetails dialog = new DisplayDetails(temp);
                final Dialog dialog = new Dialog(context);

                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.bill_details);
//
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView_bill);
                AdapterForBill myAdapter = new AdapterForBill(temp, context);

                recyclerView.setAdapter(myAdapter);

                recyclerView.setLayoutManager(new LinearLayoutManager(context));


                dialog.show();


                Button dismiss = dialog.findViewById(R.id.btn_dismiss);
                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.hide();
                    }
                });
//                try {
////                    dialog.show(Manager , "Dialog Box");
//                }
//                catch (Exception A){
//                    Toast.makeText(context , A.toString() , Toast.LENGTH_LONG).show();
//                }



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