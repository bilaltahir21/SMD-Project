package com.fast.tiffan_project;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
import java.util.Objects;
import static android.content.Context.MODE_PRIVATE;

public class historyAdaptor  extends RecyclerView.Adapter<historyAdaptor.MyViewHolder>  {
    ArrayList<DataListOfHistory> historyList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView status,bill,address,date;// init the item view's
        Button details;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            status = (TextView) itemView.findViewById(R.id.status);
            bill = (TextView) itemView.findViewById(R.id.bill);
            address=(TextView) itemView.findViewById(R.id.address);
            date=(TextView) itemView.findViewById(R.id.date);
            details=(Button) itemView.findViewById(R.id.button2);
        }
    }
    public historyAdaptor(ArrayList<DataListOfHistory> hlist,Context context) {
        this.context = context;
        this.historyList = hlist;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // set the data in items
        final DataListOfHistory c=historyList.get(position);
        holder.status.setText(c.getStatus());
        holder.bill.setText(c.getBill());
        holder.address.setText(c.getAddress());
        holder.date.setText(c.getDate());

        // implement setOnClickListener event on item view.
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                callnext(position);
            }
        });
    }
    String convert(int d)
    {
        return("Progress : "+d+"%");
    }
    @Override
    public int getItemCount() {
        return historyList.size();
    }
    public void callnext(int p)
    {
        Toast.makeText(this.context, "Not Implemented yet ", Toast.LENGTH_SHORT).show();
    }

}