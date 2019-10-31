package com.fast.tiffan_project;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterForMenu   extends RecyclerView.Adapter<AdapterForMenu.MyViewHolder>  {
private ArrayList<DataListOfMenu> Array;
        Context context;
public AdapterForMenu(ArrayList<DataListOfMenu> Array, Context context)
        {
        this.Array=Array;
        this.context=context;
        }
@Override
public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.product,parent,false);
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(final MyViewHolder holder, int position) {
    if (Array != null && holder != null) {
        DataListOfMenu Value = Array.get(position);
        // downnload and set image
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        // ImageView in your Activity
        Glide.with(context /* context */)
                .load(Value.getURI())
                .into(holder.image);

        holder.cardview1.setText(Value.getCardview1_text());
        holder.cardview2.setText(Value.getCardview2_text());
        holder.menu_title.setText(Value.getMenu_title());
        holder.menu_description.setText(Value.getMenu_description());
        holder.price.setText(Value.getPrice());
        holder.price_description.setText(Value.getPrice_description());

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });  // button

    }
}

@Override
public int getItemCount() {
        return Array.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView cardview1,cardview2,menu_title,menu_description,price,price_description;
    Button addToCart;


    public MyViewHolder(View itemView) {
        super(itemView);
        image=(ImageView)itemView.findViewById(R.id.image_menu);
        cardview1= (TextView) itemView.findViewById(R.id.txt_banner1);
        cardview2= (TextView) itemView.findViewById(R.id.txt_banner2);
        menu_title= (TextView) itemView.findViewById(R.id.txt_Title);
        menu_description= (TextView) itemView.findViewById(R.id.txt_SEO);
        price= (TextView) itemView.findViewById(R.id.txt_amount);
        price_description= (TextView) itemView.findViewById(R.id.txt_message);
        addToCart=(Button)itemView.findViewById(R.id.btn_addToCart);


    }
}
}
