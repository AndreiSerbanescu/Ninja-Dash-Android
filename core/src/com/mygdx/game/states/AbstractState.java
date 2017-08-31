package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameStateManager;

public abstract class AbstractState {

    protected Vector2 mouse;
    protected GameStateManager game;
    protected OrthographicCamera camera;

    protected AbstractState(GameStateManager game) {
        this.game = game;
        mouse = new Vector2();
        camera = new OrthographicCamera();

    }

    public abstract void handleInput();
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch sb);

    public abstract void dispose();
}
