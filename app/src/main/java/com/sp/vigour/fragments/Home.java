package com.sp.vigour.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sp.vigour.Addhelper;
import com.sp.vigour.R;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Random;

public class Home extends Fragment implements View.OnClickListener {
    private Button detailsButt;
    private TextView tips;
    private TextView Vgr_Amount;
    private TextView hide_indicator;
    private TextView toggle_hide;
    private boolean hidden = false;
    private ArrayList<String> did_u_know;
    private Handler mainHandler =  new Handler();
    private SensorManager sensorManager;
    private Sensor pedometer;
    private TextView insight_step;
    private TextView insight_coin;
    private Addhelper db;
    private String amount;
    Web3j web3;
    Credentials credentials;
    private TextView txtbalance;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        pedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        db = new Addhelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tips = view.findViewById(R.id.tips);
        detailsButt = view.findViewById(R.id.details_button);
        detailsButt.setOnClickListener(this);
        Vgr_Amount = view.findViewById(R.id.balance_home);
        hide_indicator = view.findViewById(R.id.hideEye);
        toggle_hide = view.findViewById(R.id.visibilitySwitch);
        toggle_hide.setOnClickListener(this);
        insight_step = view.findViewById(R.id.insight_step);
        insight_coin = view.findViewById(R.id.insight_coin);

        if (pedometer != null){
            // Success! There's a pedometer.
            did_u_know = new ArrayList<>();
            new fetchData().start();
        } else {
            // Failure! No pedometer.
            tips.setText("Error! Your hardware does not have a Pedometer.");
            tips.setTextColor(Color.parseColor("#EF4B39"));
            tips.setTypeface(tips.getTypeface(), Typeface.BOLD);
        }

            //insight_coin.setText(db.coinInsight());
            //insight_step.setText(db.stepsInsight());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//enter your own infura api key below
        web3 = Web3j.build(new HttpService("https://kovan.infura.io/v3/6046790414614511b931ac6ef17f28b7"));

        setupBouncyCastle();
        retrieveBalance(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.visibilitySwitch:
                if (hidden == false) {
                    hide_indicator.setVisibility(View.VISIBLE);
                    Vgr_Amount.setText("***");
                    hidden = true;
                } else {
                    hide_indicator.setVisibility(View.GONE);
                    Vgr_Amount.setText(amount);
                    hidden = false;
                }
                break;
            case R.id.details_button:
                Navigation.findNavController(v).navigate(R.id.action_home2_to_transactions);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    class fetchData extends Thread {    // to allow stuff to be run simultaneously
        String data = "";

        @Override
        public void run(){
            try {
                URL url = new URL("https://api.npoint.io/cbb709d068583b916068");
//                URL url = new URL("https://opensheet.elk.sh/1y9yJlj3czkw9BDVR3d6BZXW_cOzpMNSxHkHlESlK4D4/1");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    data += line;
                }

                if (!data.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray facts_array = jsonObject.getJSONArray("Did you know?");
                    for (int i = 0; i < facts_array.length(); i++) {
                        JSONObject facts = facts_array.getJSONObject(i);
                        String fact = facts.getString("Did you know?");
                        did_u_know.add(fact);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mainHandler.post( new Runnable() {
                @Override
                public void run() {
                    if (isNetworkAvailable()) {
                        int random = new Random().nextInt(did_u_know.size());
                        tips.setText(did_u_know.get(random));
                    }
                }
            });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
            amount = String.valueOf(b);
            Vgr_Amount.setText(amount);
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