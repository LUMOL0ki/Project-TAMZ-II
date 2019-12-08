package com.example.spaceshooter.Game;

import android.media.MediaPlayer;

public class Sounds {
    private static final Sounds ourInstance = new Sounds();

    public static Sounds getInstance() {
        return ourInstance;
    }

    private boolean isPlaying = false;

    private Sounds() {
    }

    public boolean getIsPlaying(){
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public static MediaPlayer release(MediaPlayer mediaPlayer){
        if (mediaPlayer != null){
            mediaPlayer.release();
        }
        return null;
    }
}
