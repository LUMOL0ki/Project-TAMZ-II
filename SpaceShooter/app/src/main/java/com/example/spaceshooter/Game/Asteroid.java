package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.spaceshooter.R;

import java.util.Random;

public class Asteroid extends DynamicObject {
    private int damage;

    public Asteroid(Context context, int screenX, int screenY){
        init(context, screenX, screenY, 0, -50, R.drawable.asteroid);
        init();
    }

    private void init(){
        int size = new Random().nextInt(10)+40;
        this.model = Bitmap.createScaledBitmap(this.model, this.model.getWidth(), this.model.getHeight(), false);
        this.model = Bitmap.createScaledBitmap(this.model, this.model.getWidth() * size/50, this.model.getHeight() * size/50, false);
        this.health = 5+size/3;
        this.model = Rotate(model, new Random().nextInt(60));
        this.collisionBound = new Rect(0, 0, this.model.getWidth(), this.model.getHeight());
        this.damage = 5 + (int)speed;
        this.angle = new Random().nextInt(60)-30;
    }

    public int getDamage() {
        return damage;
    }
}
