package com.mygdx.game.characters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Animation;

import java.util.Random;

import javax.xml.bind.ValidationEventLocator;

public class DiagonalEnemy  extends AbstractEnemy {


    private Animation currentAnimation;
    private Animation deadAnimation;
    private Animation attackAnimation;

    private static Texture[] attackTextures;
    private static Texture[] deadTextures;

    private float width;
    private float height;

    private Vector2 velocity;
    private int direction;

    private Texture texture;

    private static final int LEFT = -1;
    private static final int  RIGHT = 1;



    public static void initTextures() {
        attackTextures = GameUtils
                .makeTextureArray("enemy/diagonal/move/skeleton-Move_", "png", 18);
        deadTextures = GameUtils
                .makeTextureArray("enemy/diagonal/die/skeleton-Death_", "png", 22);
    }


    public DiagonalEnemy(int side, float y) {
        super();

        Random random = new Random();

        float screenWidth = MyGdxGame.WIDTH - MyGdxGame.BORDERWIDTH * 2;
        int alpha = random.nextInt(50);

        float diagonalVelocity = 1f * MyGdxGame.WIDTH / 2;
        velocity = new Vector2(diagonalVelocity * (float)Math.cos(1d * alpha),
                diagonalVelocity * (float)Math.sin(1d * alpha));

        texture = new Texture("enemy/diagonal/image.png");

        Vector2 widthHeight = getNewWidthHeight(texture.getWidth(), texture.getHeight(), 5);
        width = widthHeight.x;
        height = widthHeight.y;


        float posx;
        if (side == 0) {
            posx = MyGdxGame.BORDERWIDTH;
            direction = RIGHT;
        } else {
            posx = MyGdxGame.WIDTH - MyGdxGame.BORDERWIDTH - width;
            direction = LEFT;
        }
        position = new Vector2(posx, y);

        initCollBox(position.x, position.y, width, height);
        initAnimations();
    }

    private void initAnimations() {
        attackAnimation = new Animation(attackTextures, attackTextures.length, 1f);

        deadAnimation = new Animation(deadTextures, deadTextures.length, 0.5f, false);

        currentAnimation = attackAnimation;
    }

    @Override
    public void attack() {

    }

    @Override
    public void die() {
        currentAnimation = deadAnimation;
        velocity = new Vector2(0, 0);
        isDead = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(currentAnimation.getFrame(), position.x, position.y, width, height);

        sb.end();
    }

    @Override
    public void update(float deltaTime) {


        velocity.scl(deltaTime);
        position.add(velocity);
        velocity.scl(1f / deltaTime);


        currentAnimation.update(deltaTime);
        updateCollBox();

        if (position.x > MyGdxGame.WIDTH - MyGdxGame.BORDERWIDTH - width) {
            position.x = MyGdxGame.WIDTH - MyGdxGame.BORDERWIDTH - width;
            velocity.scl(-1);
        }
        if (position.x < MyGdxGame.BORDERWIDTH) {
            position.x = MyGdxGame.BORDERWIDTH;
            velocity.scl(-1);
        }
    }

    @Override
    public void dispose() {
        deadAnimation.dispose();
        attackAnimation.dispose();
    }

    public static void disposeTextures() {
        for (Texture attackTexture : attackTextures) {
            attackTexture.dispose();
        }
        for (Texture deadTexture : deadTextures) {
            deadTexture.dispose();
        }
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }
}
