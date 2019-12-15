package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.spaceshooter.Game.Leaderboard;
import com.example.spaceshooter.Game.Sounds;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;

public class SaveScoreActivity extends AppCompatActivity {
    private UIManager UIManager;
    private int score;
    int at=0;
    Leaderboard leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIManager = new UIManager(this.getWindow());
        //Fullscreen
        UIManager.setFullscreen();
        //Navigation bar
        UIManager.hideNavigationBar();

        setContentView(R.layout.activity_save_score);
        TextView scoreText = findViewById(R.id.scoreText);
        score = getIntent().getIntExtra("score", 0);
        scoreText.setText(scoreText.getText() + String.valueOf(score));

        leaderboard = new Leaderboard();

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

        for(int i = leaderboard.nicks.size()-1; i >= 0; i--){
            Log.d("File", leaderboard.nicks.get(i) + " " + leaderboard.scores.get(i));
            if(score > leaderboard.scores.get(i)){
                Log.d("File", "Smaller " + leaderboard.nicks.get(i) + " " + leaderboard.scores.get(i));
                at = i;
                if(i < leaderboard.nicks.size()-1){
                    leaderboard.nicks.set(i+1, leaderboard.nicks.get(i));
                    leaderboard.scores.set(i+1, leaderboard.scores.get(i));
                }
            }
        }
        leaderboard.scores.set(at, score);


    }

    public void onSave(View view){
        TextView nick = findViewById(R.id.nickname);
        if (nick.getText().toString().length() != 0){

        leaderboard.nicks.set(at, nick.getText().toString());
            FileOutputStream outputStream;

        try {
            String content = "";
            for (int i = 0; i <leaderboard.nicks.size(); i++){
                content += leaderboard.nicks.get(i) + " " + leaderboard.scores.get(i).toString() + "\n";
            }

            outputStream = openFileOutput("leaderboard.txt", getBaseContext().MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
            Intent startGameOver = new Intent(getBaseContext(), MainActivity.class);
            startActivity(startGameOver);
        }
    }
}
