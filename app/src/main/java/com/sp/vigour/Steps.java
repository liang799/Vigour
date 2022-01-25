package com.sp.vigour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class Steps extends Fragment implements View.OnClickListener {
    private Button stepsButton;
    private ImageButton eventBtn_1, eventBtn_2, eventBtn_3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_steps, container, false);
        stepsButton = (Button) v.findViewById(R.id.goto_steps_hist);
        stepsButton.setOnClickListener(this);

        eventBtn_1 = (ImageButton) v.findViewById(R.id.event_button_1);
        eventBtn_2 = (ImageButton) v.findViewById(R.id.event_button_2);
        eventBtn_3 = (ImageButton) v.findViewById(R.id.event_button_3);
        eventBtn_1.setOnClickListener(this);
        eventBtn_2.setOnClickListener(this);
        eventBtn_3.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goto_steps_hist:
                Navigation.findNavController(v).navigate(R.id.action_steps_to_stepsHistory);
                break;
            case R.id.event_button_1:
            case R.id.event_button_2:
            case R.id.event_button_3:
                Navigation.findNavController(v).navigate(R.id.action_steps_to_map);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}