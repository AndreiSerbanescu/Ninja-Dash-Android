package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface Enemy {

    void attack();

    void die();

    void render(SpriteBatch sb);

    void update(float deltaTime);

    void dispose();

    Vector2 getPosition();

    boolean collides(Rectangle rect);
}
