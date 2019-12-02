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
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.fast.tiffan_project.MainActivity.SharePrefernce;

public class fragment_cart extends Fragment {

    Context context;
    private RecyclerView recyclerView;
    private AdapterForCart adapter;
    private ArrayList<DataListForCart> Array_Cart;
    private CartItems MyCart = CartItems.get_Instance();
    private DatabaseReference myDatabaseReference;
    private Button proceed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
        try {
            setVariables(view);
            settingTheRecyclerView();
            myDatabaseReference = FirebaseDatabase.getInstance().getReference();
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();
//            fetchFromDatabase();
        } catch (Exception e) {
            //Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressSingleton addr=AddressSingleton.get_Instance();
                if (!addr.getmCity().equals("City") && !addr.getmTown().equals("Town") && !addr.getmStreet().equals("Street") && !addr.getmHouse().equals("House")) {
                    if (MyCart.getSize() != 0) {
                        AddressConfirmation addressConfirmation = new AddressConfirmation();
                        addressConfirmation.show(Objects.requireNonNull(getFragmentManager()), "Address Confirmation");
                        AddressSingleton addressSingleton;
                        addressSingleton = AddressSingleton.get_Instance();
                        if ((addressSingleton.getStatus() == "NOT CHANGED" || addressSingleton.getStatus() == "CHANGED") && addressSingleton.getStatus()!=null) {
                            placeOrderFireBase();
                        }
                    } else {
                        Toast toast = Toast.makeText(getActivity(), "Your Cart is Empty!", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Set address first!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    ViewPager viewPager = getActivity().findViewById(R.id.fragment_container);
                    viewPager.setCurrentItem(2);
                }
            }
        });
        return view;
    }


    private void placeOrderFireBase() {
        SharedPreferences prefs = context.getSharedPreferences(SharePrefernce, MODE_PRIVATE);
        String phone = prefs.getString("phone", "notsaved");//"No name defined" is the default value.
        if (!phone.equals("notsaved")) {
            myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Order").push();
            myDatabaseReference.setValue(MyCart);
            myDatabaseReference.child("Status").setValue("Pending");
            AddressSingleton addressSingleton = AddressSingleton.get_Instance();
            addressSingleton.setStatus(null);
            String address = addressSingleton.getmAddress();
            myDatabaseReference.child("Address").setValue(address);




            //////// JUST FOR NOW BUT THIS CODE WILL BE REMOVE AFTER SETTING DATABASE (REMOVING ORDERS FROM USERS ///////

            myDatabaseReference = FirebaseDatabase.getInstance().getReference("Order").push();
            myDatabaseReference.setValue(MyCart);
            myDatabaseReference.child("Status").setValue("Pending");
            myDatabaseReference.child("Address").setValue(address);
            myDatabaseReference.child("User").setValue(phone);


            //////////////////////////////////////////////////////////////////////////////////////////////////////////////



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
        //Toast.makeText(context, rootRef.getKey(), Toast.LENGTH_SHORT).show();
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