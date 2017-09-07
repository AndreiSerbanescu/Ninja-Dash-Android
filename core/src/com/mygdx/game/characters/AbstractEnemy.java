package com.mygdx.game.characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

public abstract class AbstractEnemy implements Enemy {

    private Rectangle collBox;
    protected Vector2 position;
    protected boolean isDead = false;


    public boolean collides(Rectangle rect) {
        return collBox.overlaps(rect);
    }


    protected void updateCollBox() {
        collBox.setPosition(position);
    }

    protected void initCollBox(float x, float y, float width, float height) {
        collBox = new Rectangle(x, y, width, height);
    }

    public boolean isDead() {
        return isDead;
    }

    protected Vector2 getNewWidthHeight(float oldWidth, float oldHeight, int factor) {
        float alpha = oldHeight / oldWidth;

        float newWidth = MyGdxGame.WIDTH / factor;
        float newHeight = alpha * newWidth;

        return new Vector2(newWidth, newHeight);

        /*
        *
        *  float alpha = enemyTexture.getWidth() / enemyTexture.getHeight();

        width = MyGdxGame.WIDTH / 10;
        height = alpha * MyGdxGame.WIDTH / 10;
        *
        * */
    }

    public Vector2 getPosition() {
        return position;
    }
}
