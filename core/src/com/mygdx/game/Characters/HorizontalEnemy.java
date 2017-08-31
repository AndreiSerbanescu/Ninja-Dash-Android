package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.sprites.Animation;

public class HorizontalEnemy implements Enemy {
    private TextureRegion textReg;

    private Animation idleAnimation;
    private Animation attackAnimation;


    public HorizontalEnemy() {
        initTextReg();

        initAnimations();

    }

    @Override
    public void attack() {

    }

    private void initAnimations() {

    }

    private void initTextReg() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render() {

    }

    @Override
    public void update(float deltaTime) {

    }
}
