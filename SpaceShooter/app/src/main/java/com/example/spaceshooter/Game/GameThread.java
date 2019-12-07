package com.example.spaceshooter.Game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.List;

public class GameThread extends Thread {
    private int FPS = 60;
    private double avgFPS;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private GameState gameState;
    public static Canvas canvas;

    public GameThread(GameView gameView, SurfaceHolder surfaceHolder){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        long starTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / FPS;

        //Game loop
        while(gameState == GameState.RUNNING){
            starTime = System.nanoTime();
            canvas = null;

            try{
                canvas = surfaceHolder.lockCanvas();
                //Log.d("Game", "run executed");
                synchronized (surfaceHolder){
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - starTime) / 10000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            }catch (Exception e){
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - starTime;
            frameCount++;
            if (frameCount == FPS){
                avgFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                //Log.d("GameThread", "Average FPS: " + avgFPS);
            }
        }
    }

    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }
}

