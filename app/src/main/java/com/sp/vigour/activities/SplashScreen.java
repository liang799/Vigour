package com.sp.vigour.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.sp.vigour.R;
import com.sp.vigour.workers.CryptoWorker;

import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /* ---- play music ---- */
        if(mediaPlayer ==  null){
            mediaPlayer = MediaPlayer.create(this,R.raw.windowsound);
        }
        mediaPlayer.start();

        /* ---- crypto stuff ---- */
        /*PeriodicWorkRequest cryptoUpdate =
                new PeriodicWorkRequest.Builder(CryptoWorker.class, 1, TimeUnit.MICROSECONDS)
                        // Constraints
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "Get Balance",
                ExistingPeriodicWorkPolicy.KEEP,
                cryptoUpdate);*/

        /* ---- goto login ---- */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        }, 2500);
    }
}