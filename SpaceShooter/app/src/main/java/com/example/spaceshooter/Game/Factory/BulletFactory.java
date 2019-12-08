package com.example.spaceshooter.Game.Factory;

import android.graphics.Rect;

import com.example.spaceshooter.Game.Bullet;

import java.util.ArrayList;
import java.util.Iterator;

public class BulletFactory implements Factory {
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Iterator<Bullet> bulletIterator;
    private int maxY;

    public BulletFactory(int screenY){
        this.maxY = screenY;
        bullets.clear();
        bulletIterator = null;
    }

    public void add(Object object){
        bullets.add( (Bullet) object);
    }

    public ArrayList<Bullet> getArray() {
        return bullets;
    }

    public void setIterator(){
        bulletIterator = bullets.iterator();
    }

    public Iterator<Bullet> getIterator() {
        return bulletIterator;
    }

    public void update(){
        while (bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();

            if(bullet.getY() < 0 || bullet.getY() > maxY){
                bulletIterator.remove();
                bullet.destroy();
            }
            else {
                bullet.update();
            }
        }
    }

    public void checkCollisionWith(Rect rect){
        while (bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();

            if (bullet.getCollisionBound().intersect(rect)){
                bulletIterator.remove();
                bullet.destroy();
                break;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return bullets.isEmpty();
    }
}
