package com.sp.vigour.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sp.vigour.R;
import com.sp.vigour.activities.AboutWebview;

import jnr.ffi.annotations.In;

public class About extends Fragment {
    private BottomNavigationView navBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about,container,false);
        navBar = getActivity().findViewById(R.id.bottomNavigationView);

        Button tianpokwebsite = (Button) v.findViewById(R.id.tianpokwebsite);
        Button fazithwebsite = (Button) v.findViewById(R.id.fazithwebsite);
        Button rajawebsite = (Button) v.findViewById(R.id.rajawebsite);

        tianpokwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gotoUrl("https://github.com/liang799");

                Intent intent = new Intent(getActivity(), AboutWebview.class);
                intent.putExtra("userurl", "https://github.com/liang799");
                startActivity(intent);
            }
        });

        fazithwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gotoUrl("https://zithinc.com/");

                Intent intent = new Intent(getActivity(), AboutWebview.class);
                intent.putExtra("userurl", "https://zithinc.com/");
                startActivity(intent);
            }
        });

        rajawebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gotoUrl("https://www.linkedin.com/in/krajaselvam/");

                Intent intent = new Intent(getActivity(), AboutWebview.class);
                intent.putExtra("userurl", "https://www.linkedin.com/in/krajaselvam/");
                startActivity(intent);
            }
        });


        return v;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
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

