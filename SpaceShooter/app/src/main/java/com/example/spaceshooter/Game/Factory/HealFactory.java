package com.example.spaceshooter.Game.Factory;

import android.graphics.Rect;
import android.util.Log;

import com.example.spaceshooter.Game.Heal;

import java.util.ArrayList;
import java.util.Iterator;

public class HealFactory implements Factory {
    private ArrayList<Heal> heals = new ArrayList<Heal>();
    private Iterator<Heal> healIterator;
    private int maxY;

    public HealFactory(int screenY){
        this.maxY = screenY;
        heals.clear();
        healIterator = null;
    }

    public void add(Object object){
        heals.add( (Heal) object);
    }

    public ArrayList<Heal> getArray() {
        return heals;
    }

    public void setIterator(){
        healIterator = heals.iterator();
    }

    public void setIterator(Iterator<Heal> healIterator){
        this.healIterator = healIterator;
    }

    public Iterator<Heal> getIterator() {
        return healIterator;
    }

    public void update(){
        while (healIterator.hasNext()){
            Heal heal = healIterator.next();

            if(heal.getY() > maxY){
                healIterator.remove();
                heal.destroy();
            }
            else {
                heal.update();
            }
        }
    }

    public void checkCollisionWith(Rect rect){

        while (healIterator.hasNext()){
            Heal heal = healIterator.next();

            if (heal.getCollisionBound().intersect(rect)){
                healIterator.remove();
                heal.destroy();
                break;
            }
        }
    }

    public boolean isEmpty(){
        return heals.isEmpty();
    }
}
