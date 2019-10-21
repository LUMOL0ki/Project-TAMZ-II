package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private UIManager UIManager;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Navigation bar
        UIManager.hideNavigationBar();
    }

    public void onContinueButtonClick(View view){
        Intent continueGame = new Intent(getBaseContext(), GameActivity.class);
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
        startActivity(startNewGame);
    }

    public void onStatisticsButtonClick(View view){
        Intent openStatistics = new Intent(getBaseContext(), LeaderboardActivity.class);
        startActivity(openStatistics);
    }

    public void onOptionsButtonClick(View view){
        Intent openOptions = new Intent(getBaseContext(), OptionsActivity.class);
        startActivity(openOptions);
    }
}
