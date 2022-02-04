package com.sp.vigour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.sp.vigour.R;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    File file = new File("Vigour/path");
    String Walletname;
    Credentials credentials;
    String password = "pw";
    Button createWallet;
    Button restoreWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createWallet = findViewById(R.id.createwallet);
        restoreWallet = findViewById(R.id.restorewallet);

        createWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWallet();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        restoreWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file.exists())
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                else
                    Toast.makeText(LoginActivity.this, "Please create a wallet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createWallet()  {
        try {
            Walletname = WalletUtils.generateLightNewWalletFile(password, file);
            Toast.makeText(getApplicationContext(), "Wallet generated wallet name is" + Walletname, Toast.LENGTH_SHORT).show();
            credentials = WalletUtils.loadCredentials(password, file + "/" + Walletname);
            Log.d("Wallet text","Wallet generated wallet name is" + Walletname );
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }
}