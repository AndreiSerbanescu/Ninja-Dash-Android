package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Animation;

public class VerticalEnemy extends AbstractEnemy {


    private Animation runAnimation;
    private Animation deadAnimation;
    private Animation currentAnimation;

    private Vector2 velocity;
    private int direction;
    private final int RIGHT = 1;
    private final int LEFT = -1;


    @Override
    public void attack() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public Vector2 getPosition() {
        return null;
    }
}
