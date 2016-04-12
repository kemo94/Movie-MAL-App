package com.example.kemos.fainalmoviemalapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.example.kemos.fainalmoviemalapp.Controller.MainActivity;

public class Splash extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
       //  requestWindowFeature(Window.FEATURE_NO_TITLE);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }

}
