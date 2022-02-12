package com.sp.vigour;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

public class LogoutDialogFrag extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you wish to logout of Vigour?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity().getApplicationContext(),"Logout Successful", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getActivity(), R.id.fragment_container).navigate(R.id.action_logout_to_loginActivity);
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
