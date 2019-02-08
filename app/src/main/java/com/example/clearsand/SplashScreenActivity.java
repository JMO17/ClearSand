package com.example.clearsand;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    TextView myTitle;
    //ImageView myLogo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        myTitle = findViewById(R.id.txtNombreSplashScreen);
        //myLogo = findViewById(R.id.ivLogoSplashScreen);

        Animation animClearSand = AnimationUtils.loadAnimation(this, R.anim.fadein);
        //Animation animLogo = AnimationUtils.loadAnimation(this,R.anim.shake);

        myTitle.startAnimation(animClearSand);
        //myLogo.startAnimation(animLogo);

        getSupportActionBar().hide();

        openApp(true);


    }

    private void openApp(boolean locationPermission) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
