package com.sp.vigour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sp.vigour.R;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    String Walletname;
    Credentials credentials;
    private String password = "pw";
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
            Toast.makeText(getApplicationContext(), "Wallet generated wallet name is " + Walletname, Toast.LENGTH_SHORT).show();
            credentials = WalletUtils.loadCredentials(password, file + "/" + Walletname);
        } catch(Exception e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }
}