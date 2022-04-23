package com.example.a2048game;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Creation extends AppCompatActivity {
    EditText username, email, password;
    Button btnRegister;
    TextView existing;
    ImageButton info2;
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

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";;


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().length() == 0 && email.getText().toString().trim().length() == 0 && password.getText().toString().trim().length() == 0) {
                    Toast.makeText(Creation.this, "Please fill in each information to proceed!",Toast.LENGTH_LONG).show();
                } else if (username.getText().toString().trim().length() > 12){
                    Toast.makeText(Creation.this, "The Username entered is longer than 12 characters!",Toast.LENGTH_LONG).show();
                } else if (!email.getText().toString().trim().matches(emailPattern)){
                    Toast.makeText(Creation.this, "Please enter a proper email address!",Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().trim().length() < 6) {
                    Toast.makeText(Creation.this, "Please enter a password larger than 6 characters long!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent1 = new Intent(Creation.this, GameScene.class);
                    startActivity(intent1);
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
        dialog.setMessage("All you need to do is swipe up, down, right and/or left in order to combine the same numbers. \n\nThe more blocks you combine the higher score you get!!!");
        dialog.setTitle("How To Play");
        dialog.setPositiveButton("I Understand",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}