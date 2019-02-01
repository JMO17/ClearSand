package com.example.clearsand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    TextView myTitle;
    TextView merge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        myTitle = findViewById(R.id.txtNombreSplashScreen);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.fadein);

        myTitle.startAnimation(myanim);
    }
}
