package com.example.spaceshooter.Game;

public class Game {
    public interface HealthListener {
        void onhealthChanged();

    }

    public interface ScoreListener {
        void onScoreChanged();
    }

    public interface StringListener {
        void onStringChanged();
    }

    public  interface GameListener{
        void onGameOver();
    }
}
