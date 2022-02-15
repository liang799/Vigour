package com.sp.vigour.workers;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.database.Cursor;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.sp.vigour.Addhelper;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CryptoWorker extends Worker {
    Web3j web3;
    Addhelper helper;
    SimpleDateFormat simpleDateFormat;
    Addhelper db;

    public CryptoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        /* ---- SQL stuff ---- */
        helper = new Addhelper(getApplicationContext());
        simpleDateFormat = new SimpleDateFormat("dd LLL");

        /* ---- crypto stuff ---- */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        web3 = Web3j.build(new HttpService("https://kovan.infura.io/v3/6046790414614511b931ac6ef17f28b7")); //infura api key
        setupBouncyCastle();
        retrieveBalance();
        Log.d("trans1", "showing balance ");
        return Result.retry();
    }

    public void retrieveBalance()  {
        //get wallet's balance
        try {
            EthGetBalance balanceWei = web3.ethGetBalance("0x46715A53e5654AAA2258B57AfFB9B5C2daF2612d", DefaultBlockParameterName.LATEST).sendAsync()
                    .get();
            BigInteger bigI = balanceWei.getBalance();
            int storeMe = bigI.intValue();
            Cursor model = helper.getdata();
            model.moveToLast();
            int tdysteps = Integer.parseInt(helper.getTodaySteps());

            //int tdysteps = 19;

            storeMe = storeMe/100000000;
            storeMe = storeMe + tdysteps;
            String today = simpleDateFormat.format(new Date());
            if (helper.checkForTables() == false)
                helper.insert("0", today, storeMe);
            else
                helper.updateBal(storeMe, today);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "balance failed", Toast.LENGTH_SHORT).show();
        }
    }

    /* ----- Setup the security provider ----- */
    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up a provider  when it's used for the first time.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class))
            return;
        // There is a possibility the bouncy castle registered by android may not have all ciphers
        // so we substitute with the one bundled in the app.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }
}

