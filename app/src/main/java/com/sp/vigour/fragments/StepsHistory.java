package com.sp.vigour.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.vigour.Addhelper;
import com.sp.vigour.R;
import com.sp.vigour.Vigouritem;
import com.sp.vigour.adapters.StepsAdapter;

import java.util.ArrayList;

public class StepsHistory extends Fragment {

    Addhelper db;

    private RecyclerView recyclerView;
    private TextView steps_text,steps_date;

    ArrayList<String> historyID;
    ArrayList<Vigouritem> vigouritemArrayList;
    StepsAdapter customadapter;

    // Testing data
    int steps[] = {9000, 8000, 1000, 200, 500, 0};
    String time[] = {"8:35pm", "Yesterday", "January, 10", "January, 09", "January, 08", "January, 07"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Addhelper(getContext());

        historyID = new ArrayList<>();
        vigouritemArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_steps_history, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.steps_history);
        steps_text = (TextView) v.findViewById(R.id.steps_hist_main);
        steps_date = v.findViewById(R.id.steps_hist_date);

        storeDatainArray();

        customadapter = new StepsAdapter(getContext(),historyID, vigouritemArrayList);
        recyclerView.setAdapter(customadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


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