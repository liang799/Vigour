package com.sp.vigour.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.sp.vigour.CryptoWorker;
import com.sp.vigour.R;

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
        WorkRequest uploadWorkRequest =
                new OneTimeWorkRequest.Builder(CryptoWorker.class)
                        .build();
        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(uploadWorkRequest);

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