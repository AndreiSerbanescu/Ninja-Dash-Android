package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameStateManager;
import com.mygdx.game.MyGdxGame;

import java.util.Stack;

public class StartState extends AbstractState {

    private Texture background;
    private Texture playButton;


    public StartState(GameStateManager game) {
        super(game);

        background = new Texture("background.jpg");
        playButton = new Texture("playButton.png");

        camera.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            game.set(new PlayState(game));
            dispose();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);

        sb.begin();
        sb.draw(background, 0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        sb.draw(playButton,
                MyGdxGame.WIDTH / 2 - playButton.getWidth() / 2,
                MyGdxGame.HEIGHT / 2 - playButton.getHeight() / 2);



        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
