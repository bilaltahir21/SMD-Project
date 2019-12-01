package com.tiffin.manager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentMenu extends Fragment {


    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu, null);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Menu");
        mDatabase.keepSynced(true);

        Context context = getActivity();

        recyclerView = view.findViewById(R.id.menu_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        fetch();


        return view;
    }

    private void fetch() {

        Query query = FirebaseDatabase.getInstance().getReference().child("Menu");
        FirebaseRecyclerOptions<DataListOfMenu> options = new FirebaseRecyclerOptions.Builder<DataListOfMenu>()
                .setQuery(query, new SnapshotParser<DataListOfMenu>() {
                    @NonNull
                    @Override
                    public DataListOfMenu parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new DataListOfMenu(
                                Objects.requireNonNull(snapshot.child("URI").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("cardview1").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("cardview2").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("menu_title").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("menu_description").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("price").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("price_description").getValue()).toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<DataListOfMenu, MenuViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, final int i, @NonNull DataListOfMenu dataListOfMenu) {
                try {
                    Picasso.get().load(dataListOfMenu.getURI()).into(menuViewHolder.image);
                    String discount = "Flat " + dataListOfMenu.getCardview1_text() + "%";
                    menuViewHolder.cardview1.setText(discount);
                    menuViewHolder.cardview2.setText(dataListOfMenu.getCardview2_text());
                    menuViewHolder.menu_title.setText(dataListOfMenu.getMenu_title());
                    menuViewHolder.menu_description.setText(dataListOfMenu.getMenu_description());
                    menuViewHolder.price.setText(dataListOfMenu.getPrice());
                    menuViewHolder.price_description.setText(dataListOfMenu.getPrice_description());


                } catch (Exception Ex) {
                    Toast.makeText(getContext(), "OnBindViewHolder  " + Ex.toString(), Toast.LENGTH_LONG).show();
                }

            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
                return new MenuViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    
    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView cardview1, cardview2, menu_title, menu_description, price, price_description;
        Button addToCart;

        public MenuViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_menu);
            cardview1 = itemView.findViewById(R.id.txt_banner1);
            cardview2 = itemView.findViewById(R.id.txt_banner2);
            menu_title = itemView.findViewById(R.id.txt_Title);
            menu_description = itemView.findViewById(R.id.txt_SEO);
            price = itemView.findViewById(R.id.txt_amount);
            price_description = itemView.findViewById(R.id.txt_message);
            addToCart = itemView.findViewById(R.id.btn_addToCart);

        }

    }
}
