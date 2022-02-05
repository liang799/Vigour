package com.sp.vigour.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sp.vigour.Addhelper;
import com.sp.vigour.R;
import com.sp.vigour.activities.qrcode;

public class Steps extends Fragment implements View.OnClickListener {
    private Button stepsButton;
    private ImageButton eventBtn_1, eventBtn_2, eventBtn_3;
    private TextView steps;
    private Addhelper helper = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Addhelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_steps, container, false);
        steps = v.findViewById(R.id.steps_cum);
        stepsButton = (Button) v.findViewById(R.id.goto_steps_hist);
        stepsButton.setOnClickListener(this);

        eventBtn_1 = (ImageButton) v.findViewById(R.id.event_button_1);
        eventBtn_2 = (ImageButton) v.findViewById(R.id.event_button_2);
        eventBtn_3 = (ImageButton) v.findViewById(R.id.event_button_3);
        eventBtn_1.setOnClickListener(this);
        eventBtn_2.setOnClickListener(this);
        eventBtn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(),qrcode.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //steps.setText(helper.getTodaySteps());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goto_steps_hist:
                Navigation.findNavController(v).navigate(R.id.action_steps_to_stepsHistory);
                break;
            case R.id.event_button_1:
            case R.id.event_button_2:
            //case R.id.event_button_3:
                Navigation.findNavController(v).navigate(R.id.action_steps_to_map);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}