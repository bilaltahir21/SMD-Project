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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
    private DatabaseReference myDatabaseReference;


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
                                Objects.requireNonNull(snapshot.child("price_description").getValue()).toString(),
                                Objects.requireNonNull(snapshot.child("show").getValue()).toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<DataListOfMenu, MenuViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull final MenuViewHolder menuViewHolder, final int i, @NonNull DataListOfMenu dataListOfMenu) {
                try {
                    Picasso.get().load(dataListOfMenu.getURI()).into(menuViewHolder.image);
                    String discount = "Flat " + dataListOfMenu.getCardview1_text() + "%";
                    menuViewHolder.cardview1.setText(discount);
                    menuViewHolder.cardview2.setText(dataListOfMenu.getCardview2_text());
                    menuViewHolder.menu_title.setText(dataListOfMenu.getMenu_title());
                    menuViewHolder.menu_description.setText(dataListOfMenu.getMenu_description());
                    menuViewHolder.price.setText(dataListOfMenu.getPrice());
                    menuViewHolder.price_description.setText(dataListOfMenu.getPrice_description());

                    final String ShowItem = "Show";
                    final String HideItem = "Hide";
                    if(!dataListOfMenu.getShow().toUpperCase().equals("YES")){
//                        menuViewHolder.Hide_Show.setBackgroundColor(255);
                        menuViewHolder.Hide_Show.setText(ShowItem);
                    }
                    else menuViewHolder.Hide_Show.setText(HideItem);

                    menuViewHolder.Hide_Show.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Menu");
                            DataListOfMenu temp = (DataListOfMenu) adapter.getItem(i);
                            String now = temp.getShow().toUpperCase();
                            if(now.equals("YES")){
                                Hide_Show("No" , temp);
                                menuViewHolder.Hide_Show.setText(ShowItem);
                                temp.setShow("NO");
                            }
                            else if(now.equals("NO")){
                                Hide_Show("Yes" , temp);
                                menuViewHolder.Hide_Show.setText(HideItem);
                                temp.setShow("YES");
                            }
                        }
                    });


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

    private void Hide_Show(final String DisplayMesage , final DataListOfMenu temp) {
        myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Menu");
        myDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {

                        String key = uniqueKeySnapshot.getKey();
                        String title = Objects.requireNonNull(uniqueKeySnapshot.child("menu_title").getValue()).toString();
                        if(title.equals(temp.getMenu_title())){
                            myDatabaseReference.child(Objects.requireNonNull(key)).child("show").setValue(DisplayMesage);
                            break;
                        }
                    }
                }
                catch (Exception A){
//                    Toast.makeText(getContext() , A.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        Button Hide_Show;

        public MenuViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_menu);
            cardview1 = itemView.findViewById(R.id.txt_banner1);
            cardview2 = itemView.findViewById(R.id.txt_banner2);
            menu_title = itemView.findViewById(R.id.txt_Title);
            menu_description = itemView.findViewById(R.id.txt_SEO);
            price = itemView.findViewById(R.id.txt_amount);
            price_description = itemView.findViewById(R.id.txt_message);
            Hide_Show = itemView.findViewById(R.id.btn_Hide_Show);

        }

    }
}
