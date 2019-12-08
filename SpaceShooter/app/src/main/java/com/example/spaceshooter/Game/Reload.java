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
        init();
    }

    private void init(){
        this.fireRate = 5;
        //this.model = Bitmap.createScaledBitmap(this.model, this.model.getWidth() * 22/25, this.model.getHeight() * 22/25, false);
        this.collisionBound = new Rect(0, 0, this.model.getWidth(), this.model.getHeight());
    }

    public int getFireRate() {
        return fireRate;
    }
}
