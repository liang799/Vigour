package com.sp.vigour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StepsHistory extends Fragment { //implements View.OnClickListener {
    private BottomNavigationView navBar;
    private RecyclerView recyclerView;

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
        StepsAdapter adapter = new StepsAdapter(getActivity(), steps, time);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        navBar.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        navBar.setVisibility(View.VISIBLE);
    }
}