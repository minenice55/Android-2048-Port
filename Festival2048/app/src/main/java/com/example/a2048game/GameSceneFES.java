package com.example.a2048game;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameSceneFES extends GameScene {

    // TODO: festival game

    TextView fesPeriodNotice, fesWinningNotice, fesWinningPlayer, fesPeriodTime;
    ImageView fesWinningAvatar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fesPeriodNotice = findViewById(R.id.fesPeriodNotice);
        fesWinningNotice = findViewById(R.id.fesWinningNotice);
        fesWinningPlayer = findViewById(R.id.fesWinningPlayer);
        fesPeriodTime = findViewById(R.id.fesPeriodTime);
        fesWinningAvatar = findViewById(R.id.fesWinningAvatar);

        fesPeriodNotice.setVisibility(View.VISIBLE);
        fesWinningNotice.setVisibility(View.VISIBLE);
        fesWinningPlayer.setVisibility(View.VISIBLE);
        fesPeriodTime.setVisibility(View.VISIBLE);
        fesWinningAvatar.setVisibility(View.VISIBLE);
    }
}