package com.example.spaceshooter.Game;

import android.content.Context;

public class Fighter extends EnemyShip {

    private Context context;

    public Fighter(){
        this.collisionCategory = CollisionCategory.ENEMY;
        this.health = 60;
        this.score = 100;
    }

    public Fighter(Context context){
        this.collisionCategory = CollisionCategory.ENEMY;
        this.health = 60;
        this.score = 100;
        this.context = context;
    }

    public Fighter(Context context, CollisionCategory collisionCategory){
        this.collisionCategory = collisionCategory;
        this.health = 60;
        this.score = 100;
        this.context = context;
    }

    @Override
    public void moveSet() {

    }
}
