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

    private static Texture[] attackTextures;
    private static Texture[] deadTextures;

    private Vector2 velocity;
    private int direction;
    private final int RIGHT = 1;
    private final int LEFT = -1;


    private float width;
    private float height;

    public VerticalEnemy(int side, float y) {

        initAnimations();
        initWidthHeight();

        if (side == 0) {
            position = new Vector2(MyGdxGame.BORDERWIDTH, y);
            direction = LEFT;
        } else {
            position = new Vector2(MyGdxGame.WIDTH - MyGdxGame.BORDERWIDTH - width, y);
            direction = RIGHT;
        }

        velocity = new Vector2(0, - MyGdxGame.HEIGHT / 5);
        initCollBox(position.x, position.y, width, height);
    }

    public VerticalEnemy(float y) {
        this((new Random()).nextInt(2), y);
    }

    private void initWidthHeight() {
        Texture texture = currentAnimation.getFrame().getTexture();
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
        velocity.y = 0;
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


    private void initAnimations() {
        runAnimation = new Animation(attackTextures, attackTextures.length, 1f);
        deadAnimation = new Animation(deadTextures, deadTextures.length, 1f, false);

        currentAnimation = runAnimation;
    }

    public static void initTextures() {
        attackTextures = GameUtils
                .makeTextureArray("enemy/vertical/move/skeleton-Move_", "png", 18);
        deadTextures = GameUtils
                .makeTextureArray("enemy/vertical/die/skeleton-Suicide_", "png", 26);
    }

    public static void disposeTextures() {
        for (Texture attackTexture : attackTextures) {
            attackTexture.dispose();
        }
        for (Texture deadTexture : deadTextures) {
            deadTexture.dispose();
        }
    }
}
