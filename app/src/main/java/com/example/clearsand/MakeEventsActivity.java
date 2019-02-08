package com.example.clearsand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MakeEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_events);

        /*Esconder la AppBar*/
        getSupportActionBar().hide();

    }
}
