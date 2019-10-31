package com.fast.tiffan_project;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class fragment_menu extends Fragment{

    Context context;
    RecyclerView recyclerView;
    AdapterForMenu adapter;
    ArrayList<DataListOfMenu> Array_Menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View View= inflater.inflate(R.layout.fragment_menu , null);
        setVariables(View);
        settingTheRecyclerView();


        DataListOfMenu menu1=new DataListOfMenu();
        menu1.cardview1_text="card1";
        menu1.cardview2_text="card2";
        menu1.menu_description="description";
        menu1.menu_title="title";
        menu1.price="price";
        menu1.price_description="price description";
        menu1.URI="https://firebasestorage.googleapis.com/v0/b/mr-tiffin-9c49d.appspot.com/o/Github.PNG?alt=media&token=3def416b-850c-4e5e-aba7-24d698996c97";
        Array_Menu.add(menu1);
        DataListOfMenu menu2=new DataListOfMenu();
        menu2.cardview1_text="card1";
        menu2.cardview2_text="card2";
        menu2.menu_description="description";
        menu2.menu_title="title";
        menu2.price="price";
        menu2.price_description="price description";
        menu2.URI="https://firebasestorage.googleapis.com/v0/b/mr-tiffin-9c49d.appspot.com/o/download.jpg?alt=media&token=bba75fcb-aab5-4833-b450-8abb0eb680b2";
        Array_Menu.add(menu2);


        return View;
    }

    private void setVariables(View view) {
        context = getActivity();
        Array_Menu = new ArrayList<DataListOfMenu>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_menu);
    }
    private void settingTheRecyclerView()
    {
        adapter = new AdapterForMenu(Array_Menu,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

       // recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }


}
