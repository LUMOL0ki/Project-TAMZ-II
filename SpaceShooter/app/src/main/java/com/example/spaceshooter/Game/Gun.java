package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.spaceshooter.R;

public class Gun extends DynamicObject {
    private BulletMode bulletMode;

    public Gun(Context context, int screenX, int screenY, BulletMode bulletMode){
        init(context, screenX, screenY, 0, -50, R.drawable.ammo);
        init(bulletMode);
    }

    private void init(BulletMode bulletMode){
        this.bulletMode = bulletMode;
        this.model = Bitmap.createScaledBitmap(this.model, this.model.getWidth() * 10/50, this.model.getHeight() * 10/50, false);
        this.model = Rotate(model, 45);
        this.collisionBound = new Rect(0, 0, this.model.getWidth(), this.model.getHeight());
    }

    public BulletMode getBulletMode() {
        return bulletMode;
    }
}
