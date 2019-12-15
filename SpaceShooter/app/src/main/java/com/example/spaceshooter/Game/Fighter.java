package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.spaceshooter.R;

import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class Fighter extends EnemyShip {

    public Fighter(Context context, int screenX, int screenY){
        init(context, screenX, screenY, 30, 25, 50);
    }

    public Fighter(Context context, int screenX, int screenY, int posX, int posY){
        init(context, screenX, screenY, 30, 25, 50);
        this.posX = posX;
        this.posY = posY - model.getHeight();
    }

    private void init(Context context, int screenX, int screenY, int health, int damage, int score){
        this.collisionCategory = CollisionCategory.ENEMY;
        this.context = context;
        this.score = score;
        this.damage = damage;
        this.health = health;
        this.speed = 10;
        this.angle = 0;

        this.maxX = screenX;
        this.maxY = screenY;
        this.model = BitmapFactory.decodeResource(context.getResources(), R.drawable.fighter);
        this.model = Bitmap.createScaledBitmap(this.model, this.model.getWidth() * 12/25, this.model.getHeight() * 12/25, false);
        this.collisionBound = new Rect(0, 0, this.model.getWidth(), this.model.getHeight());
        this.posY = -model.getHeight();
    }

    @Override
    public void moveSet() {

    }

    public void moveForward(float angle){
        this.posX += speed * sin(toRadians(angle));
        this.posY += speed * cos(toRadians(angle));
        collisionBound.offsetTo(posX, posY);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void update(){
        moveForward(angle);
    }

    public void genPosX(){
        this.posX = new Random().nextInt(maxX - model.getWidth()*2) + model.getWidth()/2;
    }
}
