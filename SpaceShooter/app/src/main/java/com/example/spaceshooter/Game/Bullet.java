package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

    private int posX;
    private int posY;
    private int posOffset;
    private double direction;
    private float speed;

    private Bitmap parentModel;
    private Bitmap model;
    private Rectangle collision;
    private CollisionCategory collisionCategories;

    public Bullet(Context context, int screenY, int posX, int posY, BulletMode bulletMode, Bitmap parentModel, double direction){
        init(context, screenY, posX, posY, 0, bulletMode, parentModel, direction, 10);
    }

    public Bullet(Context context, int screenY, int posX, int posY, BulletMode bulletMode, Bitmap parentModel, double direction, float speed){
        init(context, screenY, posX, posY, 0, bulletMode, parentModel, direction, speed);
    }

    public Bullet(Context context, int screenY, int posX, int posY, int posOffset, BulletMode bulletMode, Bitmap parentModel, double direction){
        init(context, screenY, posX, posY, posOffset, bulletMode, parentModel, direction, 10);
    }

    public Bullet(Context context, int screenY, int posX, int posY, int posOffset, BulletMode bulletMode, Bitmap parentModel, double direction, float speed){
        init(context, screenY, posX, posY, posOffset, bulletMode, parentModel, direction, speed);
    }

    private void init(Context context, int screenY, int posX, int posY, int posOffset, BulletMode bulletMode, Bitmap parentModel, double direction, float speed){
        this.context = context;
        this.height = screenY;
        this.angle = 90;
        if(bulletMode == BulletMode.SINGLE || bulletMode == BulletMode.DUAL){
            model = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        }else {
            model = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullete);
        }

        if(direction > 0){
            model = Rotate(model, -angle);
        }
        else if (direction < 0){
            model = Rotate(model, angle);
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
        moveForward(angle*2);

    }

    public void moveForward(float angle){
        this.posX += speed * sin(toRadians(angle));
        this.posY += speed * cos(toRadians(angle));
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

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }
}
