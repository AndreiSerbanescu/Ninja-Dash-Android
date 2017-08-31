package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Enemy {

    void attack();

    void die();

    void render(SpriteBatch sb);

    void update(float deltaTime);

    void dispose();
}
