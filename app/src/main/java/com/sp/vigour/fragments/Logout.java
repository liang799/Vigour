package com.sp.vigour.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sp.vigour.LogoutDialogFrag;
import com.sp.vigour.R;

public class Logout extends Fragment {
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private BottomNavigationView navBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* -------- Firebase stuff -------- */
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        googleSignInClient = GoogleSignIn.getClient(getActivity()
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        /* -------- Logout Dialog -------- */
        LogoutDialogFrag logoutDialogFrag = new LogoutDialogFrag(googleSignInClient, firebaseAuth);
        logoutDialogFrag.show(getChildFragmentManager(), "Logout of Vigour");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logout, container, false);
        navBar = getActivity().findViewById(R.id.bottomNavigationView);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        /* -------- Hide BottomNav & Toolbar when entering this Fragment -------- */
        navBar.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        /* -------- Show BottomNav & Toolbar when exiting this Fragment -------- */
        navBar.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}