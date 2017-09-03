package com.mygdx.game.characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractEnemy {

    private Rectangle collBox;
    protected Vector2 position;


    public boolean collides(Rectangle rect) {
        return collBox.overlaps(rect);
    }


    protected void updateCollBox() {
        collBox.setPosition(position);
    }

    protected void initCollBox(float x, float y, float width, float height) {
        collBox = new Rectangle(x, y, width, height);
    }
}
