package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.spaceshooter.Game.Sounds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private UIManager UIManager;
    private MediaPlayer mediaPlayer;
    private boolean isMadiaPlayer = false;
    int health;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.space_station);

        health = 0;
        score = 0;

        UIManager = new UIManager(this.getWindow());
        //Fullscreen
        UIManager.setFullscreen();
        //Navigation bar
        UIManager.hideNavigationBar();
        setContentView(R.layout.activity_main);
        GetSave();

        if (Sounds.getInstance().getIsPlaying()) {
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isMadiaPlayer = true;
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Navigation bar
        GetSave();
        UIManager.hideNavigationBar();
        if (Sounds.getInstance().getIsPlaying()) {
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.space_station);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        isMadiaPlayer = true;
                        mediaPlayer.setLooping(true);
                        mediaPlayer.start();
                    }
                });
            }
        }
        else {
            //mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.space_station);
            if (isMadiaPlayer){
                isMadiaPlayer =false;
                mediaPlayer.stop();
            }
        }
        //mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.space_station);
    }



    public void onContinueButtonClick(View view){
        Intent continueGame = new Intent(getBaseContext(), GameActivity.class);
        continueGame.putExtra("Health", health);
        continueGame.putExtra("Score", score);
        Sounds.release(mediaPlayer);
        startActivity(continueGame);
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

    public  void GetSave(){
        try {
            FileInputStream fileInputStream = openFileInput("Save.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuffer sb = new StringBuffer();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line+ ", ");
                Log.d("Save", line);
                String[] split = line.split(" ");
                health = Integer.parseInt(split[0]);
                score = Integer.parseInt(split[1]);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button continueBtn = findViewById(R.id.continueButton);
        continueBtn.setEnabled(false);
        if(health != 0){
            continueBtn = findViewById(R.id.continueButton);
            continueBtn.setEnabled(true);
        }
    }
}
