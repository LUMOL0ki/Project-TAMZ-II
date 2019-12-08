package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import com.example.spaceshooter.R;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class Heal extends DynamicObject {
    private int heal;

    public Heal(Context context){
        init(context, 0, 0, 0, -50, R.drawable.heal);
        init();
    }

    public Heal(Context context, int posX){
        init(context, 0, 0, posX, -50, R.drawable.heal);
        init();
    }

    public Heal(Context context, int screenX , int screenY){
        init(context, screenX, screenY, 0, -50, R.drawable.heal);
        init();
    }

    public Heal(Context context, int screenX , int screenY, int posX){
        init(context, screenX, screenY, posX, 50, R.drawable.heal);
        init();
    }

    public Heal(Context context, int screenX , int screenY,int posX, int heal){
        init(context, screenX, screenY, posX, -10, R.drawable.heal);
        init();
    }

    private void init(){
        this.heal = 10;
        this.model = Bitmap.createScaledBitmap(this.model, this.model.getWidth() * 18/25, this.model.getHeight() * 18/25, false);
        this.collisionBound = new Rect(0, 0, this.model.getWidth(), this.model.getHeight());
    }

    public int getHeal() {
        return heal;
    }
/*
    @Override
    public void moveForward(float angle) {
        super.moveForward(angle);
        this.posX += speed * sin(toRadians(angle));
        this.posY += speed * cos(toRadians(angle));
        Log.d("Heal", String.valueOf(posY));

    }

    @Override
    public void update() {
        super.update();
        if (posY < maxY){
            moveForward(angle);
        }
    }*/
}
