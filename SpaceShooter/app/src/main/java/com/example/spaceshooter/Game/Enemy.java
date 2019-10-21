package com.example.spaceshooter.Game;

public interface Enemy {
    void Fire();
    void Move();
    void Collision();
    void Hit();
    void Destroy();

}
