package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {
    UIManager uiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        uiManager = new UIManager(this.getWindow());
        //Fullscreen
        uiManager.setFullscreen();
        //Navigation bar
        uiManager.hideNavigationBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Navigation bar
        uiManager.hideNavigationBar();
    }
}
