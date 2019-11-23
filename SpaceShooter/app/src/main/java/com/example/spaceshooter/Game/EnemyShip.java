package com.example.spaceshooter.Game;

public abstract class EnemyShip implements Ship {
    protected CollisionCategory collisionCategory;
    protected int health;
    protected int score;
    protected int posX;
    protected  int posY;

    @Override
    public void fire() {

    }

    @Override
    public void moveLeft(double direction) {

    }

    @Override
    public void setCollision(CollisionCategory collisionCategory) {
        this.collisionCategory = collisionCategory;
    }

    @Override
    public CollisionCategory getCollision() {
        return collisionCategory;
    }

    @Override
    public void hit(int damage) {
        health -= damage;
        if(health <= 0){
            destroy();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }
}
