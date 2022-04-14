package com.example.festival2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    MiiStudioApi testMii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMii = new MiiStudioApi("080040030C040402020C0306060406020A0000000005000804000A0600374004000214031304170D04000A040109");
        Log.println(Log.DEBUG, "mii url:", testMii.getMiiUrl());
    }
}