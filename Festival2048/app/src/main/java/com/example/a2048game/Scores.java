package com.example.a2048game;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Scores extends AppCompatActivity {
    Button btnPlay2;
    TextView deadScoreDisp;

    String username;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        btnPlay2 = findViewById(R.id.replayBtn);
        deadScoreDisp = findViewById(R.id.deadScoreDisp);

        username = getIntent().getStringExtra("playerName");

        deadScoreDisp.setText("Final Score: " + Integer.toString(getIntent().getIntExtra("deadScore", 0)));

        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player match = DBHelper.getInstance().findMatchingPlayer(username);
                if (match != null) {
                    Intent intent5 = new Intent(Scores.this, GameScene.class);
                    intent5.putExtra("playerName", match.getUsername());
                    intent5.putExtra("playerMii", match.getAvatarCode());
                    intent5.putExtra("playerBest", match.getHighScore());
                    startActivity(intent5);
                }
                else {
                    Toast.makeText(Scores.this, "Disconnected! Please log in.", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(Scores.this, Login.class);
                    startActivity(intent1);
                }
            }
        });

        RecyclerView scoreDisp = findViewById(R.id.highScoreDisp);
        ArrayList<Player> players = new ArrayList<>(DBHelper.getPlayers().values().stream().sorted(
                                        Comparator.comparingInt(Player::getHighScore).reversed()
                                    ).collect(Collectors.toList()));

        ScoreAdapter adapt = new ScoreAdapter(players, this);
        scoreDisp.setAdapter(adapt);
        scoreDisp.setLayoutManager(new LinearLayoutManager(this));
    }
}