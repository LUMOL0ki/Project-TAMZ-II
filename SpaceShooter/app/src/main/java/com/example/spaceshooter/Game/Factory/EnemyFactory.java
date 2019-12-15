package com.example.spaceshooter.Game.Factory;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;

import com.example.spaceshooter.Game.Fighter;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.sin;

public class EnemyFactory implements Factory{
    private ArrayList<Fighter> fighters;
    private Iterator<Fighter> fighterIterator;
    private EnemyType enemyType;
    private int maxY;

    public EnemyFactory(EnemyType enemyType, int screenY){
        this.maxY = screenY;
        //fighters.clear();
        //fighterIterator = null;
        this.enemyType = enemyType;
        switch (this.enemyType){
            case BOSS:
                Log.d("", "not implemented");
                break;
            case FIGTHER:
                fighters = new ArrayList<>();
                fighterIterator = null;
                break;
            default:
                break;
        }
    }
    @Override
    public void add(Object object) {
        switch (this.enemyType){
            case BOSS:
                Log.d("", "not implemented");
                break;
            case FIGTHER:
                fighters.add( (Fighter) object);
                break;
            default:
                break;
        }
    }

    public ArrayList<Fighter> getFighter(){
        return fighters;
    }

    @Override
    public void setIterator() {
        switch (this.enemyType){
            case BOSS:
                Log.d("", "not implemented");
                break;
            case FIGTHER:
                fighterIterator = fighters.iterator();
                break;
            default:
                break;
        }
    }

    public void createFleet(Context context, int screenX, int screenY,int size){
        for(int i = 0; i < size; i++){
            fighters.add(new Fighter(context, screenX, screenY, 200*i, -300*i));
        }
    }

    @Override
    public void update() {
        switch (this.enemyType){
            case BOSS:
                Log.d("", "not implemented");
                break;
            case FIGTHER:
                while (fighterIterator.hasNext()){
                    Fighter fighter = fighterIterator.next();

                    if (fighter.getY() > maxY){
                        fighterIterator.remove();
                        fighter.destroy();
                    }
                    else {
                        fighter.update();
                    }
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void checkCollisionWith(Rect rect) {

    }

    @Override
    public boolean isEmpty() {
        switch (this.enemyType){
            case BOSS:
                Log.d("", "not implemented");
                return true;
            case FIGTHER:
                fighterIterator = fighters.iterator();
                return fighters.isEmpty();
            default:
                return true;
        }
    }
}
