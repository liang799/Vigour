package com.sp.vigour;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutDialogFrag extends DialogFragment {
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    LogoutDialogFrag(GoogleSignInClient client, FirebaseAuth fAuth){
        firebaseAuth = fAuth;
        googleSignInClient = client;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you wish to logout of Vigour?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    firebaseAuth.signOut();

                                    Toast.makeText(getActivity().getApplicationContext()
                                            ,"Logout Successful", Toast.LENGTH_SHORT).show();

                                    Navigation.findNavController(getActivity(), R.id.fragment_container).navigate(R.id.action_logout_to_loginActivity);
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Navigation.findNavController(getActivity(), R.id.fragment_container).navigate(R.id.action_logout_to_home2);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
