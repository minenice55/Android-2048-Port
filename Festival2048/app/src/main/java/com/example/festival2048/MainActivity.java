package com.example.festival2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    MiiStudioApi testMii;
    ImageView testMiiDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMiiDisp = findViewById(R.id.testMiiDisp);

        testMii = new MiiStudioApi("0800390522030504010d0353060400020a0004090007010804000a53005456040002140413010f0d04000a020109");
        Log.println(Log.DEBUG, "mii url:", testMii.getMiiUrl());

        Glide
            .with(this)
            .load(testMii.getMiiUrl())
            .centerCrop()
            .into(testMiiDisp);
    }
}