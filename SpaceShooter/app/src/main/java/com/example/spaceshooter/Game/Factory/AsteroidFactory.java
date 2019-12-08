package com.example.spaceshooter.Game.Factory;

import android.graphics.Rect;

import com.example.spaceshooter.Game.Asteroid;

import java.util.ArrayList;
import java.util.Iterator;

public class AsteroidFactory implements Factory {
    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private Iterator<Asteroid> asteroidIterator;
    private int maxY;

    public AsteroidFactory(int screenY){
        this.maxY = screenY;
        asteroids.clear();
        asteroidIterator = null;
    }

    public void add(Object object){
        asteroids.add( (Asteroid) object);
    }

    public ArrayList<Asteroid> getArray() {
        return asteroids;
    }

    public void setIterator(){
        asteroidIterator = asteroids.iterator();
    }

    public Iterator<Asteroid> getIterator() {
        return asteroidIterator;
    }

    public void update(){
        while (asteroidIterator.hasNext()){
            Asteroid asteroid = asteroidIterator.next();

            if(asteroid.getY() > maxY){
                asteroidIterator.remove();
                asteroid.destroy();
            }
            else {
                asteroid.update();
            }
        }
    }

    public void checkCollisionWith(Rect rect){
        while (asteroidIterator.hasNext()){
            Asteroid asteroid = asteroidIterator.next();

            if (asteroid.getCollisionBound().intersect(rect)){
                asteroidIterator.remove();
                asteroid.destroy();
                break;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return asteroids.isEmpty();
    }
}
