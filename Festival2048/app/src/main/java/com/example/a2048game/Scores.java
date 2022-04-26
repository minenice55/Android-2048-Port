package com.example.a2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Scores extends AppCompatActivity {
    Button btnPlay2;
    TextView deadScoreDisp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        btnPlay2 = findViewById(R.id.replayBtn);
        deadScoreDisp = findViewById(R.id.deadScoreDisp);

        deadScoreDisp.setText("Final Score: " + Integer.toString(getIntent().getIntExtra("deadScore", 0)));

        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent5 = new Intent(Scores.this, GameScene.class);
                startActivity(intent5);

            }
        });
    }
}