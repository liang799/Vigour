package com.sp.vigour.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sp.vigour.Addhelper;
import com.sp.vigour.R;
import com.sp.vigour.Vigouritem;
import com.sp.vigour.adapters.CustomadapterSteps;

import java.util.ArrayList;


public class Transactions extends Fragment {

    ArrayList<Vigouritem> vigouritemArrayList;
    ArrayList<String> historyID;
    Addhelper db;

    CustomadapterSteps customadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transactions, container, false);

        RecyclerView historyview = v.findViewById(R.id.historyview);

        db = new Addhelper(getContext());

        historyID = new ArrayList<>();
        vigouritemArrayList = new ArrayList<>();

        storeDatainArray();


        customadapter = new CustomadapterSteps(getContext(),historyID, vigouritemArrayList);
        historyview.setAdapter(customadapter);
        historyview.setLayoutManager(new LinearLayoutManager(getContext()));


        return v;
    }

    private void storeDatainArray() {

        db.close();
        Cursor cursor = db.getdata();

        historyID.clear();


        vigouritemArrayList.clear();

        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){

                historyID.add(cursor.getString(0));

                String usersteps = (cursor.getString(1));
                String userdate = (cursor.getString(2));
                String usertime = (cursor.getString(3));
                String usercrypto = (cursor.getString(4));


                vigouritemArrayList.add(new Vigouritem( usersteps, userdate, usertime, usercrypto));

            }
            db.close();
        }
    }
}