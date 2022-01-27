package com.sp.vigour;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class Logout extends Fragment implements View.OnClickListener {
    Button btLogout;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    private BottomNavigationView navBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        googleSignInClient = GoogleSignIn.getClient(getActivity()
                , GoogleSignInOptions.DEFAULT_SIGN_IN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_logout, container, false);
        btLogout = v.findViewById(R.id.logout_button);
        btLogout.setOnClickListener(this);
        navBar = getActivity().findViewById(R.id.bottomNavigationView);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_logout:
                signOut(view);
                break;
        }
    }


    private void signOut(View v) {
        googleSignInClient.signOut()
                .addOnCompleteListener((Executor) this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseAuth.getInstance().signOut();
                        Navigation.findNavController(v).navigate(R.id.action_logout_to_loginActivity);
                    }
                });
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