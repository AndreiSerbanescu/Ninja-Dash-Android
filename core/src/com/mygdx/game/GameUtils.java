package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.sprites.Animation;

public class GameUtils {


    public static Animation makeAnimation(String rootPath, String format, int frameCount, float cycleTime) {
        Texture[] textures = new Texture[frameCount];

        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Texture(rootPath + Integer.toString(i) + "." + format);
        }

        return new Animation(textures, frameCount, cycleTime);
    }
}
