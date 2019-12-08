package com.example.spaceshooter.Game.Factory;

import android.graphics.Rect;

import java.util.Iterator;

public interface Factory {
    void add(Object object);
    void setIterator();
    void update();
    void checkCollisionWith(Rect rect);
    boolean isEmpty();
}
