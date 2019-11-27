package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.spaceshooter.Game.GameView;


public class GameActivity extends AppCompatActivity{
    private UIManager UIManager;
    private GameView gameView;
    private double moveSpeed;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private View.OnTouchListener onTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] > 0.5 || event.values[0] < -0.5){
                    gameView.getPlayer().moveLeft(-event.values[0]);
                }else {
                    gameView.getPlayer().moveLeft(0);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        gameView.setZOrderOnTop(true);
        //gameView.setZOrderMediaOverlay(true);
        gameView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        TextView score = findViewById(R.id.scoreTextView);
        TextView health = findViewById(R.id.healthTextView);
        score.setText("" + gameView.getPlayer().getScore());
        health.setText("Health: " + gameView.getPlayer().getHealth());

        Button left = findViewById(R.id.leftButton);
        Button right = findViewById(R.id.rightButton);

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        gameView.getPlayer().moveLeft(-1);
                        //Log.d("Window", "leftButton touched");
                        break;
                    case MotionEvent.ACTION_UP:
                        gameView.getPlayer().moveLeft(0);
                        break;
                }
                return false;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        gameView.getPlayer().moveLeft(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        gameView.getPlayer().moveLeft(0);
                        break;
                }
                return false;
            }
        });
    }

    public void onClickFire(View view){
        gameView.getPlayer().fire();
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

    public void onMotionEvent(MotionEvent e){

    }
}
