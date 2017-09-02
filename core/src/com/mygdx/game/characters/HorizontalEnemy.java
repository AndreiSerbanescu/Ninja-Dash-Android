package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Animation;

import java.util.Random;

public class HorizontalEnemy implements Enemy {

    private Vector2 position;

    private Vector2 velocity;
    private int direction;
    private final int RIGHT = 1;
    private final int LEFT = -1;

    private Rope rope;

    private float width;
    private float height;


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
        startx = 0;
        endx = MyGdxGame.WIDTH;
    }

    private void init(float x, float y) {
        position = new Vector2(x, y);

        //TODO make height according to width
        width = MyGdxGame.WIDTH / 10;
        height = MyGdxGame.HEIGHT / 10;

        velocity = new Vector2(MyGdxGame.WIDTH / 4, 0);

        if ((new Random()).nextInt(2) == 0) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
        velocity.scl(direction);

        rope = new Rope();

        initAnimations();


        System.out.println(position + " " + direction);
    }

    public HorizontalEnemy() {
        this(0f, 0f);
    }

    @Override
    public void attack() {

    }

    private void initAnimations() {
        attackAnimation
                = makeAnimation("enemy/horizontal/slimeWalk", "png", 2, 0.5f);

        currentAnimation = attackAnimation;
    }

    private Animation makeAnimation(String rootPath, String format, int frameCount, float cycleTime) {
        Texture[] textures = new Texture[frameCount];

        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Texture(rootPath + Integer.toString(i + 1) + "." + format);
        }

        return new Animation(textures, frameCount, cycleTime);
    }

    @Override
    public void die() {
        dispose();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(currentAnimation.getFrame(), position.x, position.y, width, height);
        sb.end();

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
        } else if (position.x < startx) {
            position.x = startx;
            velocity.scl(-1);
        }
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
