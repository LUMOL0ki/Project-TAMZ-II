package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIManager uiManager = new UIManager(this.getWindow());

        //Fullscreen
        uiManager.setFullscreen();
        //Navigation bar
        uiManager.hideNavigationBar();

        setContentView(R.layout.activity_main);

        Button continueBtn = findViewById(R.id.continueButton);
        continueBtn.setEnabled(false);

    }

    public void onContinueButtonClick(View view){
        Intent continueGame = new Intent(getBaseContext(), GameActivity.class);
        startActivity(continueGame);
    }

    public void onNewGameButtonClick(View view){
        Intent startNewGame = new Intent(getBaseContext(), GameActivity.class);
        startActivity(startNewGame);
    }

    public void onStatisticsButtonClick(View view){
        Intent openStatistics = new Intent(getBaseContext(), StatisticsActivity.class);
        startActivity(openStatistics);
    }

    public void onOptionsButtonClick(View view){
        Intent openOptions = new Intent(getBaseContext(), OptionsActivity.class);
        startActivity(openOptions);
    }
}
