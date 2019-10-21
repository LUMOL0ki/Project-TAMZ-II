package com.example.spaceshooter;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class UIManager {

    private Window window;

    UIManager(Window window){
        this.window = window;
    }

    public void setFullscreen(){
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void hideNavigationBar(){
        this.window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }
}