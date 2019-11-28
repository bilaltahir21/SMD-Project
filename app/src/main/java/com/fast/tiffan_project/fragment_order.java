package com.fast.tiffan_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import static android.content.Context.MODE_PRIVATE;


public class fragment_order extends Fragment {

    private DatabaseReference myDatabaseReference;
    private static ArrayList<DataListOfHistory> History_Array = new ArrayList<>();
    private RecyclerView recyclerView;
    //    private FirebaseRecyclerAdapter adapter;
    private Context context;
    private historyAdaptor myAdaptor;

    private GenericTypeIndicator<ArrayList<DataListForCart>> genericTypeIndicator
            = new GenericTypeIndicator<ArrayList<DataListForCart>>() {
    };
    private ArrayList<DataListForCart> History_CartItems = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);

//        History_Array = null;
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(MainActivity.SharePrefernce, MODE_PRIVATE);
        String phone = prefs.getString("phone", "notsaved");//"No name defined" is the default value.

        if (!phone.equals("notsaved")) {
            DisplayOrders(phone);
        }

        recyclerView = view.findViewById(R.id.history_recyclerView);
        //                recyclerView.setHasFixedSize(true);
        this.context = getActivity();
        //                recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // set a LinearLayoutManager with default vertical orientation
        //                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        //                recyclerView.setLayoutManager(linearLayoutManager);
        //                loadhistory();
        //                this.ha=new historyAdaptor(array,context);

        if(History_Array!=null){
//            int Bill = History_Array.get(0).getBill();
//
//            if (Bill < 10) {
//                Toast.makeText(getActivity(), "Small", Toast.LENGTH_LONG).show();
//            }
//            settingTheRecyclerView();
        }
        return view;
    }

    public void loadhistory() {
//                DataListOfHistory d1=new DataListOfHistory("Preparing","650","Johar town block A","Nov 05, 2019" , null);
//                DataListOfHistory d2=new DataListOfHistory("Preparing","650","Johar town block A","Nov 05, 2019" , null);
//                DataListOfHistory d3=new DataListOfHistory("Preparing","650","Johar town block A","Nov 05, 2019" , null);
//                DataListOfHistory d4=new DataListOfHistory("Preparing","650","Johar town block A","Nov 05, 2019" , null);
//                array.add(d1);
//                array.add(d2);
//                array.add(d3);
//                array.add(d4);
    }


    private void DisplayOrders(final String number) {
        
        History_Array.clear();
        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(number).child("Order");

        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final ArrayList<DataListOfHistory> Array = new ArrayList<>();

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

//                                        Array.add(temp);
                                        History_Array.add(temp);


                                        int Bill = History_Array.get(0).getBill();

                                        if (Bill > 10) {
                                            Toast.makeText(getActivity(), "Small", Toast.LENGTH_LONG).show();
                                            settingTheRecyclerView(History_Array);
                                        }

                                        if(History_Array!= null){
                                            for (int i=0; i< History_Array.size(); i++){
                                                String Name = History_Array.get(i).getStatus();
                                                Toast.makeText(getContext() , Name, Toast.LENGTH_LONG).show();

                                            }
                                        }

                                    }
                                } catch (Exception a) {
                                    Toast.makeText(getContext(), a.toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


//                        for(DataSnapshot object : uniqueKeySnapshot.getChildren()){
//                            String key = object.getValue().toString();
//                //                        myDatabaseReference.child(Objects.requireNonNull(key)).child("Address");
//
//                            Toast.makeText(getContext(), key , Toast.LENGTH_LONG).show();
////
////                            String Address = uniqueKeySnapshot.child(key).getValue().toString();
////
////                            String Status = uniqueKeySnapshot.child(key).child("Address").getValue().toString();
//                        }

                    }

                } catch (Exception a) {
                    Toast.makeText(getContext(), a.toString(), Toast.LENGTH_LONG).show();
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