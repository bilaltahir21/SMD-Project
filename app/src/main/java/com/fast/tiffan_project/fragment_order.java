package com.fast.tiffan_project;

import android.app.AlertDialog;
import android.content.Context;
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


import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


public class fragment_order extends Fragment{

            private RecyclerView recyclerView;
            private FirebaseRecyclerAdapter adapter;
            private Context context;
            private ArrayList<DataListOfHistory> array;
            public historyAdaptor ha;


            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_order , null);

                recyclerView = view.findViewById(R.id.menu_recyclerView);
//                recyclerView.setHasFixedSize(true);
                this.context=getContext();
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                // set a LinearLayoutManager with default vertical orientation
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                recyclerView.setLayoutManager(linearLayoutManager);
                array=new ArrayList<>();
//                loadhistory();
//                this.ha=new historyAdaptor(array,context);
                return view;
            }
            public void loadhistory()
            {
                DataListOfHistory d1=new DataListOfHistory("Preparing","650","Johar town block A","Nov 05, 2019");
                DataListOfHistory d2=new DataListOfHistory("Preparing","650","Johar town block A","Nov 05, 2019");
                DataListOfHistory d3=new DataListOfHistory("Preparing","650","Johar town block A","Nov 05, 2019");
                DataListOfHistory d4=new DataListOfHistory("Preparing","650","Johar town block A","Nov 05, 2019");
                array.add(d1);
                array.add(d2);
                array.add(d3);
                array.add(d4);
            }

            @Override
            public void onStop() {
                super.onStop();
//                adapter.stopListening();

            }

            @Override
            public void onStart() {
                super.onStart();
//                adapter.startListening();
            }

}