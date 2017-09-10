package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private TextureRegion endFrame;

    private boolean cycle;
    private boolean loopedOnce;

    public Animation(Texture[] textures, int frameCount, float cycleTime) {
        this(textures, frameCount, cycleTime, true);
    }

    public Animation(Texture[] textures, int frameCount, float cycleTime, boolean cycle) {
        frames = new Array<TextureRegion>();

        for (int i = 0; i < textures.length; i++) {
            frames.add(new TextureRegion(textures[i]));
        }

        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
        this.cycle = cycle;
        endFrame = frames.get(frames.size - 1);
    }

    public void update(float deltaTime) {
        currentFrameTime += deltaTime;

        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }

        if (frame == frameCount - 1) {
            loopedOnce = true;
        }

        if (frame == frameCount) {
            frame = 0;
        }
    }

    public TextureRegion getFrame() {
        if (!cycle && loopedOnce) {
            return endFrame;
        }

        return frames.get(frame);
    }

    public void dispose() {
        frames.clear();
    }
}
