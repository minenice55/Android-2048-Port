package com.example.a2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Scores extends AppCompatActivity {
    Button btnPlay2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        btnPlay2 = findViewById(R.id.replayBtn);

        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent5 = new Intent(Scores.this, GameScene.class);
                startActivity(intent5);

            }
        });
    }
}