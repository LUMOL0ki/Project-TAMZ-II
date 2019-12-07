package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
                Log.d("Game","Game after join closed");
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
    private boolean gameOver = false;

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
        this.context = context;
        gameThread = new GameThread(this, getHolder());
        setFocusable(true);

        player = new Player(context, getWidth(), getHeight());
        player.setFireRate(3);
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

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() <= screenX * 3/8){
                    this.getPlayer().moveLeft(-1);
                }
                else if(event.getX() >= screenX * 5/8){
                    this.getPlayer().moveLeft(1);
                }
                return true;
            case MotionEvent.ACTION_UP:
                this.getPlayer().moveLeft(0);
                return false;
        }
        return super.onTouchEvent(event);
    }

    private ArrayList<Bullet> playerBullets = new ArrayList<>();
    private ArrayList<Heal> heals = new ArrayList<>();
    private ArrayList<Gun> guns = new ArrayList<>();
    private ArrayList<Asteroid> asteroids = new ArrayList<>();

    private Iterator<Bullet> itePlayerBullets;
    private Iterator<Heal> iteHeal;
    private Iterator<Gun> iteGun;
    private Iterator<Asteroid> iteAsteroid;

    public void update(){
        player.update();

        if(heals.isEmpty()){
            Heal tempHeal = new Heal(context, screenX, screenY);
            tempHeal.genPosX();
            heals.add(tempHeal);
        }

        if(guns.isEmpty()){
            Gun tempGun = new Gun(context, screenX, screenY, BulletMode.DUAL);
            tempGun.genPosX();
            guns.add(tempGun);
        }

        if(asteroids.size() < 3){
            Asteroid tempAsteroid = new Asteroid(context, screenX, screenY);
            tempAsteroid.genPosX();
            asteroids.add(tempAsteroid);
        }

        iteHeal = heals.iterator();
        iteGun = guns.iterator();
        iteAsteroid = asteroids.iterator();

        while (iteHeal.hasNext()){
            Heal heal = iteHeal.next();

            if(heal.posY > screenY){
                iteHeal.remove();
                heal.destroy();
            }
            else {
                heal.update();
                if (heal.getCollisionBound().intersect(player.getCollisionBound())){
                    iteHeal.remove();
                    heal.destroy();
                    player.restoreHealth(heal.getHeal());
                    onHealthChanged();
                }
            }
        }

        while (iteGun.hasNext()){
            Gun gun = iteGun.next();

            if(gun.posY > screenY){
                iteGun.remove();
                gun.destroy();
            }
            else {
                gun.update();
                if (gun.getCollisionBound().intersect(player.getCollisionBound())){
                    iteGun.remove();
                    gun.destroy();
                    //player.setFireRate();
                }
            }
        }

        while (iteAsteroid.hasNext()){
            Asteroid asteroid = iteAsteroid.next();

            if (asteroid.posY > screenY){
                iteAsteroid.remove();
                asteroid.destroy();
            }
            else {
                asteroid.update();
                if (asteroid.getCollisionBound().intersect(player.getCollisionBound())){
                    iteAsteroid.remove();
                    asteroid.destroy();
                    player.hit(asteroid.getDamage());
                    onHealthChanged();
                }
            }
        }

        if(player.isFiring()){
            player.setCooldown();
            if(player.getCooldown() == player.getFireRate()){
                Bullet temp = new Bullet(context, screenY, player.getX(), player.getY(), BulletMode.SINGLE, player.getModel(), 1, 30);
                playerBullets.add(temp);
            }
        }

        itePlayerBullets = playerBullets.iterator();

        while (itePlayerBullets.hasNext()){
            Bullet bullet = itePlayerBullets.next();

            if(bullet.getY() < 0 || bullet.getY() > screenY){
                itePlayerBullets.remove();
                bullet.destroy();
            }
            else {
                bullet.update();
            }
        }

        if (player.getHealth() <= 0){
            onDetachedFromWindow();
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Log.d("Game", "onDraw executed");
        //Log.d("Game", player.getX() + " " + player.getY());

        for (Heal heal: heals) {
            canvas.drawBitmap(heal.getModel(), heal.posX, heal.posY, null);
        }

        for (Gun gun: guns) {
            canvas.drawBitmap(gun.getModel(), gun.posX, gun.posY, null);
        }

        for (Asteroid asteroid: asteroids) {
            canvas.drawBitmap(asteroid.getModel(), asteroid.posX, asteroid.posY, null);
        }

        for (Bullet playerBullet : playerBullets) {
            canvas.drawBitmap(playerBullet.getModel(), playerBullet.getX(), playerBullet.getY(), null);
        }

        canvas.drawBitmap(player.getModel(), player.getX(), player.getY(), null);
    }

    public List<Bullet> getPlayerBullets(){
        return playerBullets;
    }

    public int getScreenY(){
        return screenY;
    }

    @Override
    protected void onAttachedToWindow() {
        gameState = GameState.RUNNING;
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        gameState = GameState.CLOSED;
        if (player.getHealth() <= 0){
            onGameOver();
        }
        //Log.d("Game","Game was closed");
        super.onDetachedFromWindow();
    }



    private List<Game.HealthListener> healthListeners = new ArrayList<>();

    public  void addHealthListener(Game.HealthListener healthListener){
        healthListeners.add(healthListener);
    }

    public void onHealthChanged(){
        for(Game.HealthListener healthListener : healthListeners){
            healthListener.onhealthChanged();
        }
    }

    private  List<Game.GameListener> gameListeners = new ArrayList<>();

    public void  addGameListener(Game.GameListener gameListener){
        gameListeners.add(gameListener);
    }

    public void onGameOver(){
        for(Game.GameListener gameListener : gameListeners){
            gameListener.onGameOver();
        }
    }
}
