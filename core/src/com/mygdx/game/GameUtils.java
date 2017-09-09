package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.sprites.Animation;

public class GameUtils {


    public static Animation makeAnimation(String rootPath, String format, int frameCount, float cycleTime) {
        return new Animation(makeTextureArray(rootPath, format, frameCount), frameCount, cycleTime);
    }

    public static Texture[] makeTextureArray(String rootPath, String format, int number) {
        Texture[] textures = new Texture[number];

        for (int i = 0; i < number; i++) {
            textures[i] = new Texture(rootPath + Integer.toString(i) + "." + format);
        }

        return textures;
    }
}
