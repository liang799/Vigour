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
    String Walletname;
    Credentials credentials;
    String password = "pw";
    Button createWallet;
    Button restoreWallet;
    final String etheriumwalletPath = "/wallet";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createWallet = findViewById(R.id.createwallet);
        restoreWallet = findViewById(R.id.restorewallet);

        file = new File(getFilesDir() + etheriumwalletPath);// the etherium wallet location

        createWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file.exists()) {
                    Toast.makeText(LoginActivity.this, "Please restore your old wallet", Toast.LENGTH_SHORT).show();
                } else {
                    createWallet();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
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
            if (!file.mkdirs() )
                file.mkdirs();
            else
                Log.d("Wallet", "Directory exists");
            Walletname = WalletUtils.generateLightNewWalletFile(password, file);
            Toast.makeText(getApplicationContext(), "Wallet generated wallet name is" + Walletname, Toast.LENGTH_SHORT).show();
            credentials = WalletUtils.loadCredentials(password, file + "/" + Walletname);
            Log.d("Wallet","Wallet generated wallet name is" + Walletname );
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }
}