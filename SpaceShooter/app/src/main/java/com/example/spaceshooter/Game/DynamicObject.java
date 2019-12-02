package com.example.spaceshooter.Game;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.constraintlayout.solver.widgets.Rectangle;

public abstract class DynamicObject {
    private int posX;
    private int posY;
    private int maxX;
    private int maxY;

    private int rotationSpeed;
    private float speed;
    private double direction;
    private int health;

    private Bitmap model;
    private Context context;
    private Rectangle collisionBound;
    private CollisionCategory collisionCategory;
}
