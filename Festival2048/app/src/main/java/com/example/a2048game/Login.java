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

public class Login extends AppCompatActivity {
    EditText userName, passWord;
    Button btnPlay;
    TextView createUser;
    ImageButton info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.editTextName);
        passWord = findViewById(R.id.editTextPass);
        btnPlay = findViewById(R.id.btnPlay);
        createUser = findViewById(R.id.textViewButton2);
        info = findViewById(R.id.imageButtonInfo);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().toString().trim().length() == 0 && passWord.getText().toString().trim().length() == 0) {
                    Toast.makeText(Login.this, "Please fill in each information to proceed!",Toast.LENGTH_LONG).show();
                } else if (userName.getText().toString().trim().length() == 0){ //Check if the username matches in firebase (Needs to be added)
                    Toast.makeText(Login.this, "The username entered is too long!",Toast.LENGTH_LONG).show();
                } else if (passWord.getText().toString().trim().length() == 0) {//Check if the password matches the user in firebase (Needs to be added)
                    Toast.makeText(Login.this, "Please enter a password!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent1 = new Intent(Login.this, GameScene.class);
                    startActivity(intent1);
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