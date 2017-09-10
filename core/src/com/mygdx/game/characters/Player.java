package com.mygdx.game.characters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Animation;


public class Player {

    private static final int LEFT = -1;
    private static final int RIGHT = 1;

    private Vector2 position;
    private Vector2 nonJumpPos;

    private boolean isDead = false;
    private boolean isProtected = true;

    private Vector2 velocity;

    private float jumpVelocityX = MyGdxGame.WIDTH * 2;

    private int orientation;
    private float margin = MyGdxGame.WIDTH / 10;

    private Rectangle collBox;


    private Animation runAnimation;
    private Animation currentAnimation;
    private Animation jumpAnimation;
    private Animation deadAnimation;

    private Rectangle ground1Left, ground1Right, ground2Left, ground2Right;
    private OrthographicCamera camera;

    private boolean isJumping = false;

    private static float playerUpVelocityY = MyGdxGame.HEIGHT / 5;

    private ProtectionOrb protectionOrb;

    private float width;
    private float height;

    public Player(Vector2 position,
                  Rectangle ground1Left, Rectangle ground1Right,
                  Rectangle ground2Left, Rectangle ground2Right,
                  OrthographicCamera camera) {

        initAnimations();
        updateWidthHeight();

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

        protectionOrb = new ProtectionOrb();
        //protText = new Texture("spr_shield.png");

    }


    private void initAnimations() {
        runAnimation = GameUtils
                .makeAnimation("ninjaAnimation/run/Run__00", "png", 9, 0.2f);
        jumpAnimation = GameUtils
                .makeAnimation("ninjaAnimation/attack/Glide_00", "png", 10, 0.2f);
        deadAnimation = GameUtils
                .makeAnimation("ninjaAnimation/dead/Dead__00", "png", 10, 0.2f, false);

        currentAnimation = jumpAnimation;
    }

    public Rectangle getCollBox() {
        return collBox;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
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

        if (isDead) {
            currentAnimation = deadAnimation;
        } else if (isGrounded()) {
            currentAnimation = runAnimation;
        } else {
            currentAnimation = jumpAnimation;
        }


        currentAnimation.update(deltaTime);

        updateWidthHeight();

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
            protectionOrb.render(sb);
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
        jumpAnimation.dispose();
    }

    public float getNonJumpPosY() {
        return nonJumpPos.y;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void receiveAttack(Enemy enemy) {
        if (isJumping && !enemy.isInvincible()) {
            enemy.die();
            return;
        }

        if (enemy.isInvincible()) {
            if (isProtected) {
                isProtected = false;
                enemy.die();
            } else {
                die();
            }
            return;
        }

        if (!isJumping) {
            if (isProtected) {
                isProtected = false;
                enemy.die();
            } else {
                die();
            }
        }
    }
    private void updateWidthHeight() {

        Texture currTexture = currentAnimation.getFrame().getTexture();
        width = MyGdxGame.WIDTH / 5;
        height = width * currTexture.getHeight() / currTexture.getWidth();
    }

    public class ProtectionOrb {
        private Texture texture;
        private float width;
        private float height;

        public ProtectionOrb() {
            texture = new Texture("spr_shield.png");
            width = Player.this.width * 3f;
            height = width;
        }

        public void render(SpriteBatch sb) {
            Vector2 pos = getPosition();
            sb.draw(texture, pos.x, pos.y, width, height);
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }

        public Vector2 getPosition() {
            Vector2 playerPos = position;
            float playerWidth = Player.this.width;
            float playerHeight = Player.this.height;

            Vector2 centre = new Vector2(playerPos.x + playerWidth / 2, playerPos.y + playerHeight / 2);
            Vector2 orbPos = new Vector2(centre.x - width / 2, centre.y - height / 2);
            return orbPos;
        }
    }
}