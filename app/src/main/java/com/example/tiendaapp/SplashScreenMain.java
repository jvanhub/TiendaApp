package com.example.tiendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;


import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenMain.this, Login.class);
                startActivity(intent);
                finish();
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(tt,3000);
    }

}