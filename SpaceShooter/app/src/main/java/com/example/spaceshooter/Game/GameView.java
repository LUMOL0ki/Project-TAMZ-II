package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setGameState(GameState.RUNNING);
        Log.d("Game","Game started");
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while (retry){
            try{
                gameThread.setGameState(GameState.CLOSED);
                Log.d("Game","Game was closed");
                gameThread.join();
            } catch (Exception e){
                e.printStackTrace();
            }
            retry = false;
        }

    }

    private Context context;
    private int screenX;
    private int screenY;
    private Player player;
    private GameState gameState;
    private GameThread gameThread;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        getHolder().addCallback(this);

        gameThread = new GameThread(this, getHolder());
        setFocusable(true);

        player = new Player(context, getWidth(), getHeight());
        this.context = context;
        gameState = GameState.RUNNING;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.screenX = w;
        this.screenY = h;
        player.updateSize(w, h);
        //Log.d("Game", "Width: " + w + " Height: " + h);
    }

    public Player getPlayer(){
        return player;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }

    public void update(){
        player.update();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Log.d("Game", "onDraw executed");
        //Log.d("Game", player.getX() + " " + player.getY());
        canvas.drawBitmap(player.getModel(), player.getX(), player.getY(), null);

    }

    @Override
    protected void onAttachedToWindow() {
        gameState = GameState.RUNNING;
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        gameState = GameState.CLOSED;
        //Log.d("Game","Game was closed");
        super.onDetachedFromWindow();
    }
}
