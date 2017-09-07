package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.sprites.Animation;

public class DiagonalEnemy  extends AbstractEnemy {


    private Animation currentAnimation;
    private Animation deadAnimation;
    private Animation attackAnimation;

    private float width;
    private float height;

    private Vector2 velocity;
    private int direction;

    private static final int LEFT = -1;
    private static final int  RIGHT = 1;

    @Override
    public void attack() {

    }

    @Override
    public void die() {
        currentAnimation = deadAnimation;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(currentAnimation.getFrame(), position.x, position.y, width, height);

        sb.end();
    }

    @Override
    public void update(float deltaTime) {
        velocity.scl(direction * deltaTime);
        position.add(velocity);
        velocity.scl(1f / deltaTime);
    }

    @Override
    public void dispose() {
        deadAnimation.dispose();
        attackAnimation.dispose();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }
}
