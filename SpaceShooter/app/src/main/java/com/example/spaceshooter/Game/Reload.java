package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.spaceshooter.R;

import java.util.Random;

public class Reload extends DynamicObject {
    private int fireRate;


    public Reload(Context context, int screenX, int screenY){
        init(context, screenX, screenY, 0, -50, R.drawable.reload);
        this.model = Bitmap.createScaledBitmap(this.model, this.model.getWidth(), this.model.getHeight(), false);
        int size = new Random().nextInt(10)+40;
        this.model = Bitmap.createScaledBitmap(this.model, this.model.getWidth() * size/50, this.model.getHeight() * size/50, false);
        this.model = Rotate(model, new Random().nextInt(90));
        this.fireRate = 5;
        this.angle = new Random().nextInt(90)-45;
        this.collisionBound = new Rect(posX, posY, this.model.getWidth(), this.model.getHeight());
    }

    public int getFireRate() {
        return fireRate;
    }
}
