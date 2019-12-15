package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
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

        gameView = findViewById(R.id.gameView);
        moveSpeed = 10;

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
        };

        gameView.addGameListener(gameListener);
        gameView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Sounds.release(mediaPlayer);
            }
        });
    }

    public void onClickFire(View view){
        boolean f = gameView.getPlayer().fire();
        if(f){
            Log.d("Player", "firing");
        }
        else {
            Log.d("Player", "not firing");
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
}
