package com.example.spart.recupmynews.Controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.spart.recupmynews.R;


public class SplashScreen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen);

        getWindow().setStatusBarColor( ContextCompat.getColor(this,
                R.color.colorStatusBarHome ));

        new Handler(  ).postDelayed( new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, 7000);
    }
}
