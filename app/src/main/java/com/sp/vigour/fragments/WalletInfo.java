package com.sp.vigour.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sp.vigour.R;

public class WalletInfo extends Fragment {
    private BottomNavigationView navBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_walletinfo,container,false);
        navBar = getActivity().findViewById(R.id.bottomNavigationView);
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

