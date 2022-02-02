package com.sp.vigour.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sp.vigour.R;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;

public class crypto extends Fragment {
    Web3j web3;
    Credentials credentials;
    private TextView txtbalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_transactions, container, false);
        txtbalance=v.findViewById(R.id.userCountry);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//enter your own infura api key below
        web3 = Web3j.build(new HttpService("https://kovan.infura.io/v3/6046790414614511b931ac6ef17f28b7"));

        setupBouncyCastle();
        retrieveBalance(v);

         return v;
    }

    public void retrieveBalance (View v)  {
        //get wallet's balance

        try {

            EthGetBalance balanceWei = web3.ethGetBalance("0x46715A53e5654AAA2258B57AfFB9B5C2daF2612d", DefaultBlockParameterName.LATEST).sendAsync()
                    .get();

            BigInteger a = balanceWei.getBalance();
            int b = a.intValue();
            b = b/100000000;
            b = b + 20; // where changes should be made to liase with steps
            txtbalance.setText(String.valueOf(b));
            //txtbalance.setText(getString(R.string.your_balance) + c);


        }
        catch (Exception e){
            //ShowToast("balance failed");
            Toast.makeText(getActivity(), "balance failed", Toast.LENGTH_SHORT).show();

        }
    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up a provider  when it's used for the first time.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            return;
        }
        //There is a possibility  the bouncy castle registered by android may not have all ciphers
        //so we  substitute with the one bundled in the app.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

}

