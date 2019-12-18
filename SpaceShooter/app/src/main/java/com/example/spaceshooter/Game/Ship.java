package com.example.spaceshooter.Game;

public interface Ship {
    boolean fire();
    void moveLeft(double direction);
    void moveSet();
    void setCollision(CollisionCategory collisionCategory);
    CollisionCategory getCollision();
    void hit(int damage);
    void destroy();
    int getX();
    int getY();
}

enum MoveSet{
    SINUS
}
