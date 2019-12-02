package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.constraintlayout.solver.widgets.Rectangle;

import com.example.spaceshooter.R;

public class Player implements Ship{

    private int posX;
    private int posY;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;
    private boolean firing;
    private int fireRate;
    private int cooldown;

    private CollisionCategory collisionCategory;
    private Rectangle collisionBound;
    private Context context;
    private Bitmap model;
    private float movementSpeed;
    private double direction;
    private int health;
    private int maxHealth;
    private int score;

    public Player(Context context, int screenX, int screenY){
        init(context, screenX, screenY, 100, 10.0f, 0);
    }

    public Player(Context context, int screenX, int screenY, float movementSpeed, int padding){
        init(context, screenX, screenY, 100, movementSpeed, padding);
    }

    private void init(Context context, int screenX, int screenY, int health, float movementSpeed, int padding){
        this.collisionCategory = CollisionCategory.FRIENDLY;
        this.context = context;
        this.maxX = screenX - padding;
        this.maxY = screenY - padding;
        this.minX = 0 + padding;
        this.minY = 0 + padding;

        this.firing = false;
        this.fireRate = 60/1;
        this.cooldown = fireRate - 1;
        this.model = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        this.model = Bitmap.createScaledBitmap(model, model.getWidth() * 2/50, model.getHeight() * 2/50, false);

        this.movementSpeed = movementSpeed;
        this.direction = 0;

        this.posX = maxX/2;
        this.posY = maxY/2;

        this.health = health;
        this.maxHealth = health;
        this.score = 0;
    }

    public int getScore(){
        return score;
    }

    public int getHealth(){
        return health;
    }

    public Bitmap getModel() {
        return model;
    }

    @Override
    public boolean fire() {
        firing = !firing;
        /*if(!firing){
            cooldown = 0;
        }*/
        Log.d("Player", String.valueOf(firing));
        return firing;
    }

    public void setCooldown(){
        cooldown++;
        if(fireRate < cooldown){
            cooldown = 0;
        }
    }

    public int getCooldown(){
        return cooldown;
    }

    public void setFireRate(int fireRate){
        this.fireRate = 60/fireRate;
    }

    public int getFireRate(){
        return fireRate;
    }

    public boolean isFiring(){
        return firing;
    }

    @Override
    public void moveLeft(double direction) {
        if(direction > 1){
            this.direction = 1;
        }else if (direction < -1){
            this.direction = -1;
        }else {
            this.direction = direction;
        }
    }

    @Override
    public void moveSet() {

    }

    @Override
    public void setCollision(CollisionCategory collisionCategory) {
        this.collisionCategory = collisionCategory;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    @Override
    public CollisionCategory getCollision() {
        return this.collisionCategory;
    }

    @Override
    public void hit(int damage) {
        health -= damage;
        if(health <= 0){
            destroy();
        }
    }

    public void destroy(){
        if(health < 0){
            this.score = 0;
        }
        else {
        }
    }

    public void update(){
        if(direction != 0){
            if(posX <= maxX && direction <= 1 && direction > 0){
                this.posX += movementSpeed * direction;
            } else if (posX > maxX && direction == -1){
                this.posX += movementSpeed * direction;
            }
            if(posX >= minX && direction >= -1 && direction < 0){
                this.posX += movementSpeed * direction;
            } else if (posX < minX && direction == 1){
                this.posX += movementSpeed * direction;
            }
            //Log.d("Player", "PositionX: " + this.getX());
        }
    }

    public void updateSize(int width, int height){
        this.maxX = width - model.getWidth();
        this.maxY = height - model.getHeight();

        this.posX = maxX / 2;
        this.posY = maxY-10;
    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }

}
