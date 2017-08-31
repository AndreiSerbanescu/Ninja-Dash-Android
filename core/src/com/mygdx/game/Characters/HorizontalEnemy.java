package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Animation;

public class HorizontalEnemy implements Enemy {

    private Vector2 position;

    private Vector2 velocity;

    private TextureRegion textReg;

    private float width;
    private float height;


    private Animation attackAnimation;
    private Animation currentAnimation;


    public HorizontalEnemy(float x, float y) {
        position = new Vector2(x, y);

        //TOOD make height according to width
        width = MyGdxGame.WIDTH / 10;
        height = MyGdxGame.HEIGHT / 10;

        velocity = new Vector2(30, MyGdxGame.HEIGHT * 2 / 3);

        initTextReg();

        initAnimations();
    }
    public HorizontalEnemy() {
        this(0f, 0f);
    }

    @Override
    public void attack() {

    }

    private void initAnimations() {
        attackAnimation
                = makeAnimation("./enemy/horizontal/slimeWalk", "png", 2, 0.5f);

        currentAnimation = attackAnimation;
    }

    private Animation makeAnimation(String rootPath, String format, int frameCount, float cycleTime) {
        Texture[] textures = new Texture[frameCount];

        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Texture(rootPath + Integer.toString(i + 1) + "." + format);
        }

        return new Animation(textures, frameCount, cycleTime);
    }

    private void initTextReg() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(currentAnimation.getFrame(), position.x, position.y, width, height);
        sb.end();
    }

    @Override
    public void update(float deltaTime) {
        currentAnimation.update(deltaTime);

        velocity.scl(deltaTime);
        position.add(velocity);
        velocity.scl(1f / deltaTime);
    }

    @Override
    public void dispose() {
        attackAnimation.dispose();
    }
}
