package com.fast.tiffan_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class fragment_cart extends Fragment {

    Context context;
    RecyclerView recyclerView;
    AdapterForCart adapter;
    ArrayList<DataListForCart> Array_Cart;
    private CartItems MyCart = CartItems.get_Instance();
    DatabaseReference myDatabaseReference;
    private FirebaseAuth mAuth;
    Button proceed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
        try {
            setVariables(view);
            settingTheRecyclerView();
            myDatabaseReference = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
//            fetchFromDatabase();
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyCart.getSize() != 0) {
                    AddressConfirmation addressConfirmation = new AddressConfirmation();
                    addressConfirmation.show(getFragmentManager(), "Address Confirmation");
                    placeOrderFireBase();
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Your Cart is Empty!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        return view;
    }


    private void placeOrderFireBase() {
        SharedPreferences prefs = context.getSharedPreferences(MainActivity.SharePrefernce, MODE_PRIVATE);
        String phone = prefs.getString("phone", "notsaved");//"No name defined" is the default value.
        if (!phone.equals("notsaved")) {
            myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Order").push();
            myDatabaseReference.setValue(MyCart);
            myDatabaseReference.child("Status").setValue("Pending");

            AddressSingleton addressSingleton = AddressSingleton.get_Instance();
            String address = addressSingleton.getmAddress();
            myDatabaseReference.child("Address").setValue(address);
            MyCart.EmptyCart();
            adapter.notifyDataSetChanged();
        }
    }

    private void fetchFromDatabase() {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Menu");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                //This will loop through all items. Add variables to arrays or lists as required
                String a = dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        });
        Toast.makeText(context, rootRef.getKey(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setVariables(View view) {
        context = getActivity();
//        Array_Cart = new ArrayList<DataListForCart>();
        Array_Cart = MyCart.getCartItems();
        proceed = view.findViewById(R.id.btn_proceed);
        recyclerView = view.findViewById(R.id.recyclerView_cart);
    }

    private void settingTheRecyclerView() {
        adapter = new AdapterForCart(Array_Cart, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

}