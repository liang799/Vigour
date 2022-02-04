package com.sp.vigour.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.vigour.Addhelper;
import com.sp.vigour.R;
import com.sp.vigour.adapters.StepsAdapter;

public class StepsHistory extends Fragment {
    private RecyclerView recyclerView;
    private Addhelper helper = null;
    private Cursor model = null;
    private StepsAdapter adapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Addhelper(getContext());
        model = helper.getdata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_steps_history, container, false);
        adapter = new StepsAdapter(getActivity(), model, helper);
        recyclerView = (RecyclerView) v.findViewById(R.id.steps_history);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.notifyDataSetChanged();
        return v;
    }
}