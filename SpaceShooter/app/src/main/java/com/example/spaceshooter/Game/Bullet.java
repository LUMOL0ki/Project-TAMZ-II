package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

import androidx.constraintlayout.solver.widgets.Rectangle;

import com.example.spaceshooter.R;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class Bullet {
    private Context context;
    private int height;
    private float angle;
    private int damage;

    private int posX;
    private int posY;
    private int posOffset;
    private double direction;
    private float speed;

    private Bitmap parentModel;
    private Bitmap model;
    private Rect collisionBound;
    private CollisionCategory collisionCategories;

    public Bullet(Context context, int screenY, int posX, int posY, BulletMode bulletMode, Bitmap parentModel, double direction){
        init(context, screenY, posX, posY, 0, bulletMode, parentModel, 10, direction, 10);
    }

    public Bullet(Context context, int screenY, int posX, int posY, BulletMode bulletMode, Bitmap parentModel, double direction, float speed){
        init(context, screenY, posX, posY, 0, bulletMode, parentModel, 10, direction, speed);
    }

    public Bullet(Context context, int screenY, int posX, int posY, int posOffset, BulletMode bulletMode, Bitmap parentModel, double direction){
        init(context, screenY, posX, posY, posOffset, bulletMode, parentModel, 10, direction, 10);
    }

    public Bullet(Context context, int screenY, int posX, int posY, int posOffset, BulletMode bulletMode, Bitmap parentModel, int damage, double direction, float speed){
        init(context, screenY, posX, posY, posOffset, bulletMode, parentModel, damage, direction, speed);
    }

    private void init(Context context, int screenY, int posX, int posY, int posOffset, BulletMode bulletMode, Bitmap parentModel, int damage, double direction, float speed){
        this.context = context;
        this.height = screenY;
        this.angle = 0;
        this.damage = damage;
        if(bulletMode == BulletMode.SINGLE || bulletMode == BulletMode.DUAL){
            model = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_bullet);
        }else {
            model = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullete);
        }

        model = Bitmap.createScaledBitmap(this.model, model.getWidth() * 20/50, model.getHeight() * 20/50, false);

        if(direction > 0){
            model = Rotate(model, angle);
        }
        else if (direction < 0){
            model = Rotate(model, 180-angle);
            /*
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            model = Bitmap.createBitmap(model, 0, 0, model.getWidth(), model.getHeight(), matrix, true);*/
        }

        this.parentModel = parentModel;

        this.posX = posX + parentModel.getWidth()/2;
        this.posY = posY - parentModel.getHeight()/2;
        this.posOffset = posOffset;

        this.direction = direction;
        this.speed = speed;
        this.collisionBound = new Rect(0,0, model.getWidth(), model.getHeight());
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public Bitmap Rotate(Bitmap model, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(model, 0, 0, model.getWidth(), model.getHeight(), matrix, true);
    }

    public Bitmap getModel() {
        return model;
    }

    public void update(){
        //this.posY -= direction*speed;
        moveForward(angle);

    }

    public void moveForward(float angle){
        this.posX += speed * sin(toRadians(180+angle));
        this.posY += speed * cos(toRadians(180+angle));
        collisionBound.offsetTo(posX, posY);
    }

    public void destroy(){
        if (direction > 0){
            posY = height;
        }else if (direction < 0){
            posY = 0 - model.getHeight();
        }else {
            Log.d("Bullet", "Help me I am lost.");
        }
        direction = 0;
    }

    public Rect getCollisionBound() {
        return collisionBound;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }
}
