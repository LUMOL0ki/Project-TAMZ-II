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

import com.example.spaceshooter.Game.Leaderboard;
import com.example.spaceshooter.Game.Sounds;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class GameOverActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private UIManager UIManager;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIManager = new UIManager(this.getWindow());
        //Fullscreen
        UIManager.setFullscreen();
        //Navigation bar
        UIManager.hideNavigationBar();

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

        score = getIntent().getIntExtra("score", 0);
        Log.d("Score", String.valueOf(score));

        Leaderboard leaderboard = new Leaderboard();

        try {
            FileInputStream fileInputStream = openFileInput("leaderboard.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuffer sb = new StringBuffer();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line+ ", ");
                String[] split = line.split(" ");
                leaderboard.nicks.add(split[0]);
                leaderboard.scores.add(Integer.parseInt(split[1]));
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(score > leaderboard.scores.get(leaderboard.scores.size()-1)){
            Button save = findViewById(R.id.saveButton);
            save.setEnabled(true);
        }
    }

    public void onRetry(View view){
        Intent startNewGame = new Intent(getBaseContext(), GameActivity.class);
        Sounds.release(mediaPlayer);
        SetSave();
        startActivity(startNewGame);
    }

    public void onBackToMenu(View view){
        Intent backToMenu = new Intent(getBaseContext(), MainActivity.class);
        Sounds.release(mediaPlayer);
        SetSave();
        startActivity(backToMenu);
    }

    public void onSave(View view){
        Intent saveScore = new Intent(getBaseContext(), SaveScoreActivity.class);
        saveScore.putExtra("score", score);
        SetSave();
        startActivity(saveScore);
    }

    public  void SetSave(){
        FileOutputStream outputStream;
        try {
            String content;
            content = 0 + " " + 0 + "\n";
            Log.d("Save", "Saved " + 0 + " " + 0 + "\n");
            outputStream = openFileOutput("Save.txt", getBaseContext().MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
