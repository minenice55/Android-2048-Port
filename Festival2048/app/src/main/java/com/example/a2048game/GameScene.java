package com.example.a2048game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GameScene extends AppCompatActivity implements
        GestureDetector.OnGestureListener {

    private GestureDetectorCompat mDetector;
    static final float SWIPE_THRESHOLD = 1000.0f;

    int[] GridCells = {
            R.id.GameCell00, R.id.GameCell10, R.id.GameCell20, R.id.GameCell30,
            R.id.GameCell01, R.id.GameCell11, R.id.GameCell21, R.id.GameCell31,
            R.id.GameCell02, R.id.GameCell12, R.id.GameCell22, R.id.GameCell32,
            R.id.GameCell03, R.id.GameCell13, R.id.GameCell23, R.id.GameCell33,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scene);
        mDetector = new GestureDetectorCompat(this,this);


//        Intent intent4 = new Intent(GameScene.this, Scores.class);
//        startActivity(intent4);
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
            }
            else if (vX < -SWIPE_THRESHOLD)
            {
                Log.println(Log.DEBUG, "Flicked", "Left");
            }
        }
        else
        {
            if (vY > SWIPE_THRESHOLD)
            {
                Log.println(Log.DEBUG, "Flicked", "Down");
            }
            else if (vY < -SWIPE_THRESHOLD)
            {
                Log.println(Log.DEBUG, "Flicked", "Up");
            }
        }
        return false;
    }
}