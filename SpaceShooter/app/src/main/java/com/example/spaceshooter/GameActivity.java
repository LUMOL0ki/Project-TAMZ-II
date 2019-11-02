package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.TextView;

import com.example.spaceshooter.Game.Player;

import java.util.Vector;

public class GameActivity extends AppCompatActivity {
    private UIManager UIManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        UIManager = new UIManager(this.getWindow());
        //Fullscreen
        UIManager.setFullscreen();
        //Navigation bar
        UIManager.hideNavigationBar();
        setContentView(R.layout.activity_game);

        Player player = new Player(this);

        TextView score = findViewById(R.id.scoreTextView);
        TextView health = findViewById(R.id.healthTextView);
        score.setText(String.valueOf(player.getScore()));
        health.setText(String.valueOf(player.getHealth()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Navigation bar
        UIManager.hideNavigationBar();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
