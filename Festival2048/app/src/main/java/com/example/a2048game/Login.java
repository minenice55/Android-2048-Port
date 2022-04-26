package com.example.a2048game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText userName, passWord;
    Button btnPlay;
    TextView createUser;
    ImageButton info;

    long lastId = 0;
    ArrayList<Player> players;

    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.editTextName);
        passWord = findViewById(R.id.editTextPass);
        btnPlay = findViewById(R.id.btnPlay);
        createUser = findViewById(R.id.textViewButton2);
        info = findViewById(R.id.imageButtonInfo);

        players = new ArrayList<>();

        database = FirebaseDatabase.getInstance("https://festival2048-default-rtdb.firebaseio.com/");
        ref = database.getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    lastId = (snapshot).getChildrenCount();
                    players = new ArrayList<>();
                    for (DataSnapshot child: snapshot.getChildren()) {
                        players.add(child.getValue(Player.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInf = userName.getText().toString().trim();
                String passwordInf = passWord.getText().toString();

                if (usernameInf.length() == 0 && passWord.getText().toString().trim().length() == 0) {
                    Toast.makeText(Login.this, "Please fill in each information to proceed!",Toast.LENGTH_LONG).show();
                }
                else {
                    Player match = findMatchingPlayer(usernameInf, passwordInf);
                    if (match != null) {
                        Intent intent1 = new Intent(Login.this, GameScene.class);
                        intent1.putExtra("playerName", match.getUsername());
                        intent1.putExtra("playerMii", match.getAvatarCode());
                        intent1.putExtra("playerBest", match.getHighScore());
                        startActivity(intent1);
                    }
                    else
                    {
                        Toast.makeText(Login.this, "No matching account! Please check entered credentials.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(Login.this, Creation.class);
                startActivity(intent3);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });

    }

    private Player findMatchingPlayer(String username)
    {
        Player match = null;
        for (Player player : players) {
            if (player.getUsername().equals(username))
                match = player;
        }
        return match;
    }

    private Player findMatchingPlayer(String username, String password)
    {
        Player match = null;
        for (Player player : players) {
            if (player.getUsername().equals(username) && player.getPasswordHash().equals(password))
                match = player;
        }
        return match;
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Swipe up, down, left or right to combine matching blocks!\nCombine blocks to rack up a high score!");
        dialog.setTitle("How To Play");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}