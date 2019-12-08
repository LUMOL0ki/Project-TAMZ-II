package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.example.spaceshooter.Game.Sounds;

public class OptionsActivity extends AppCompatActivity {
    private UIManager UIManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIManager = new UIManager(this.getWindow());
        //Fullscreen
        UIManager.setFullscreen();
        //Navigation bar
        UIManager.hideNavigationBar();
        setContentView(R.layout.activity_options);

        Switch audio = findViewById(R.id.audioSwitch);
        audio.setChecked(Sounds.getInstance().getIsPlaying());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Navigation bar
        UIManager.hideNavigationBar();
    }

    public void onBackClick(View view){
        this.UIManager = null;
        this.closeContextMenu();
        finish();
    }

    public void onAudioClick(View view){
        Sounds.getInstance().setIsPlaying(!Sounds.getInstance().getIsPlaying());
        Switch audio = findViewById(R.id.audioSwitch);
        audio.setChecked(Sounds.getInstance().getIsPlaying());
    }
}
