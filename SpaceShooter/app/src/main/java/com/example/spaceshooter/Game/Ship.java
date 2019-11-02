package com.example.spaceshooter.Game;

public interface Ship {
    void fire();
    void moveLeft(double direction, float speed);
    void moveSet();
    void setCollision(CollisionCategory collisionCategory);
    CollisionCategory getCollision();
    void hit(int damage);
    void destroy();
    int getX();
    int getY();
}
