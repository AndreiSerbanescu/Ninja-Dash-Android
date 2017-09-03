package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Animation;




//TODO remove magic numbers

public class Player {

    private static final int LEFT = -1;
    private static final int RIGHT = 1;

    private int width;
    private  int height;
    private Vector2 position;
    private Vector2 nonJumpPos;

    private boolean isDead = false;
    private boolean isProtected = true;
    private Texture protectionOrb;

    private Vector2 velocity;

    private float jumpVelocityX = 700;

    private int orientation;
    private int margin = 30;

    private Rectangle collBox;


    private Animation runAnimation;
    private Animation jumpAnimation;
    private Animation currentAnimation;
    private Animation slideAnimation;
    private Animation deadAnimation;
    private Animation deadAnimationStopped;

    private Rectangle ground1Left, ground1Right, ground2Left, ground2Right;
    private OrthographicCamera camera;

    private boolean isJumping = false;

    private static float playerUpVelocityY = MyGdxGame.HEIGHT / 5;

    public Player(int width, int height, Vector2 position,
                  Rectangle ground1Left, Rectangle ground1Right,
                  Rectangle ground2Left, Rectangle ground2Right,
                  OrthographicCamera camera) {

        initAnimations();
        this.width = width;
        this.height = height;
        this.position = position;
        nonJumpPos = position.cpy();

        orientation = RIGHT;
        velocity = new Vector2(0, playerUpVelocityY);

        collBox = new Rectangle(position.x, position.y, width, height);

        this.camera = camera;

        this.ground1Left = ground1Left;
        this.ground1Right = ground1Right;
        this.ground2Left = ground2Left;
        this.ground2Right = ground2Right;

        protectionOrb = new Texture("star.png");
    }


    private void initAnimations() {
        runAnimation
                = makeAnimation("ninjaAnimation/run/Run__00", 9, "png", 0.2f);
        jumpAnimation
                = makeAnimation("ninjaAnimation/jump/Jump__00", 9, "png", 0.2f);
        slideAnimation
                = makeAnimation("ninjaAnimation/slide/Slide__00", 9, "png", 0.2f);
        deadAnimation
                = makeAnimation("ninjaAnimation/dead/Dead__00", 9, "png", 0.2f);
        deadAnimationStopped
                = makeAnimation("ninjaAnimation/dead/Dead_stop", 1, "png", 1f);


        currentAnimation = slideAnimation;
    }

    public Rectangle getCollBox() {
        return collBox;
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

        if (isDead && deadAnimation.loopedOnce()) {
            currentAnimation = deadAnimationStopped;
        } else if (isDead && !deadAnimation.loopedOnce()) {
            currentAnimation = deadAnimation;
        } else if (isGrounded()) {
            currentAnimation = runAnimation;
        } else {
            currentAnimation = slideAnimation;
        }


        currentAnimation.update(deltaTime);

        velocity.x = jumpVelocityX;

        if (isRightOfCamera() || isGroundedRight()) {
            position.x = MyGdxGame.WIDTH - margin - width;
            if (velocity.x > 0) {
                velocity.x = 0;
            }
        }
        if (isLeftOfCamera() || isGroundedLeft()) {
            position.x = margin;
            if (velocity.x < 0) {
                velocity.x = 0;
            }
        }



        Vector2 velY = velocity.cpy();
        velocity.scl(deltaTime);
        velY.scl(deltaTime);

        position.add(velocity);
        velY.x = 0;
        nonJumpPos.add(velY);

        velocity.scl(1f / deltaTime);
        velY.scl(1f / deltaTime);


        if (isJumping) {
            position = getJumpPosition(position);
        }
        collBox.setPosition(position);


        if (isGrounded()) {
            isJumping = false;
        }
    }

    private Vector2 getJumpPosition(Vector2 position) {
        float alpha = -0.0035f;

        float variable = position.x;
        float offset = ground1Left.width;
        float endValue = MyGdxGame.WIDTH - ground1Left.width - width;

        return new Vector2(position.x,  nonJumpPos.y + (variable - offset) * (variable - endValue) * alpha);
    }
    public Texture getTexture() {
        return runAnimation.getFrame().getTexture();
    }

    public void jump() {

        if (isGrounded()) {
            jumpVelocityX = -jumpVelocityX;
            orientation = -orientation;
        }
        isJumping = true;
    }


    private boolean isGrounded() {
        return isGroundedLeft() || isGroundedRight();
    }

    private boolean isGroundedLeft() {
        return collBox.overlaps(ground1Left) || collBox.overlaps(ground2Left)
                || position.x == ground1Left.x + ground1Left.width
                || position.x == ground2Left.x + ground2Left.width;
    }

    private boolean isGroundedRight() {
        return collBox.overlaps(ground1Right) || collBox.overlaps(ground2Right)
                || position.x + width == ground1Right.x || position.x + width == ground2Right.x;
    }

    private boolean isOutOfCameraView() {
        return (position.x < camera.position.x - camera.viewportWidth / 2)
                || (position.x > camera.position.x + camera.viewportWidth / 2);
    }

    private boolean isInAir() {
        return !isGrounded() && !isOutOfCameraView();
    }

    private boolean isLeftOfCamera() {
        return position.x < camera.position.x - camera.viewportWidth / 2;
    }

    private boolean isRightOfCamera() {
        return position.x > camera.position.x + camera.viewportWidth / 2;
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
       if (isProtected) {
           sb.draw(protectionOrb, position.x, position.y, width, height);
       }
        
       sb.end();
    }

    public void kill(Enemy enemy) {
        enemy.die();
    }

    public void die() {
        currentAnimation = deadAnimation;
        isDead = true;
        velocity.y = 0;
    }

    public void dispose() {
        runAnimation.dispose();
        jumpAnimation.dispose();
        currentAnimation.dispose();
        slideAnimation.dispose();
    }

    public float getNonJumpPosY() {
        return nonJumpPos.y;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void receiveAttack(Enemy enemy) {
        if (isProtected) {
            isProtected = false;
            enemy.die();
        } else {
            die();
        }
    }
}