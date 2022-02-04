package com.sp.vigour.fragments;

import static androidx.navigation.Navigation.findNavController;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp.vigour.Addhelper;
import com.sp.vigour.R;
import com.sp.vigour.adapters.TransAdapter;


public class Transactions extends Fragment {
    private RecyclerView recyclerView;
    private Addhelper helper = null;
    private Cursor model = null;
    private TransAdapter adapter = null;
    private TextView balance;
    private ImageView circle;

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
        /* ---- Balance stuff ---- */
        circle = v.findViewById(R.id.circle);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(v).navigate(R.id.action_transactions_to_settings);
            }
        });
        balance = v.findViewById(R.id.userCountry);
        model.moveToLast();
        balance.setText(helper.getCoin(model));

        /* ---- RecyclerView stuff ---- */
        adapter = new TransAdapter(getActivity(), model, helper);
        recyclerView = (RecyclerView) v.findViewById(R.id.historyview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();

        return v;
    }
}