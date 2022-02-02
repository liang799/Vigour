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
import com.sp.vigour.adapters.StepsAdapter;
import com.sp.vigour.adapters.TransAdapter;

import java.util.ArrayList;


public class Transactions extends Fragment {
    private RecyclerView recyclerView;
    private Addhelper helper = null;
    private Cursor model = null;
    private TransAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Addhelper(getContext());
        model = helper.getdata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transactions, container, false);
        adapter = new TransAdapter(getActivity(), model, helper);
        recyclerView = (RecyclerView) v.findViewById(R.id.historyview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }
}