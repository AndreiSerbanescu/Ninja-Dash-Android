package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Animation;

import java.util.Random;

public class VerticalEnemy extends AbstractEnemy {


    private Animation runAnimation;
    private Animation deadAnimation;
    private Animation currentAnimation;

    private Vector2 velocity;
    private int direction;
    private final int RIGHT = 1;
    private final int LEFT = -1;


    private float width;
    private float height;

    public VerticalEnemy(int side, float y) {
        initWidthHeight();

        if (side == 0) {
            position = new Vector2(MyGdxGame.BORDERWIDTH, y);
            direction = LEFT;
        } else {
            position = new Vector2(MyGdxGame.WIDTH - MyGdxGame.BORDERWIDTH - width, y);
            direction = RIGHT;
        }

        velocity = new Vector2(0, - MyGdxGame.HEIGHT / 10);
        initCollBox(position.x, position.y, width, height);
        initAnimations();
    }

    public VerticalEnemy(float y) {
        this((new Random()).nextInt(2), y);
    }

    private void initWidthHeight() {
        Texture texture = new Texture("enemy/vertical/p1_walk00.png");
        Vector2 widthHeight = getNewWidthHeight(texture.getWidth(), texture.getHeight(), 7);
        width = widthHeight.x;
        height = widthHeight.y;
    }

    @Override
    public void attack() {

    }

    @Override
    public void die() {
        currentAnimation = deadAnimation;
        isDead = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (direction == RIGHT) {
            currentAnimation.getFrame().flip(true, false);
        }


        sb.begin();
        sb.draw(currentAnimation.getFrame(), position.x, position.y, width, height);
        sb.end();

        if (direction == RIGHT) {
            currentAnimation.getFrame().flip(true, false);
        }
    }

    @Override
    public void update(float deltaTime) {
        velocity.scl(deltaTime);
        position.add(velocity);
        velocity.scl(1f / deltaTime);

        updateCollBox();
        currentAnimation.update(deltaTime);
    }

    @Override
    public void dispose() {
        deadAnimation.dispose();
        runAnimation.dispose();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    private void initAnimations() {
        runAnimation = GameUtils
                .makeAnimation("enemy/vertical/p1_walk0", "png", 9, 0.2f);
        deadAnimation = GameUtils
                .makeAnimation("enemy/vertical/p1_hurt", "png", 1, 1f);

        currentAnimation = runAnimation;
    }
}
