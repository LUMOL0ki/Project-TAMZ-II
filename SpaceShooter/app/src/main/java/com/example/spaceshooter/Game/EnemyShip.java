package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class EnemyShip implements Ship {
    protected CollisionCategory collisionCategory;
    protected int health;
    protected int damage;
    protected int score;
    protected int maxX;
    protected int maxY;
    protected int posX;
    protected int posY;
    protected boolean firing;
    protected int speed;
    protected int angle;

    protected Bitmap model;
    protected Context context;
    protected Rect collisionBound;

    @Override
    public boolean fire() {
        return firing;
    }

    @Override
    public void moveLeft(double direction) {

    }

    @Override
    public void setCollision(CollisionCategory collisionCategory) {
        this.collisionCategory = collisionCategory;
    }

    @Override
    public CollisionCategory getCollision() {
        return collisionCategory;
    }

    public Rect getCollisionBound(){
        return this.collisionBound;
    }

    public Bitmap getModel(){
        return model;
    }

    @Override
    public void hit(int damage) {
        health -= damage;
        if(health <= 0){
            destroy();
        }
    }

    @Override
    public void destroy() {

    }

    public void setAngle(int angle) {
        this.angle = angle;
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
