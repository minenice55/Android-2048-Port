package com.example.a2048game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class Scores extends AppCompatActivity {
    Button btnPlay2;
    TextView deadScoreDisp;

    Map<String, Player> players;

    FirebaseDatabase database;
    DatabaseReference ref;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        btnPlay2 = findViewById(R.id.replayBtn);
        deadScoreDisp = findViewById(R.id.deadScoreDisp);

        username = getIntent().getStringExtra("playerName");

        deadScoreDisp.setText("Final Score: " + Integer.toString(getIntent().getIntExtra("deadScore", 0)));

        database = FirebaseDatabase.getInstance("https://festival2048-default-rtdb.firebaseio.com/");
        ref = database.getReference("users");

        // TODO: list out all scores in our highScoreDisp RecyclerView

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    players = new HashMap<>();

                    for (DataSnapshot child: snapshot.getChildren()) {
                        players.put(child.getKey(), child.getValue(Player.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player match = findMatchingPlayer(username);
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
    }

    private Player findMatchingPlayer(String username)
    {
        Log.println(Log.DEBUG, "Looking players ", "matching for " + username);
        return players.get(username);
    }
}