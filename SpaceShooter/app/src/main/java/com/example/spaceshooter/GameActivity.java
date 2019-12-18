package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.spaceshooter.Game.Game;
import com.example.spaceshooter.Game.GameState;
import com.example.spaceshooter.Game.GameView;
import com.example.spaceshooter.Game.Sounds;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Observable;


public class GameActivity extends AppCompatActivity{
    private UIManager UIManager;
    private GameView gameView;
    private double moveSpeed;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    //private boolean senzor; //on or off senzor listener
    private MediaPlayer mediaPlayer;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Sounds.getInstance().getIsPlaying()) {
            mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.hum);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.start();
                }
            });
        }
        super.onCreate(savedInstanceState);

        UIManager = new UIManager(this.getWindow());
        //Fullscreen
        UIManager.setFullscreen();
        //Navigation bar
        UIManager.hideNavigationBar();
        setContentView(R.layout.activity_game);
        moveSpeed = 10;

        gameView = findViewById(R.id.gameView);
        gameView.getPlayer().setScore(getIntent().getIntExtra("Score", 0));
        gameView.getPlayer().initHealth(getIntent().getIntExtra("Health", 100));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorEventListener = new SensorEventListener() {
            private float prevValue = 0;
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] > 0.5 || event.values[0] < -0.5){
                    gameView.getPlayer().moveLeft(-event.values[0]);
                    prevValue = event.values[0];
                }else if(event.values[0] != prevValue){
                    gameView.getPlayer().moveLeft(0);
                    prevValue = event.values[0];
                    Log.d("Senzor", String.valueOf(prevValue));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        gameView.setZOrderOnTop(true);
        //gameView.setZOrderMediaOverlay(true);
        gameView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Game.GameListener gameListener = new Game.GameListener() {
            @Override
            public void onGameOver() {
                Log.d("Gameover", "" + gameView.getPlayer().getScore());
                Intent startGameOver = new Intent(getBaseContext(), GameOverActivity.class);
                startGameOver.putExtra("score", gameView.getPlayer().getScore());
                Sounds.release(mediaPlayer);
                startActivity(startGameOver);
            }

            @Override
            public void onPause() {
                Intent pause = new Intent(getBaseContext(), MainActivity.class);
                Sounds.release(mediaPlayer);
                save();
                startActivity(pause);
            }
        };

        gameView.addGameListener(gameListener);
        gameView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if(gameView.getPlayer().getHealth() > 0){
                    save();
                }
                Sounds.release(mediaPlayer);
            }
        });
    }

    public void onClickFire(View view){
        boolean f = gameView.getPlayer().fire();
        Button fireBtn = findViewById(R.id.fireButton);
        if(f){
            Log.d("Player", "firing");
            fireBtn.setBackgroundColor(getResources().getColor(R.color.continueEnabled));
        }
        else {
            Log.d("Player", "not firing");
            fireBtn.setBackgroundColor(getResources().getColor(R.color.continueDisabled));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Navigation bar
        UIManager.hideNavigationBar();
        sensorManager.registerListener(sensorEventListener, sensor, sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    private void save(){
        FileOutputStream outputStream;
        try {
            String content;
            content = gameView.getPlayer().getHealth() + " " + gameView.getPlayer().getScore() + "\n";
            Log.d("Save", "Saved " + gameView.getPlayer().getHealth() + " " + gameView.getPlayer().getScore() + "\n");
            outputStream = openFileOutput("Save.txt", getBaseContext().MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
