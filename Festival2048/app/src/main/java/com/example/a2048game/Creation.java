package com.example.a2048game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Creation extends AppCompatActivity {
    EditText username, email, password;
    Button btnRegister;
    TextView existing;
    ImageButton info2;

    long lastId = 0;

    FirebaseDatabase database;
    DatabaseReference ref;

    Map<String, Player> players;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        username = findViewById(R.id.editTextUser);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassWord);
        btnRegister = findViewById(R.id.btnGo);
        existing= findViewById(R.id.textViewButton);
        info2 = findViewById(R.id.imageButtonInfo2);


        players = new HashMap<>();

        database = FirebaseDatabase.getInstance("https://festival2048-default-rtdb.firebaseio.com/");
        ref = database.getReference("users");

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";;

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    lastId = (snapshot).getChildrenCount();
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInf = username.getText().toString().trim();
                String emailInf = email.getText().toString().trim();
                String passwordInf = password.getText().toString();

                if (usernameInf.length() == 0 && emailInf.length() == 0 && passwordInf.length() == 0) {
                    Toast.makeText(Creation.this, "Please fill in each information to proceed!",Toast.LENGTH_LONG).show();
                } else if (usernameInf.length() > 10){
                    Toast.makeText(Creation.this, "The Username entered is longer than 10 characters!",Toast.LENGTH_LONG).show();
                } else if (!emailInf.matches(emailPattern)){
                    Toast.makeText(Creation.this, "Please enter a proper email address!",Toast.LENGTH_LONG).show();
                } else if (passwordInf.length() < 6) {
                    Toast.makeText(Creation.this, "Please enter a password larger than 6 characters long!", Toast.LENGTH_LONG).show();
                } else {
                    if (DBHelper.addPlayer(Long.toString(lastId), usernameInf, passwordInf, emailInf))
                    {
                        // TODO: send confirmation email when signing up (FireBase has this feature?)
                        Toast.makeText(Creation.this, "Successfully made account! Please log in.", Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(Creation.this, Login.class);
                        startActivity(intent1);
                    }
                    else
                    {
                        Toast.makeText(Creation.this, "Another player already uses this username!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        existing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Creation.this, Login.class);
                startActivity(intent2);
            }
        });

        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });

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