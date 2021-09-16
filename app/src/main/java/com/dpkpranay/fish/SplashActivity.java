package com.dpkpranay.fish;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    Timer timer;
    private MediaPlayer m1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                m1.start();
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        },5000);
        m1=MediaPlayer.create(this,R.raw.herewego);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
