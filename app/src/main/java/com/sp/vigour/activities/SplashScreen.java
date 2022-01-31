package com.sp.vigour.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.sp.vigour.R;

public class SplashScreen extends AppCompatActivity {

    ProgressBar pb;
    int counter = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(mediaPlayer ==  null){
            mediaPlayer = MediaPlayer.create(this,R.raw.windowsound);
        }

        mediaPlayer.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        }, 2500);
    }
}