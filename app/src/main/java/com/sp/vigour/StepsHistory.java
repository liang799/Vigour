package com.sp.vigour;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.function.ToLongBiFunction;

public class StepsHistory extends Fragment {
    public StepsHistory() {}

    private RecyclerView recyclerView;
    private TextView steps_text;

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
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) v.findViewById(R.id.steps_history);
        steps_text = (TextView) v.findViewById(R.id.steps_hist_main);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}