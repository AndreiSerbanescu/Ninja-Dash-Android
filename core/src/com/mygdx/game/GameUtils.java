package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.sprites.Animation;

public class GameUtils {



    public static Animation makeAnimation(String rootPath, String format, int frameCount, float cycleTime) {
        return makeAnimation(rootPath, format, frameCount, cycleTime, true);
    }
    public static Animation makeAnimation(String rootPath, String format, int frameCount, float cycleTime, boolean cycle) {
        return new Animation(makeTextureArray(rootPath, format, frameCount), frameCount, cycleTime, cycle);
    }

    public static Texture[] makeTextureArray(String rootPath, String format, int number) {
        Texture[] textures = new Texture[number];

        for (int i = 0; i < number; i++) {
            textures[i] = new Texture(rootPath + Integer.toString(i) + "." + format);
        }

        return textures;
    }
}
