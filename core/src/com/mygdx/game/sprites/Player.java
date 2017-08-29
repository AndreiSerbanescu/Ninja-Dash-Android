package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

public class Player {

    private static final int LEFT = -1;
    private static final int RIGHT = 1;

    private int width;
    private  int height;
    private Vector2 position;
    private Vector2 velocity;
    private float gravityX = 50;
    private int orientation;
    private int margin = 10;

    private Animation runAnimation;
    private Animation jumpAnimation;
    private Animation currentAnimation;
    private Animation slideAnimation;

    public Player(int width, int height, Vector2 position) {

        initAnimations();
        this.width = width;
        this.height = height;
        this.position = position;
        orientation = RIGHT;
        velocity = new Vector2(0, 0);
    }

    public Player(int width, int height, int x, int y) {
        this(width, height, new Vector2(x, y));
    }


    private void initAnimations() {
        runAnimation
                = makeAnimation("ninjaAnimation/run/Run__00", 9, "png", 0.2f);
        jumpAnimation
                = makeAnimation("ninjaAnimation/jump/Jump__00", 9, "png", 0.2f);
        slideAnimation
                = makeAnimation("ninjaAnimation/slide/Slide__00", 9, "png", 0.2f);

        currentAnimation = slideAnimation;
    }

    private Animation makeAnimation(String pathRoot, int count, String textureFormat, float cycleTime) {
        String path;

        Texture[] textures = new Texture[count];

        for (int i = 0; i < count; i++) {
            path = pathRoot + Integer.toString(i) + "." + textureFormat;
            textures[i] = new Texture(path);
        }


        return new Animation(textures, count, cycleTime);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float deltaTime) {
        if (isGrounded()) {
            currentAnimation = runAnimation;
        } else {
            currentAnimation = slideAnimation;
        }


        currentAnimation.update(deltaTime);

        velocity.add(gravityX, 0);


        if (position.x + width + margin > MyGdxGame.WIDTH) {
            position.x = MyGdxGame.WIDTH - margin - width;
            if (velocity.x > 0) {
                velocity.x = 0;
            }
        }
        if (position.x - margin < 0) {
            position.x = margin;
            if (velocity.x < 0) {
                velocity.x = 0;
            }
        }

        System.out.println(position.x + " " + margin + " " + MyGdxGame.WIDTH);

        velocity.scl(deltaTime);

        position.add(velocity);

        velocity.scl(1f / deltaTime);
    }

    public Texture getTexture() {
        return runAnimation.getFrame().getTexture();
    }

    public void jump() {
        if (isGrounded()) {
            gravityX = -gravityX;
            orientation = -orientation;
        }
    }

    private boolean isGrounded() {
        int buffer = 5;
        return ((margin - buffer <= position.x)
                    && (position.x <= margin + buffer))
                ||
                ((position.x >= MyGdxGame.WIDTH - margin - width - buffer)
                        && (position.x <= MyGdxGame.WIDTH - margin - width + buffer));
    }

    public void render(SpriteBatch sb) {
        TextureRegion currentFrame = currentAnimation.getFrame();

       if (orientation == LEFT) {
            currentFrame.flip(true, false);
        }

        sb.begin();
        sb.draw(currentFrame, position.x, position.y, width, height);

        if (orientation == LEFT) {
            currentFrame.flip(true, false);
        }

        sb.end();
    }
}