package com.mygdx.game.Characters;

public interface Enemy {

    void attack();

    void die();

    void render();

    void update(float deltaTime);
}
