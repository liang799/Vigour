package com.sp.vigour;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.logging.Logger;

public class StepsHistory extends Fragment implements View.OnClickListener {
    public StepsHistory() {}

    private BottomNavigationView navBar;
    private RecyclerView recyclerView;
    private TextView steps_text;
    private ImageButton close;

    // Testing data
    int steps[] = {9000, 8000, 1000, 200, 500, 0};
    String time[] = {"8:35pm", "Yesterday", "January, 10", "January, 09", "January, 08", "January, 07"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_steps_history, container, false);
        navBar = getActivity().findViewById(R.id.bottomNavigationView);
        recyclerView = (RecyclerView) v.findViewById(R.id.steps_history);
        steps_text = (TextView) v.findViewById(R.id.steps_hist_main);
        close = (ImageButton) v.findViewById(R.id.close_sHist);
        close.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        navBar.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        navBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_sHist:
                Navigation.findNavController(v).navigate(R.id.action_stepsHistory_to_steps);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}