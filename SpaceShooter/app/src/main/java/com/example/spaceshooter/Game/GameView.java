package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import com.example.spaceshooter.Game.Factory.AsteroidFactory;
import com.example.spaceshooter.Game.Factory.BulletFactory;
import com.example.spaceshooter.Game.Factory.EnemyFactory;
import com.example.spaceshooter.Game.Factory.EnemyType;
import com.example.spaceshooter.Game.Factory.GunFactory;
import com.example.spaceshooter.Game.Factory.HealFactory;
import com.example.spaceshooter.Game.Factory.ReloadFactory;
import com.example.spaceshooter.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
        bulletFactory = new BulletFactory(screenY);
        enemyBulletFactory = new BulletFactory(screenY);
        healFactory = new HealFactory(screenY);
        reloadFactory = new ReloadFactory(screenY);
        asteroidFactory = new AsteroidFactory(screenY);
        gunFactory = new GunFactory(screenY);
        fighterFactory = new EnemyFactory(EnemyType.FIGTHER, screenY);
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

    BulletFactory bulletFactory;
    BulletFactory enemyBulletFactory;
    HealFactory healFactory;
    GunFactory gunFactory;
    AsteroidFactory asteroidFactory;
    ReloadFactory reloadFactory;
    EnemyFactory fighterFactory;

    public void update(){
        player.update();

        Iterator<Bullet> tempEnemyBullet = enemyBulletFactory.getArray().iterator();
        while (tempEnemyBullet.hasNext()) {
            Bullet bullet = tempEnemyBullet.next();
            if (bullet.getCollisionBound().intersect(player.getCollisionBound())) {
                tempEnemyBullet.remove();
                bullet.destroy();
                player.hit(bullet.getDamage());
                if (player.getHealth() <= 0){
                    //player.setScore(player.getScore());
                    tempEnemyBullet.remove();
                    player.destroy();
                }
            }
        }

        if(player.isFiring()){
            player.setCooldown();
            if(player.getCooldown() == player.getFireRate()){
                bulletFactory.add(new Bullet(context, screenY, player.getX(), player.getY(), BulletMode.SINGLE, player.getModel(), 1, 30));
            }
        }

        bulletFactory.setIterator();
        bulletFactory.update();

        if(player.getScore() % 100 == 0 && healFactory.isEmpty() && player.getScore() != 0){
            Heal tempHeal = new Heal(context, screenX, screenY);
            tempHeal.genPosX();
            healFactory.add(tempHeal);
        }

        healFactory.setIterator();
        healFactory.update();
        Iterator<Heal> tempHeal = healFactory.getArray().iterator();
        while (tempHeal.hasNext()){
            Heal heal = tempHeal.next();
            if (heal.getCollisionBound().intersect(player.getCollisionBound())){
                tempHeal.remove();
                heal.destroy();
                player.restoreHealth(heal.getHeal());
                onHealthChanged();
                //Log.d("Health", String.valueOf(player.getHealth()));
            }
        }

        if(player.getScore() % 400 == 0 && gunFactory.isEmpty() && player.getScore() != 0){
            Gun tempGun = new Gun(context, screenX, screenY, BulletMode.DUAL);
            tempGun.genPosX();
            gunFactory.add(tempGun);
        }

        gunFactory.setIterator();
        gunFactory.update();
        Iterator<Gun> tempGun = gunFactory.getArray().iterator();
        while (tempGun.hasNext()){
            Gun gun = tempGun.next();
            if (gun.getCollisionBound().intersect(player.getCollisionBound())){
                tempGun.remove();
                gun.destroy();
            }
        }

        if(asteroidFactory.getArray().size() < 3){
            Asteroid tempAsteroid = new Asteroid(context, screenX, screenY);
            tempAsteroid.genPosX();
            asteroidFactory.add(tempAsteroid);
        }

        asteroidFactory.setIterator();
        asteroidFactory.update();
        Iterator<Asteroid> tempAsteriod = asteroidFactory.getArray().iterator();
        while (tempAsteriod.hasNext()){
            Asteroid asteroid = tempAsteriod.next();
            if (asteroid.getCollisionBound().intersect(player.getCollisionBound())){
                tempAsteriod.remove();
                asteroid.destroy();
                player.hit(asteroid.getDamage());
                onHealthChanged();
            }
            else {
                Iterator<Bullet> tempBullet = bulletFactory.getArray().iterator();
                while (tempBullet.hasNext()) {
                    Bullet bullet = tempBullet.next();
                    if (bullet.getCollisionBound().intersect(asteroid.getCollisionBound())) {
                        tempBullet.remove();
                        bullet.destroy();
                        asteroid.health -= bullet.getDamage();
                        if (asteroid.health <= 0){
                            player.setScore(asteroid.getScore());
                            tempAsteriod.remove();
                            asteroid.destroy();
                        }
                    }
                }
            }
        }

        if (player.getScore() % 25 == 0 && fighterFactory.isEmpty() && player.getScore() != 0){
            Fighter tempFighter = new Fighter(context, screenX, screenY);
            tempFighter.genPosX();
            fighterFactory.add(tempFighter);
            player.setScore(1250);
        }

        if(player.getScore() % 200 == 0 && fighterFactory.getFighter().size() < 3 && player.getScore() != 0){
            fighterFactory.createFleet(context, screenX, screenY, 5);
        }

        fighterFactory.setIterator();
        fighterFactory.update();

        Iterator<Fighter> tempFighter = fighterFactory.getFighter().iterator();
        while (tempFighter.hasNext()){
            Fighter fighter = tempFighter.next();
            if(new Random().nextInt(100) % 100 == 0){
                enemyBulletFactory.add(new Bullet(context, screenY, fighter.getX(), fighter.getY(), BulletMode.SINGLE, fighter.getModel(), -1, -30, CollisionCategory.ENEMY));
            }
            if (fighter.getCollisionBound().intersect(player.getCollisionBound())){
                tempFighter.remove();
                fighter.destroy();
                player.hit(fighter.getDamage());
                onHealthChanged();
            }
            else {
                Iterator<Bullet> tempBullet = bulletFactory.getArray().iterator();
                while (tempBullet.hasNext()) {
                    Bullet bullet = tempBullet.next();
                    if (bullet.getCollisionBound().intersect(fighter.getCollisionBound())) {
                        tempBullet.remove();
                        bullet.destroy();
                        fighter.health -= bullet.getDamage();
                        if (fighter.health <= 0){
                            player.setScore(fighter.getScore());
                            tempFighter.remove();
                            fighter.destroy();
                        }
                    }
                }
            }
        }

        enemyBulletFactory.setIterator();
        enemyBulletFactory.update();

        if (player.getScore() % 200 == 0 && reloadFactory.isEmpty() && player.getScore() != 0){
            int f = (60/player.getFireRate()) + 1;
            if(f > 60){
                f = 60;
            }

            Reload tempReload = new Reload(context, screenX, screenY, f);
            tempReload.genPosX();
            reloadFactory.add(tempReload);
        }
        reloadFactory.setIterator();
        reloadFactory.update();
        Iterator<Reload> tempReload = reloadFactory.getArray().iterator();
        while (tempReload.hasNext()){
            Reload reload = tempReload.next();
            if (reload.getCollisionBound().intersect(player.getCollisionBound())){
                player.setFireRate(reload.getFireRate());

                tempReload.remove();
                reload.destroy();

            }
        }
        if (player.getHealth() <= 0){
            onDetachedFromWindow();
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        for (Heal heal: healFactory.getArray()) {
            canvas.drawBitmap(heal.getModel(), heal.posX, heal.posY, null);
            //canvas.drawRect(heal.collisionBound, paint);
        }

        for (Gun gun: gunFactory.getArray()) {
            canvas.drawBitmap(gun.getModel(), gun.posX, gun.posY, null);
            //canvas.drawRect(gun.collisionBound, paint);
        }

        for (Asteroid asteroid: asteroidFactory.getArray()) {
            canvas.drawBitmap(asteroid.getModel(), asteroid.posX, asteroid.posY, null);
            //canvas.drawRect(asteroid.collisionBound, paint);
        }

        for (Fighter fighter: fighterFactory.getFighter()){
            canvas.drawBitmap(fighter.getModel(), fighter.posX, fighter.posY, null);
        }

        for (Reload reload: reloadFactory.getArray()) {
            canvas.drawBitmap(reload.getModel(), reload.posX, reload.posY, null);
            //canvas.drawRect(asteroid.collisionBound, paint);
        }

        for (Bullet playerBullet : bulletFactory.getArray()) {
            canvas.drawBitmap(playerBullet.getModel(), playerBullet.getX(), playerBullet.getY(), null);
            //canvas.drawRect(playerBullet.getCollisionBound(), paint);
        }

        for (Bullet enemyBullet : enemyBulletFactory.getArray()) {
            canvas.drawBitmap(enemyBullet.getModel(), enemyBullet.getX(), enemyBullet.getY(), null);
            //canvas.drawRect(playerBullet.getCollisionBound(), paint);
        }

        canvas.drawBitmap(player.getModel(), player.getX(), player.getY(), null);
        //canvas.drawRect(player.getCollisionBound(), paint);

        Paint text = new Paint();
        //canvas.drawPaint(paint);
        text.setColor(Color.WHITE);
        text.setTextSize(56);
        canvas.drawText("Health: "+ String.valueOf(player.getHealth()), 10, 50, text);
        canvas.drawText("Score: " + String.valueOf(player.getScore()), 10, 110, text);
    }

    public List<Bullet> getPlayerBullets(){
        return bulletFactory.getArray();
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
        if (player.getHealth() <= 0){
            onGameOver();
        }
        gameState = GameState.CLOSED;
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
