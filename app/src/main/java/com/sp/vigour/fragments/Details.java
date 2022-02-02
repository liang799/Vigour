package com.sp.vigour.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sp.vigour.R;

public class Details extends Fragment {
    private Bundle bundle;
    private TextView amount;
    private TextView date;
    private TextView description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_steps_details, container, false);
        amount = v.findViewById(R.id.vgr_amt);
        date = v.findViewById(R.id.date_det);
        description = v.findViewById(R.id.desc);
        String amtStr = bundle.getString("amount");
        String dateStr = bundle.getString("date");
        String stepsStr = "For walking " + bundle.getString("steps") + " steps";

        amount.setText(amtStr);
        date.setText(dateStr);
        description.setText(stepsStr);

        return v;
    }
}