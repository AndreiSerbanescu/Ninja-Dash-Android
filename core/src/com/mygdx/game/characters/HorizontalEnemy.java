package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Animation;

import java.util.Random;

public class HorizontalEnemy extends AbstractEnemy {

    private Vector2 velocity;
    private int direction;
    private final int RIGHT = 1;
    private final int LEFT = -1;


    private Rope rope;

    private float width;
    private float height;


    private Animation deadAnimation;
    private Animation attackAnimation;
    private Animation currentAnimation;

    private float startx;
    private float endx;



    public HorizontalEnemy(float x, float y) {
        preInit();
        init(x, y);
    }
    public HorizontalEnemy(float y) {
        preInit();
        init((float)(new Random()).nextDouble() * (endx - startx), y);
    }

    private void preInit() {

        initAnimations();

        Texture enemyTexture = currentAnimation.getFrame().getTexture();
        Vector2 widthHeight = getNewWidthHeight(enemyTexture.getWidth(), enemyTexture.getHeight(), 10);
        width = widthHeight.x;
        height = widthHeight.y;

        startx = MyGdxGame.BORDERWIDTH;
        endx = MyGdxGame.WIDTH - MyGdxGame.BORDERWIDTH - width;
    }

    private void init(float x, float y) {
        position = new Vector2(x, y);;

        initVelocity();

        if ((new Random()).nextInt(2) == 0) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        velocity.scl(direction);

        rope = new Rope();

        initCollBox(position.x, position.y, width, height);

    }

    public HorizontalEnemy() {
        this(0f, 0f);
    }

    private void initVelocity() {
        float lowerBound = MyGdxGame.WIDTH / 4;
        float upperBound = lowerBound * 3;

        float randomOffset = (float)(new Random()).nextDouble();

        velocity = new Vector2(lowerBound + randomOffset * (upperBound - lowerBound), 0);
    }

    @Override
    public void attack() {

    }

    private void initAnimations() {
        attackAnimation = GameUtils.makeAnimation("enemy/horizontal/snail/snailWalk",
                "png", 2, 0.2f);

        deadAnimation = GameUtils.makeAnimation("enemy/horizontal/snail/snailShell",
                "png", 2, 0.2f);

        currentAnimation = attackAnimation;
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

        rope.render(sb);
    }

    @Override
    public void update(float deltaTime) {
        currentAnimation.update(deltaTime);

        velocity.scl(deltaTime);
        position.add(velocity);
        velocity.scl(1f / deltaTime);

        if (position.x >= endx) {
            position.x = endx;
            velocity.scl(-1);
            flipDirection();
        } else if (position.x < startx) {
            position.x = startx;
            velocity.scl(-1);
            flipDirection();
        }

        updateCollBox();
    }

    private void flipDirection() {
        direction *= (-1);
    }

    @Override
    public void dispose() {
        attackAnimation.dispose();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    private class Rope {
        private Vector2 position;
        private Texture texture;
        private float width;
        private float height;

        private Rope() {
            width = MyGdxGame.WIDTH;
            height = MyGdxGame.HEIGHT / 150;
            position = new Vector2(0, 0);
            updatePos();
            texture = new Texture("tiles/stoneCenter.png");
        }


        private void update() {
            updatePos();
        }

        private void updatePos() {
            position.y = HorizontalEnemy.this.position.y;
            position.y -= height;
        }

        private void render(SpriteBatch sb) {
            sb.begin();

            sb.draw(texture, position.x, position.y, width, height);

            sb.end();
        }
    }

}
