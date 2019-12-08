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

public class MainActivity extends AppCompatActivity {
    private UIManager UIManager;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIManager = new UIManager(this.getWindow());
        //Fullscreen
        UIManager.setFullscreen();
        //Navigation bar
        UIManager.hideNavigationBar();
        setContentView(R.layout.activity_main);

        Button continueBtn = findViewById(R.id.continueButton);
        continueBtn.setEnabled(false);

        if (Sounds.getInstance().getIsPlaying()) {
            mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.space_station);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.start();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Navigation bar
        UIManager.hideNavigationBar();
        //mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.space_station);
    }

    public void onContinueButtonClick(View view){
        Intent continueGame = new Intent(getBaseContext(), GameActivity.class);
        Sounds.release(mediaPlayer);
        startActivity(continueGame);
    }

    public void continueButtonSetEnabled(boolean enabled){
        Button continueBtn = findViewById(R.id.continueButton);
        continueBtn.setEnabled(enabled);
        /*if(enabled){
            continueBtn.setBackgroundColor(getResources().getColor(R.color.continueEnabled));
        }else {
            continueBtn.setBackgroundColor(getResources().getColor(R.color.continueDisabled));
        }*/
    }

    public void onNewGameButtonClick(View view){
        Intent startNewGame = new Intent(getBaseContext(), GameActivity.class);
        Sounds.release(mediaPlayer);
        startActivity(startNewGame);
    }

    public void onStatisticsButtonClick(View view){
        Intent openStatistics = new Intent(getBaseContext(), LeaderboardActivity.class);
        //mediaPlayer.release();
        //mediaPlayer = null;
        startActivity(openStatistics);
    }

    public void onOptionsButtonClick(View view){
        Intent openOptions = new Intent(getBaseContext(), OptionsActivity.class);
        //mediaPlayer.release();
        //mediaPlayer = null;
        startActivity(openOptions);
    }
}
