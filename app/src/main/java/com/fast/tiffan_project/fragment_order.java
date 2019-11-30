package com.fast.tiffan_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class fragment_order extends Fragment {

    private DatabaseReference myDatabaseReference;
    private static ArrayList<DataListOfHistory> History_Array = new ArrayList<>();
    private RecyclerView recyclerView;
    //    private FirebaseRecyclerAdapter adapter;
    //    private Context context;
    private historyAdaptor myAdaptor;

    private GenericTypeIndicator<ArrayList<DataListForCart>> genericTypeIndicator
            = new GenericTypeIndicator<ArrayList<DataListForCart>>() {};

    private ArrayList<DataListForCart> History_CartItems = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container,false);

//        History_Array = null;
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(MainActivity.SharePrefernce, MODE_PRIVATE);
        String phone = prefs.getString("phone", "notsaved");//"No name defined" is the default value.

        if (!phone.equals("notsaved")) {
            DisplayOrders(phone);
        }

        recyclerView = view.findViewById(R.id.history_recyclerView);
        //                recyclerView.setHasFixedSize(true);
//        this.context = getActivity();
        //                recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // set a LinearLayoutManager with default vertical orientation
        //                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        //                recyclerView.setLayoutManager(linearLayoutManager);
        //                loadhistory();
        //                this.ha=new historyAdaptor(array,context);
        return view;
    }

    private void DisplayOrders(final String number) {

        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(number).child("Order");

        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                History_Array.clear();
                try {

                    for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {

                        final String key = uniqueKeySnapshot.getKey();

                        myDatabaseReference.child(Objects.requireNonNull(key)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                try {

                                    String Status = Objects.requireNonNull(dataSnapshot.child("Status").getValue()).toString();

                                    if (!(Status.equals("Delivered") || Status.equals("Cancelled"))) {

                                        History_CartItems = dataSnapshot.child("cartItems").getValue(genericTypeIndicator);

                                        String Address = Objects.requireNonNull(dataSnapshot.child("Address").getValue()).toString();
                                        DataListOfHistory temp = new DataListOfHistory(Status, Address, History_CartItems);

                                        History_Array.add(temp);

                                        settingTheRecyclerView(History_Array);
                                    }
                                } catch (Exception a) {
                                    // Toast.makeText(getContext(), a.toString(), Toast.LENGTH_LONG).show();
                                }
//                                settingTheRecyclerView(History_Array);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                } catch (Exception a) {
                    // Toast.makeText(getContext(), a.toString(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        History_Array.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        History_Array.clear();
//      myAdaptor.stopListening();

    }

    @Override
    public void onStart() {
        super.onStart();
//        myAdaptor.startListening();
    }


    private void settingTheRecyclerView(ArrayList<DataListOfHistory> Arr) {

        myAdaptor = new historyAdaptor(Arr, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdaptor);
    }

}