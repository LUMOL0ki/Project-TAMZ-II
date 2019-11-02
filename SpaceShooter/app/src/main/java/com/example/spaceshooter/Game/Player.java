package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.drawable.shapes.OvalShape;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class Player implements Ship{

    private int posX;
    private int posY;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;
    private CollisionCategory collisionCategory;
    private Rectangle collisionBound;
    private int health;
    private int score;

    public Player(){
        this.collisionCategory = CollisionCategory.FRIENDLY;
        this.health = 100;
        this.score = 0;
    }

    public Player(Context context){
        this.collisionCategory = CollisionCategory.FRIENDLY;
        this.health = 100;
        this.score = 0;
    }

    public int getScore(){
        return score;
    }

    public int getHealth(){
        return health;
    }

    @Override
    public void fire() {

    }

    @Override
    public void moveLeft(double direction, float speed) {

    }

    @Override
    public void moveSet() {

    }

    @Override
    public void setCollision(CollisionCategory collisionCategory) {
        this.collisionCategory = collisionCategory;
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
        //this.score = 0;
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
