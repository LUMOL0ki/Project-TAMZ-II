package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.constraintlayout.solver.widgets.Rectangle;

import com.example.spaceshooter.R;

import java.util.List;

public class Bullet {
    private Context context;
    private int height;

    private int posX;
    private int posY;
    private int posOffset;
    private double direction;
    private float speed;

    private Bitmap parentModel;
    private Bitmap model;
    private Rectangle collision;
    private List<CollisionCategory> collisionCategories;

    public Bullet(Context context, int height, int posX, int posY, BulletMode bulletMode, Bitmap parentModel, double direction){
        init(context, height, posX, posY, 0, bulletMode, parentModel, direction, 10);
    }

    public Bullet(Context context, int height, int posX, int posY, BulletMode bulletMode, Bitmap parentModel, double direction, float speed){
        init(context, height, posX, posY, 0, bulletMode, parentModel, direction, speed);
    }

    public Bullet(Context context, int height, int posX, int posY, int posOffset, BulletMode bulletMode, Bitmap parentModel, double direction){
        init(context, height, posX, posY, posOffset, bulletMode, parentModel, direction, 10);
    }

    public Bullet(Context context, int height, int posX, int posY, int posOffset, BulletMode bulletMode, Bitmap parentModel, double direction, float speed){
        init(context, height, posX, posY, posOffset, bulletMode, parentModel, direction, speed);
    }

    private void init(Context context, int height, int posX, int posY, int posOffset, BulletMode bulletMode, Bitmap parentModel, double direction, float speed){
        this.context = context;
        this.height = height;

        this.posX = posX;
        this.posY = posY;
        this.posOffset = posOffset;

        this.parentModel = parentModel;

        if(bulletMode == BulletMode.SINGLE || bulletMode == BulletMode.DUAL){
            model = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        }else {
            model = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullete);
        }

        this.posX = posX + parentModel.getWidth() / 2;
        this.posY = posY + parentModel.getHeight();
    }

    public void draw(){
        if (direction != 0){
            direction += direction*speed;
        }
    }

    public void destroy(){
        if (direction > 0){
            posY = height;
        }else if (direction < 0){
            posY = 0 - model.getHeight();
        }else {
            Log.d("Bullet", "Help me I am lost.");
        }
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
