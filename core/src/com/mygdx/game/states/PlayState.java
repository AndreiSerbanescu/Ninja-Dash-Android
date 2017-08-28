package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameStateManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Player;

public class PlayState extends AbstractState {
    private com.mygdx.game.sprites.Player player;

    public PlayState(GameStateManager game) {
        super(game);

        Texture playerTexture = new Texture("ninjaAnimation/Run__000.png");

        int newWidth = MyGdxGame.WIDTH / 5;
        int newHeight = newWidth * playerTexture.getHeight() / playerTexture.getWidth();

        player = new Player(newWidth,
                newHeight,
                0, MyGdxGame.HEIGHT / 4);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            player.jump();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();

        player.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch sb) {
        player.render(sb);
    }

    @Override
    public void dispose() {

    }
}
