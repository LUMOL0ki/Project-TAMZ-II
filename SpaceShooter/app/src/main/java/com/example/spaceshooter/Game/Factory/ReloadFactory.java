package com.example.spaceshooter.Game.Factory;

import android.graphics.Rect;

import com.example.spaceshooter.Game.Reload;

import java.util.ArrayList;
import java.util.Iterator;

public class ReloadFactory implements Factory {
    private ArrayList<Reload> reloads = new ArrayList<Reload>();
    private Iterator<Reload> reloadIterator;
    private int maxY;

    public ReloadFactory(int screenY){
        this.maxY = screenY;
        reloads.clear();
        reloadIterator = null;
    }

    @Override
    public void add(Object object) {
        reloads.add((Reload) object);
    }

    public ArrayList<Reload> getArray() {
        return reloads;
    }

    @Override
    public void setIterator() {
        reloadIterator = reloads.iterator();
    }

    public Iterator<Reload> getIterator() {
        return reloadIterator;
    }

    @Override
    public void update() {
        while (reloadIterator.hasNext()){
            Reload reload = reloadIterator.next();

            if(reload.getY() > maxY){
                reloadIterator.remove();
                reload.destroy();
            }
            else {
                reload.update();
            }
        }
    }

    @Override
    public void checkCollisionWith(Rect rect) {
        while (reloadIterator.hasNext()){
            Reload reload = reloadIterator.next();

            if (reload.getCollisionBound().intersect(rect)){
                reloadIterator.remove();
                reload.destroy();
                break;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return reloads.isEmpty();
    }
}
