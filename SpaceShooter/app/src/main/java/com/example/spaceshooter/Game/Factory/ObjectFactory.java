package com.example.spaceshooter.Game.Factory;

import android.graphics.Rect;

import com.example.spaceshooter.Game.Bullet;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class ObjectFactory implements Factory {
    private ArrayList<Object> objects = new ArrayList<>();
    private Iterator<Object> objectIterator;
    private int maxY;

    public ObjectFactory(int screenY){
        this.maxY =screenY;
        objects.clear();
        objectIterator = null;
    }

    @Override
    public void add(Object object) {
        objects.add(object);
    }

    @Override
    public void setIterator() {
        objectIterator = objects.iterator();
    }

    @Override
    public void update() {
        while (objectIterator.hasNext()){
            //Object object = objectIterator.next();
        }
    }

    @Override
    public void checkCollisionWith(Rect rect) {

    }

    @Override
    public boolean isEmpty() {
        return objects.isEmpty();
    }
}
