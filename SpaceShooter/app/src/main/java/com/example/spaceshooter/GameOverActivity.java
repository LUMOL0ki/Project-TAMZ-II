package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spaceshooter.Game.Sounds;

public class GameOverActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Button retry = findViewById(R.id.retryButton);

        if (Sounds.getInstance().getIsPlaying()) {
            mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.dead);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
    }

    public void onRetry(View view){
        Intent startNewGame = new Intent(getBaseContext(), GameActivity.class);
        Sounds.release(mediaPlayer);
        startActivity(startNewGame);
    }

    public void onBackToMenu(View view){
        Intent backToMenu = new Intent(getBaseContext(), MainActivity.class);
        Sounds.release(mediaPlayer);
        startActivity(backToMenu);
    }
}
