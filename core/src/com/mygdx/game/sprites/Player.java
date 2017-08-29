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
    private float gravityX = 15;
    private int orientation;

    private Animation runAnimation;
    //private Animation jumpAnimation;

    public Player(int width, int height, Vector2 position) {

        initAnimation();
        this.width = width;
        this.height = height;
        this.position = position;
        orientation = RIGHT;
        velocity = new Vector2(0, 0);
    }

    public Player(int width, int height, int x, int y) {
        this(width, height, new Vector2(x, y));
    }

    private void initAnimation() {
        String pathRoot = "ninjaAnimation/run/Run__00";
        String path;
        int runCount = 9;
        Texture[] textures = new Texture[runCount];


        for (int i = 0; i < runCount; i++) {
            path = pathRoot + Integer.toString(i) + ".png";
            textures[i] = new Texture(path);
        }

        runAnimation = new Animation(textures, runCount, 0.2f);
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
        runAnimation.update(deltaTime);

        int margin = 10;

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
        gravityX = -gravityX;
        orientation = -orientation;
    }

    public void render(SpriteBatch sb) {
        TextureRegion currentFrame = runAnimation.getFrame();

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