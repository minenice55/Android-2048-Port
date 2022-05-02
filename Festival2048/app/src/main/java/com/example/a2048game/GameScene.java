package com.example.a2048game;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameScene extends AppCompatActivity implements
        GestureDetector.OnGestureListener {

    // TODO: festival game

    private GestureDetectorCompat mDetector;
    private GameGrid grid;
    static final float SWIPE_THRESHOLD = 1000.0f;

    int score;
    TextView scoreDisp, usernameDisp, bestDisp;
    ImageView avatarDisp;

    TextView fesWinningNotice, fesWinningPlayer;
    ImageView fesWinningAvatar;

    int pb;
    String msmn;
    String username;

    MiiStudioApi mii;

    protected int[] GridCells = {
            R.id.GameCell00, R.id.GameCell10, R.id.GameCell20, R.id.GameCell30,
            R.id.GameCell01, R.id.GameCell11, R.id.GameCell21, R.id.GameCell31,
            R.id.GameCell02, R.id.GameCell12, R.id.GameCell22, R.id.GameCell32,
            R.id.GameCell03, R.id.GameCell13, R.id.GameCell23, R.id.GameCell33,
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scene);

        pb = getIntent().getIntExtra("playerBest", 0);
        username = getIntent().getStringExtra("playerName");
        msmn = getIntent().getStringExtra("playerMii");

        mii = new MiiStudioApi(msmn);

        mDetector = new GestureDetectorCompat(this,this);
        grid = new GameGrid(this);

        score = 0;
        scoreDisp = findViewById(R.id.scoreDisp);
        usernameDisp = findViewById(R.id.usernameDisp);
        bestDisp = findViewById(R.id.bestDisp);
        avatarDisp = findViewById(R.id.avatarDisp);

        usernameDisp.setText(username);
        bestDisp.setText(Integer.toString(pb));

        Glide
            .with(this)
            .load(mii.getMiiUrl())
            .centerCrop()
            .into(avatarDisp);

        grid.attemptRandPopulate(2);

        // set msg showing current best player (this doesn't update in real time)
        Optional<Player> topPlayer = DBHelper.getPlayers().values().stream().max(
                Comparator.comparingInt(Player::getHighScore));
        if (topPlayer.isPresent() && !topPlayer.get().getUsername().equals(username))
        {
            fesWinningNotice = findViewById(R.id.fesWinningNotice);
            fesWinningPlayer = findViewById(R.id.fesWinningPlayer);
            fesWinningAvatar = findViewById(R.id.fesWinningAvatar);

            fesWinningNotice.setVisibility(View.VISIBLE);
            fesWinningPlayer.setVisibility(View.VISIBLE);
            fesWinningAvatar.setVisibility(View.VISIBLE);

            fesWinningNotice.setText("Beat " + topPlayer.get().getUsername() + "!");
            fesWinningPlayer.setText(Integer.toString(topPlayer.get().getHighScore()));
            MiiStudioApi winningMii = new MiiStudioApi(topPlayer.get().getAvatarCode());
            Glide
                .with(this)
                .load(winningMii.getMiiUrl())
                .centerCrop()
                .into(fesWinningAvatar);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float vX, float vY) {
        Log.println(Log.DEBUG, "onFling:", "xvel = " + vX + " yvel = " + vY);
        if (Math.abs(vX) > Math.abs(vY))
        {
            if (vX > SWIPE_THRESHOLD)
            {
                Log.println(Log.DEBUG, "Flicked", "Right");
                if (grid.swipeRows(true))
                    grid.attemptRandPopulate(1);
            }
            else if (vX < -SWIPE_THRESHOLD)
            {
                Log.println(Log.DEBUG, "Flicked", "Left");
                if (grid.swipeRows(false))
                    grid.attemptRandPopulate(1);
            }
        }
        else
        {
            if (vY > SWIPE_THRESHOLD)
            {
                Log.println(Log.DEBUG, "Flicked", "Down");
                if (grid.swipeColumns(true))
                    grid.attemptRandPopulate(1);
            }
            else if (vY < -SWIPE_THRESHOLD)
            {
                Log.println(Log.DEBUG, "Flicked", "Up");
                if (grid.swipeColumns(false))
                    grid.attemptRandPopulate(1);
            }
        }
        if (grid.getNumAvailableCells() == 0 && !grid.checkMovesPossible())
        {
            if (score > pb) {
                DatabaseReference pRef = DBHelper.getUsersReference().child(username);
                Map<String, Object> pUp = new HashMap<>();
                pUp.put("highScore", score);

                pRef.updateChildren(pUp);
            }

            //game over, go to score scene
            Intent intent4 = new Intent(GameScene.this, Scores.class);
            intent4.putExtra("deadScore", getScore());
            intent4.putExtra("playerName", username);
            startActivity(intent4);

        }
        return false;
    }

    public int[] getGridCells() {
        return GridCells;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        this.scoreDisp.setText(Integer.toString(this.score));
    }

    public void addScore(int score) {
        this.score += score;
        this.scoreDisp.setText(Integer.toString(this.score));
    }
}