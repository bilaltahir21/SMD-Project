package com.tiffin.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentOrder extends Fragment {

    private DatabaseReference myDatabaseReference;
    private DatabaseReference UserDatabaseReference;

    private OrderList_Adaptor myAdaptor;

    private static ArrayList<Order_Data> Order_Array = new ArrayList<>();

    private RecyclerView recyclerView;

    private ArrayList<OrderDish> Dishes = new ArrayList<>();


    String Name;
    private GenericTypeIndicator<ArrayList<OrderDish>> genericTypeIndicator
            = new GenericTypeIndicator<ArrayList<OrderDish>>() {
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order,null);

        recyclerView = view.findViewById(R.id.order_recyclerView);

//        FetchData();
        Populate_Date();
        return view;
    }


    private void settingTheRecyclerView(ArrayList<Order_Data> Arr) {

        myAdaptor = new OrderList_Adaptor(Arr, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdaptor);
    }



    private void Populate_Date(){
        myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Order");


        myDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               String key =  dataSnapshot.getKey();

                myDatabaseReference.child(Objects.requireNonNull(key)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String Status = Objects.requireNonNull(dataSnapshot.child("Status").getValue()).toString();
//                        Toast.makeText(getContext(), Status, Toast.LENGTH_LONG).show();

                        if (!(Status.equals("Delivered") || Status.equals("Cancelled"))) {

                            String Address = Objects.requireNonNull(dataSnapshot.child("Address").getValue()).toString();

                            Dishes = dataSnapshot.child("cartItems").getValue(genericTypeIndicator);

                            final String userPhone = Objects.requireNonNull(dataSnapshot.child("User").getValue()).toString();
                            UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

                            UserDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot UserDataSnapshot) {
                                    String  F_Name , L_Name ;
                                    F_Name = Objects.requireNonNull(UserDataSnapshot.child(userPhone).child("First Name").getValue()).toString();
                                    L_Name = Objects.requireNonNull(UserDataSnapshot.child(userPhone).child("Last Name").getValue()).toString();
                                    Name = F_Name + " " + L_Name;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            Order_Data temp = new Order_Data(Status , Address , Dishes , Name, userPhone);
                            Order_Array.add(temp);

                            settingTheRecyclerView(Order_Array);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void FetchData(){

        try{
            myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Order");

            myDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Order_Array.clear();

                    for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {

                        String key = uniqueKeySnapshot.getKey();

                        myDatabaseReference.child(Objects.requireNonNull(key)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String Status = Objects.requireNonNull(dataSnapshot.child("Status").getValue()).toString();
                                if (!(Status.equals("Delivered") || Status.equals("Cancelled"))) {

                                    String Address = Objects.requireNonNull(dataSnapshot.child("Address").getValue()).toString();

                                    Dishes = dataSnapshot.child("cartItems").getValue(genericTypeIndicator);

                                    final String userPhone = Objects.requireNonNull(dataSnapshot.child("User").getValue()).toString();
                                    UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

                                    UserDatabaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot UserDataSnapshot) {
                                            String  F_Name , L_Name ;
                                            F_Name = Objects.requireNonNull(UserDataSnapshot.child(userPhone).child("First Name").getValue()).toString();
                                            L_Name = Objects.requireNonNull(UserDataSnapshot.child(userPhone).child("Last Name").getValue()).toString();
                                            Name = F_Name + " " + L_Name;
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    Order_Data temp = new Order_Data(Status , Address , Dishes , Name, userPhone);
                                    Order_Array.add(temp);

                                    settingTheRecyclerView(Order_Array);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception A){

        }
    }
}
