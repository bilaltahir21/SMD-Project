package com.fast.tiffan_project;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
                AddressSingleton addr = AddressSingleton.get_Instance();
                if (!addr.getmCity().equals("City") && !addr.getmTown().equals("Town") && !addr.getmStreet().equals("Street") && !addr.getmHouse().equals("House")) {
                    if (MyCart.getSize() != 0) {
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.address_confirmation);
                        dialog.show();
                        Button button = dialog.findViewById(R.id.nah);
                        button.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          placeOrderFireBase();
                                                          dialog.hide();
                                                      }
                                                  }
                        );
                        Button button1 = dialog.findViewById(R.id.change);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog change=new Dialog(getActivity());
                                change.setContentView(R.layout.message);
                                dialog.hide();
                                change.show();
                                final EditText editCity=change.findViewById(R.id.city);
                                final EditText editTown=change.findViewById(R.id.town);
                                final EditText editStreet=change.findViewById(R.id.street);
                                final EditText editHouse=change.findViewById(R.id.house);

                                Button actionSave=change.findViewById(R.id.actionSave);
                                actionSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AddressSingleton addressSingleton;
                                        addressSingleton=AddressSingleton.get_Instance();
                                        addressSingleton.setmCity(editCity.getText().toString());
                                        addressSingleton.setmTown(editTown.getText().toString());
                                        addressSingleton.setmStreet(editStreet.getText().toString());
                                        addressSingleton.setmHouse(editHouse.getText().toString());
                                        addressSingleton.setmAddress(editCity.getText().toString()+", "+editTown.getText().toString()+", "+editStreet.getText().toString()+", "+editHouse.getText().toString());

                                        placeOrderFireBase();
                                        change.hide();
                                    }
                                });

                                Button actionCancel=change.findViewById(R.id.actionCancel);
                                actionCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        change.hide();
                                    }
                                });
                            }
                        });
//                        AddressConfirmation addressConfirmation = new AddressConfirmation();
//                        addressConfirmation.show(Objects.requireNonNull(getFragmentManager()), "Address Confirmation");
                        AddressSingleton addressSingleton;
                        addressSingleton = AddressSingleton.get_Instance();
                        if (addressSingleton.getStatus() != null) {
                            if (addressSingleton.getStatus() == "NOT CHANGED" || addressSingleton.getStatus() == "CHANGED") {
                                //placeOrderFireBase();
                            }
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


    public void placeOrderFireBase() {
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
        AddressSingleton addressSingleton;
        addressSingleton = AddressSingleton.get_Instance();
        addressSingleton.setStatus(null);
    }

}