package com.example.a2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GameScene extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scene);

//        Intent intent4 = new Intent(GameScene.this, Scores.class);
//        startActivity(intent4);
    }
}