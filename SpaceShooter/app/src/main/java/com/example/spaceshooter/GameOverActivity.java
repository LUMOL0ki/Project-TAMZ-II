package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Button retry = findViewById(R.id.retryButton);
    }

    public void onRetry(View view){
        Intent startNewGame = new Intent(getBaseContext(), GameActivity.class);
        startActivity(startNewGame);
    }

    public void onBackToMenu(View view){
        Intent backToMenu = new Intent(getBaseContext(), MainActivity.class);
        startActivity(backToMenu);
    }
}
