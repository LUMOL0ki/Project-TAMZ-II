package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;
import com.example.spaceshooter.R;

import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public abstract class DynamicObject {
    protected int posX;
    protected int posY;
    protected int maxX;
    protected int maxY;

    protected int rotationSpeed;
    protected float speed;
    protected double direction;
    protected float angle;
    protected int health;

    protected Bitmap model;
    protected Context context;
    protected Rect collisionBound;
    protected CollisionCategory collisionCategory;


    public void init(Context context, int screenX , int screenY, int posX, int posY, final int model){
        this.context = context;
        this.model = BitmapFactory.decodeResource(context.getResources(), model);
        this.model = Bitmap.createScaledBitmap(this.model, this.model.getWidth() * 5/25, this.model.getHeight() * 5/25, false);
        this.posX = posX;
        this.posY = posY;
        this.maxX = screenX;
        this.maxY = screenY;
        this.direction = -1;
        this.angle = 0;
        this.speed = new Random().nextInt(12) + 5;
        this.collisionCategory = CollisionCategory.ENEMY;
        //this.collisionBound = new Rect(posX, posY, this.model.getWidth(), this.model.getHeight());
    }

    public Bitmap getModel() {
        return model;
    }

    public Bitmap Rotate(Bitmap model, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(model, 0, 0, model.getWidth(), model.getHeight(), matrix, true);
    }

    public CollisionCategory getCollisionCategory() {
        return collisionCategory;
    }

    public float getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public Rect getCollisionBound() {
        return collisionBound;
    }

    public void setCollisionCategory(CollisionCategory collisionCategory) {
        this.collisionCategory = collisionCategory;
    }

    public void moveForward(float angle){
        this.posX += speed * sin(toRadians(angle));
        this.posY += speed * cos(toRadians(angle));
        collisionBound.offsetTo(posX, posY);
    }

    public void genPosX(){
        this.posX = new Random().nextInt(maxX - model.getWidth() - 20) + model.getWidth() + 20;
    }

    public void update(){
        moveForward(angle);
    }

    public void updateSize(int width, int height){
        this.maxX = width - model.getWidth();
        this.maxY = height + model.getHeight();
        this.posY = 0 - model.getHeight();
        this.collisionBound.offsetTo(posX, posY);
    }

    public void destroy(){
        if (direction > 0){
            posY = maxY;
        }else if (direction < 0){
            posY = 0 - model.getHeight();
        }else {
            Log.d("Dynamic object", "Help me I am lost.");
        }
        direction = 0;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }
}
