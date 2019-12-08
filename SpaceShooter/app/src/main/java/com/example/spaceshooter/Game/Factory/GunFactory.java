package com.example.spaceshooter.Game.Factory;

import android.graphics.Rect;
import android.util.Log;

import com.example.spaceshooter.Game.Gun;

import java.util.ArrayList;
import java.util.Iterator;

public class GunFactory implements Factory {
    private ArrayList<Gun> guns = new ArrayList<>();
    private Iterator<Gun> gunIterator;
    private int maxY;

    public GunFactory(int screenY){
        this.maxY = screenY;
        guns.clear();
        gunIterator = null;
    }

    public void add(Object object){
        guns.add( (Gun) object);
    }

    public ArrayList<Gun> getArray() {
        return guns;
    }

    public void setIterator(){
        gunIterator = guns.iterator();
    }

    public void setIterator(Iterator<Gun> gunIterator){
        this.gunIterator = getIterator();
    }

    public Iterator<Gun> getIterator() {
        return gunIterator;
    }

    public void update(){
        while (gunIterator.hasNext()){
            Gun gun = gunIterator.next();

            if(gun.getY() > maxY){
                gunIterator.remove();
                gun.destroy();
            }
            else {
                gun.update();
            }
        }
    }

    public void checkCollisionWith(Rect rect){
        while (gunIterator.hasNext()){
            Gun gun = gunIterator.next();

            if (gun.getCollisionBound().intersect(rect)){
                gunIterator.remove();
                gun.destroy();
                break;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return guns.isEmpty();
    }
}
